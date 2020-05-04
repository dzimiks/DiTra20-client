package com.example.si_broker.controllers.v1;

import com.example.si_broker.domain.MyUserDetails;
import com.example.si_broker.repositories.ServiceRepository;
import com.example.si_broker.repositories.UserRepository;
import com.example.si_broker.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL)
public class RouteController {

    ServiceRepository serviceRepository;
    UserRepository userRepository;

    @RequestMapping("/**")
    public ResponseEntity<Object> findServiceForUser(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        System.out.println("Principal: "+ principal);
        String user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        System.out.println("User info: "+ user);

        return null;
    }
}
