package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.ComplexServiceDTO;
import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.ServiceDomain;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ComplexServiceAPIService {
    List<ComplexServiceDTO> getAllComplexServices();

    ComplexServiceDTO addComplexService(ComplexServiceDTO serviceDTO);

    ComplexServiceDTO updateComplexService(String id, ComplexServiceDTO complexServiceDTO);

    Boolean deleteComplexServiceById(String id);

    ComplexServiceDTO addService(String id,ServiceDTO serviceDTO);

    ComplexServiceDTO deleteService(String id,ServiceDTO serviceDTO);

    ComplexServiceDTO getComplexServiceById(String id);


}
