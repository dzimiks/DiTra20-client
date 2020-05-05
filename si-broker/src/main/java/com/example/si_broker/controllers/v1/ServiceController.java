package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.services.ServiceAPIService;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
