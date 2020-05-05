package com.example.si_broker.api.v1.models;

import com.example.si_broker.domain.Role;
import com.example.si_broker.domain.ServiceDomain;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class ComplexServiceDTO {
    private String id;
    private String name;
    private String route;
    private Integer port;
    private String httpMethod;
    private Set<Role> roles;
    private List<ServiceDomain> serviceDomainList;
}
