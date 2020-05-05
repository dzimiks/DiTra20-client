package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.ComplexServiceDomain;
import com.example.si_broker.domain.ComplexServiceType;
import com.example.si_broker.services.ServiceAPIService;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL + "/registerUpdateService")
public class ServiceController {

    private ServiceAPIService serviceAPIService;

    @Autowired
    public ServiceController(ServiceAPIService serviceAPIService) {
        this.serviceAPIService = serviceAPIService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/allServices")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return new ResponseEntity<>(serviceAPIService.getAllServices(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/addService")
    public ResponseEntity<ServiceDTO> addService(@RequestBody ServiceDTO serviceDTO) {
        if (serviceDTO.getType() != null) {
            ComplexServiceDomain complexService = serviceDTOtoComplexService(serviceDTO);
            UsernamePasswordAuthenticationToken securityContextHolder = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String username = securityContextHolder.getName();

            Map<String, Map<String, Object>> serviceEndpointAndRoles = complexService.getEndpointAndRoles();

            for (String newEndpoint : serviceEndpointAndRoles.keySet()) {
                if (serviceEndpointAndRoles.get(newEndpoint).containsKey("type")) {
                    Map<String, Object> map = serviceEndpointAndRoles.get(newEndpoint);
                    ComplexServiceType type = ComplexServiceType.valueOf((String) map.get("type"));

                    switch (type) {
                        case ENDPOINT:
                            break;
                        case CATEGORY:
                            break;
                        case FUNCTION:
                            break;
                        default:
                            System.err.println("Error: Type " + type + " doesn't exists!");
                            break;
                    }
                }
            }
        }

        return new ResponseEntity<>(serviceAPIService.addService(serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/updateService")
    public ResponseEntity<ServiceDTO> updateService(@RequestParam String id, @RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceAPIService.updateService(id, serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteService")
    public ResponseEntity<Boolean> deleteServiceById(@RequestParam String id) {
        return new ResponseEntity<>(serviceAPIService.deleteServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/findServiceById")
    public ResponseEntity<ServiceDTO> getServiceById(@RequestParam String id) {
        return new ResponseEntity<>(serviceAPIService.getServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/updateEndpoint")
    public ResponseEntity<ServiceDTO> addServiceEndpoint(@RequestParam String id, @RequestBody Map<String, Map<String, Object>> endpointAndRoles) {
        return new ResponseEntity<>(serviceAPIService.addServiceEndpoint(id, endpointAndRoles), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/updateEndpoint")
    public ResponseEntity<ServiceDTO> updateServiceEndpoint(@RequestParam String id, @RequestBody Map<String, Map<String, Object>> endpointAndRoles) {
        return new ResponseEntity<>(serviceAPIService.updateServiceEndpoint(id, endpointAndRoles), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/updateEndpoint")
    public ResponseEntity<ServiceDTO> deleteServiceEndpoint(@RequestParam String id, @RequestBody String endpoint) {
        return new ResponseEntity<>(serviceAPIService.deleteServiceEndpoint(id, endpoint), HttpStatus.OK);
    }

    private ComplexServiceDomain serviceDTOtoComplexService(ServiceDTO serviceDTO) {
        ComplexServiceDomain complexService = new ComplexServiceDomain();
        complexService.setId(serviceDTO.getId());
        complexService.setName(serviceDTO.getName());
        complexService.setRoute(serviceDTO.getRoute());
        complexService.setPort(serviceDTO.getPort());
        complexService.setHttpMethod(serviceDTO.getHttpMethod());
        complexService.setType(serviceDTO.getType());
        complexService.setRoles(serviceDTO.getRoles());
        complexService.setEndpointAndRoles(serviceDTO.getEndpointAndRoles());
        return complexService;
    }
}
