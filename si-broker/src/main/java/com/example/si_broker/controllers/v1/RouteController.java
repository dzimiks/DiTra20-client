package com.example.si_broker.controllers.v1;

import com.example.si_broker.domain.*;
import com.example.si_broker.repositories.*;

import com.example.si_broker.services.LogService;
import com.example.si_broker.state.Context;
import com.example.si_broker.state.ServiceRunnerState;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL)
public class RouteController {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    LogService logService;

    @Autowired
    ComplexServiceRepository complexServiceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/**")
    public ResponseEntity<Object> findServiceForUser(HttpServletRequest request) throws IOException {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        UsernamePasswordAuthenticationToken securityContextHolder = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Principal: " + principal);
        System.out.println("SecurityContextHolder: " + securityContextHolder);

        MyUserDetails userDetails = new MyUserDetails(
            "id-1",
            principal.getName(),
            "email",
            principal.getCredentials().toString(),
            principal.getAuthorities()
        );

//        System.out.println("User details: " + userDetails);

        String uri = request.getRequestURI();
        System.out.println("URI: " + uri);

        int PREFIX_LENGTH = "/api/v1/services".length();
        int index = uri.lastIndexOf('/');

        String part = uri.substring(0, index);
        String serviceName = part.length() <= PREFIX_LENGTH ? uri.substring(index + 1) : part.substring(PREFIX_LENGTH + 1);
        String routePart = uri.substring(PREFIX_LENGTH + serviceName.length() + 1);
        String route = routePart.equals("") ? "/" : routePart;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();


//        System.out.println("Service name: " + serviceName);
//        System.out.println("Route: " + route);

        Optional<ServiceDomain> serviceDomain = serviceRepository.findByName(serviceName);
        Optional<ComplexService> complexService = complexServiceRepository.findByName(serviceName);
        // TODO: Add complex service

        if (serviceDomain.isEmpty()) {

            logService.addLog(new Log(UUID.randomUUID().toString(),serviceName,userDetails.getUsername(),dateFormat.format(date),"User tried to access non-existing service",false));
            // TODO: 21.5.20. Logger
            System.out.println("User: " + userDetails + " tried to access non-existing " + serviceName + " service at " + dateFormat.format(date));

            return ResponseEntity.badRequest().body("Error: Service " + serviceName + " doesn't exists!");
        }

//        logService.addLog(new Log(UUID.randomUUID().toString(),serviceName,userDetails.getUsername(),dateFormat.format(date),true));
//        // TODO: 21.5.20. Logger
//        System.out.println("User: " + userDetails + " tried to access " + serviceName + " service at " + dateFormat.format(date));

        if (!serviceDomain.isEmpty()) {
            ServiceDomain service = serviceDomain.get();
//            System.out.println("SERVICE: " + service);

            Map<String, Map<String, Object>> serviceEndpointAndRoles = service.getEndpointAndRoles();

            if (!serviceEndpointAndRoles.containsKey(route)) {
                ResponseEntity.badRequest().body("Error: Endpoint " + route + " doesn't exists!");
            }

//            System.out.println("DETAILS: " + userDetails);
            boolean accessGranted = false;

            for (GrantedAuthority authority : userDetails.getAuthorities()) {
//                System.out.println("AUTHORITY: " + authority.getAuthority());
//                Optional<Role> role = roleRepository.findByName(RoleType.valueOf(authority.getAuthority()));
//
//                if (role.isEmpty()) {
//                    return ResponseEntity.badRequest().body("Error: Role " + authority.getAuthority() + " doesn't exists!");
//                }

                List<Role> roles = (List<Role>) serviceEndpointAndRoles.get(route).get("roles");

                for (Role r : roles) {
                    if (r.getName().equals(RoleType.valueOf(authority.getAuthority()))) {
                        accessGranted = true;
                        logService.addLog(new Log(UUID.randomUUID().toString(),serviceName,userDetails.getUsername(),dateFormat.format(date),"User accessed service",true));
                        // TODO: 21.5.20. Logger
                        System.out.println("Access to service: " + serviceName + " GRANTED for User: " + userDetails + " at " + dateFormat.format(date));
                        break;
                    }
                }
            }

            if (!accessGranted) {
                logService.addLog(new Log(UUID.randomUUID().toString(),serviceName,userDetails.getUsername(),dateFormat.format(date),"User tried to access service without permission",false));
                // TODO: 21.5.20. Logger
                System.out.println("Access to service: " + serviceName + "NOT GRANTED for User: " + userDetails + " at " + dateFormat.format(date));
                return ResponseEntity.badRequest().body("Error: You don't have permission for service " + serviceName);
            }

            // TODO: Which URL should be valid?
            String url = "http://" + service.getRoute() + ":" + service.getPort() + route;
            System.out.println("URL: " + url);

            if (request.getMethod().equals("POST")) {
                String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                System.out.println(body);

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccessControlAllowOrigin("this is origin");
                HttpEntity<String> entity = new HttpEntity<>(body, headers);

                // TODO: Error is here!
                String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();

                if (response != null) {
                    return ResponseEntity.ok().body(response);
                }

                return ResponseEntity.badRequest().body("Error: Failed to get a response from service.");
            }
        }

        if (!complexService.isEmpty()) {
            ComplexService cs = complexService.get();
            String parameters = request.getQueryString();

            Context context = new Context();

            for (ServiceDomain sd : cs.getServiceDomainList()) {
                context.setServiceDomain(sd);
                context.setComplexServiceParameters(parameters);
                ServiceRunnerState serviceRunnerState = new ServiceRunnerState();
                context.setState(serviceRunnerState);
                context.execute();
            }
        }


        return ResponseEntity.ok().build();
    }
}
