package com.example.si_broker.services.impl;

import com.example.si_broker.api.v1.mappers.ServiceMapper;
import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.ServiceDomain;
import com.example.si_broker.domain.User;
import com.example.si_broker.repositories.ServiceRepository;
import com.example.si_broker.services.ServiceAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceAPIServiceImpl implements ServiceAPIService {

    private ServiceRepository serviceRepository;
    private ServiceMapper serviceMapper;

    @Autowired
    public ServiceAPIServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(serviceMapper::serviceToServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO addService(ServiceDTO serviceDTO) {
        return saveAndReturnDTO(serviceMapper.serviceDTOtoService(serviceDTO));
    }

    @Override
    public ServiceDTO updateService(String id, ServiceDTO serviceDTO) {
        ServiceDomain serviceDomainToSave = serviceMapper.serviceDTOtoService(serviceDTO);
        serviceDomainToSave.setId(id);
        return saveAndReturnDTO(serviceDomainToSave);
    }

    @Override
    public Boolean deleteServiceById(String id) {
        Optional<ServiceDomain> serviceDomainToDelete = serviceRepository.findById(id);

        if (serviceDomainToDelete.isEmpty()) {
            return false;
        }

        serviceRepository.deleteById(id);
        return true;
    }

    @Override
    public ServiceDTO getServiceById(String id) {
        Optional<ServiceDomain> serviceDomain = serviceRepository.findById(id);

        if (serviceDomain.isEmpty()) {
            return null;
        }
        return serviceMapper.serviceToServiceDTO(serviceDomain.get());
    }

    private ServiceDTO saveAndReturnDTO(ServiceDomain service) {
        serviceRepository.save(service);
        return serviceMapper.serviceToServiceDTO(service);
    }
}
