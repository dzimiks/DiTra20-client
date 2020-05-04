package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.ServiceDTO;

import java.util.List;
import java.util.Map;

public interface ServiceAPIService {
    List<ServiceDTO> getAllServices();

    ServiceDTO addService(ServiceDTO serviceDTO);

    ServiceDTO updateService(String id, ServiceDTO serviceDTO);

    Boolean deleteServiceById(String id);

    ServiceDTO getServiceById(String id);

    ServiceDTO addServiceEndpoint(String id, Map<String, Map<String, Object>> endpointAndRoles);

    ServiceDTO updateServiceEndpoint(String id, Map<String, Map<String, Object>> endpointAndRoles);

    ServiceDTO deleteServiceEndpoint(String id, String endpoint);
}
