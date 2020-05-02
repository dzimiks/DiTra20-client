package com.example.si_broker.api.v1.models;

import com.example.si_broker.domain.Role;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ServiceDTO {
    private String id;
    private String name;
    private String route;
    private Integer port;
    private String httpMethod;
    private Map<String, List<Role>> endpointAndRoles;
}
