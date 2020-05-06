package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.ComplexServiceDTO;
import com.example.si_broker.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(Constants.SERVICES_BASE_URL + "/complexService")
public class ComplexServiceTestController {

    @RequestMapping(method = RequestMethod.GET, value = "/getStudents")
    public ResponseEntity<String> getStudents() {
        return  ResponseEntity.ok().body("Vraceni svi studenti");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/function")
    public ResponseEntity<String> compute(@RequestParam String function) {
        if(function.equals("AVG")){
            return ResponseEntity.ok().body("AVG FUNCTION");
        }else if(function.equals("SUM")){
            return ResponseEntity.ok().body("SUM FUNCTION");
        }
        return ResponseEntity.ok().body("FUNCTION NOT FOUND");

    }

    @RequestMapping(method = RequestMethod.GET, value = "/sendMail")
    public ResponseEntity<String> sendMail() {
        return  ResponseEntity.ok().body("Mail poslat");
    }
}
