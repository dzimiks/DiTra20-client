package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.Role;
import com.example.si_broker.services.ServiceAPIService;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL)
public class ServiceController {

    private ServiceAPIService serviceAPIService;

    @Autowired
    public ServiceController(ServiceAPIService serviceAPIService) {
        this.serviceAPIService = serviceAPIService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET,value = "/allServices")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return new ResponseEntity<>(serviceAPIService.getAllServices(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST,value = "/addService")
    public ResponseEntity<ServiceDTO> addService(@RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceAPIService.addService(serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/updateService/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String id, @RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceAPIService.updateService(id, serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteService/{id}")
    public ResponseEntity<Boolean> deleteServiceById(@PathVariable String id) {
        return new ResponseEntity<>(serviceAPIService.deleteServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/findServiceById/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable String id) {
        return new ResponseEntity<>(serviceAPIService.getServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/updateEndpoint/{id}")
    public ResponseEntity<ServiceDTO> addServiceEndpoint(@PathVariable String id, @RequestBody Map<String, Set<Role>>endpointAndRole){
        return new ResponseEntity<>(serviceAPIService.addServiceEndpoint(id,endpointAndRole),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/updateEndpoint/{id}")
    public ResponseEntity<ServiceDTO> updateServiceEndpoint(@PathVariable String id, @RequestBody Map<String,Set<Role>> endpointAndRole){
        return new ResponseEntity<>(serviceAPIService.updateServiceEndpoint(id,endpointAndRole),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/updateEndpoint/{id}")
    public ResponseEntity<ServiceDTO> deleteServiceEndpoint(@PathVariable String id, @RequestBody String endpoint){
        return new ResponseEntity<>(serviceAPIService.deleteServiceEndpoint(id,endpoint),HttpStatus.OK);
    }

}
