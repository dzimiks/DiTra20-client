package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ComplexServiceDTO;
import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.services.ComplexServiceAPIService;
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

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL + "/registerComplexService")
public class ComplexServiceController {

    private ComplexServiceAPIService complexServiceAPIService;

    @Autowired
    public ComplexServiceController(ComplexServiceAPIService complexServiceAPIService) {
        this.complexServiceAPIService = complexServiceAPIService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/allComplexServices")
    public ResponseEntity<List<ComplexServiceDTO>> getAllComplexServices() {
        return new ResponseEntity<>(complexServiceAPIService.getAllComplexServices(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/addComplexService")
    public ResponseEntity<ComplexServiceDTO> addComplexService(@RequestBody ComplexServiceDTO complexServiceDTO) {
        return new ResponseEntity<>(complexServiceAPIService.addComplexService(complexServiceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/updateComplexService")
    public ResponseEntity<ComplexServiceDTO> updateComplexService(@RequestParam String id, @RequestBody ComplexServiceDTO complexServiceDTO) {
        return new ResponseEntity<>(complexServiceAPIService.updateComplexService(id, complexServiceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteComplexService")
    public ResponseEntity<Boolean> deleteComplexServiceById(@RequestParam String id) {
        return new ResponseEntity<>(complexServiceAPIService.deleteComplexServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/findComplexServiceById")
    public ResponseEntity<ComplexServiceDTO> getComplexServiceById(@RequestParam String id) {
        return new ResponseEntity<>(complexServiceAPIService.getComplexServiceById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/addService")
    public ResponseEntity<ComplexServiceDTO> addService(@RequestParam String id,@RequestBody ServiceDTO serviceDTO){
        return new ResponseEntity<>(complexServiceAPIService.addService(id,serviceDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/deleteService")
    public ResponseEntity<ComplexServiceDTO> deleteService(@RequestParam String id,@RequestBody ServiceDTO serviceDTO){
        return new ResponseEntity<>(complexServiceAPIService.deleteService(id,serviceDTO), HttpStatus.OK);
    }

}