package com.example.si_broker.api.v1.models;

import lombok.Data;

import java.util.Map;

@Data
public class ServiceDTO {
    private String id;
    private String name;
    private String route;
    private Integer port;
    private String httpMethod;
    private Map<String, Map<String, Object>> endpointAndRoles;
}
