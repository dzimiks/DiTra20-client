package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.Role;
import com.example.si_broker.services.ServiceAPIService;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL)
public class ServiceController {

    private ServiceAPIService serviceAPIService;

    @Autowired
    public ServiceController(ServiceAPIService serviceAPIService) {
        this.serviceAPIService = serviceAPIService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return new ResponseEntity<>(serviceAPIService.getAllServices(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ServiceDTO> addService(@RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceAPIService.addService(serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String id, @RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceAPIService.updateService(id, serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Boolean> deleteServiceById(@PathVariable String id) {
        return new ResponseEntity<>(serviceAPIService.deleteServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<ServiceDTO> getServiceById(@RequestParam(value = "id") String id) {
        return new ResponseEntity<>(serviceAPIService.getServiceById(id), HttpStatus.OK);
    }
}
