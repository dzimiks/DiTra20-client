package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.ComplexServiceDTO;
import com.example.si_broker.api.v1.models.ServiceDTO;

import java.util.List;

public interface ComplexServiceAPIService {
    List<ComplexServiceDTO> getAllComplexServices();

    ComplexServiceDTO addComplexService(ComplexServiceDTO serviceDTO);

    ComplexServiceDTO updateComplexService(String id, ComplexServiceDTO complexServiceDTO);

    Boolean deleteComplexServiceById(String id);

    ComplexServiceDTO addService(String id, ServiceDTO serviceDTO);

    ComplexServiceDTO deleteService(String id, String serviceId);

    ComplexServiceDTO getComplexServiceById(String id);
}
