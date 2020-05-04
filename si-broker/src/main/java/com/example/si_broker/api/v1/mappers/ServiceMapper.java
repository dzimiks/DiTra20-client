package com.example.si_broker.api.v1.mappers;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.ServiceDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceMapper {
    ServiceMapper instance = Mappers.getMapper(ServiceMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "route", target = "route")
    @Mapping(source = "port", target = "port")
    @Mapping(source = "httpMethod", target = "httpMethod")
    @Mapping(source = "endpointAndRoles", target = "endpointAndRoles")
    ServiceDTO serviceToServiceDTO(ServiceDomain service);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "route", target = "route")
    @Mapping(source = "port", target = "port")
    @Mapping(source = "httpMethod", target = "httpMethod")
    @Mapping(source = "endpointAndRoles", target = "endpointAndRoles")
    ServiceDomain serviceDTOtoService(ServiceDTO serviceDTO);

}
