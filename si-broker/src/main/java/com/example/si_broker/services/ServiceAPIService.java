package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceAPIService {
    List<ServiceDTO> getAllServices();

    ServiceDTO addService(ServiceDTO serviceDTO);

    ServiceDTO updateService(String id, ServiceDTO serviceDTO);

    Boolean deleteServiceById(String id);

    ServiceDTO getServiceById(String id);

    ServiceDTO addServiceEndpoint(ServiceDTO serviceDTO,Map<String, Set<Role>> endpointAndRoles);

    ServiceDTO updateServiceEndpoint(ServiceDTO serviceDTO,Map<String, Set<Role>> endpointAndRoles);

    ServiceDTO deleteServiceEndpoint(ServiceDTO serviceDTO,Map<String, Set<Role>> endpointAndRoles);


}
