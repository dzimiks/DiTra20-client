package com.example.si_broker.services.impl;

import com.example.si_broker.api.v1.mappers.ComplexServiceMapper;
import com.example.si_broker.api.v1.mappers.ServiceMapper;
import com.example.si_broker.api.v1.models.ComplexServiceDTO;
import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.ComplexService;
import com.example.si_broker.repositories.ComplexServiceRepository;
import com.example.si_broker.services.ComplexServiceAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplexServiceAPIServiceImpl implements ComplexServiceAPIService {


    private ComplexServiceRepository complexServiceRepository;

    private ComplexServiceMapper complexServiceMapper;

    private ServiceMapper serviceMapper;

    @Autowired
    public ComplexServiceAPIServiceImpl(ComplexServiceRepository complexServiceRepository, ComplexServiceMapper complexServiceMapper, ServiceMapper serviceMapper) {

        this.complexServiceRepository = complexServiceRepository;
        this.complexServiceMapper = complexServiceMapper;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ComplexServiceDTO> getAllComplexServices() {
        return complexServiceRepository.findAll()
                .stream()
                .map(complexServiceMapper::coplexServiceToComplexServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComplexServiceDTO addComplexService(ComplexServiceDTO complexServiceDTO) {
        return saveAndReturnDTO(complexServiceMapper.complexServiceDTOtoComplexService(complexServiceDTO));
    }

    @Override
    public ComplexServiceDTO updateComplexService(String id, ComplexServiceDTO complexServiceDTO) {
        ComplexService complexServiceToSave = complexServiceMapper.complexServiceDTOtoComplexService(complexServiceDTO);
        complexServiceToSave.setId(id);
        return saveAndReturnDTO(complexServiceToSave);
    }

    @Override
    public Boolean deleteComplexServiceById(String id) {
        Optional<ComplexService> complexServiceToDelete = complexServiceRepository.findById(id);

        if (complexServiceToDelete.isEmpty()) {
            return false;
        }

        complexServiceRepository.deleteById(id);
        return true;
    }


    @Override
    public ComplexServiceDTO addService(String id,ServiceDTO serviceDTO) {
        Optional<ComplexService> optionalComplexService = complexServiceRepository.findById(id);

        if (optionalComplexService.isEmpty()) {
            return null;
        }

        ComplexService complexService = optionalComplexService.get();

        complexService.getServiceDomainList().remove(serviceMapper.serviceDTOtoService(serviceDTO));
        return saveAndReturnDTO(complexService);
    }

    @Override
    public ComplexServiceDTO deleteService(String id,ServiceDTO serviceDTO) {
        Optional<ComplexService> optionalComplexService = complexServiceRepository.findById(id);

        if (optionalComplexService.isEmpty()) {
            return null;
        }

        ComplexService complexService = optionalComplexService.get();

        complexService.getServiceDomainList().remove(serviceMapper.serviceDTOtoService(serviceDTO));

        return saveAndReturnDTO(complexService);
    }

    @Override
    public ComplexServiceDTO getComplexServiceById(String id) {
        Optional<ComplexService> complexService = complexServiceRepository.findById(id);

        if (complexService.isEmpty()) {
            return null;
        }

        return complexServiceMapper.coplexServiceToComplexServiceDTO(complexService.get());
    }



    private ComplexServiceDTO saveAndReturnDTO(ComplexService service) {
        complexServiceRepository.save(service);
        return complexServiceMapper.coplexServiceToComplexServiceDTO(service);
    }
}
