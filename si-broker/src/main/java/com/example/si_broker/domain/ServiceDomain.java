package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Document(collection = "services")
public class ServiceDomain {

    @Id
    private String id;

    private String name;

    private String route;

    private Integer port;

    private String httpMethod;

    private Set<Role> roles = new HashSet<>();

    private Map<String, Map<String, Object>> endpointAndRoles = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, Map<String, Object>> getEndpointAndRoles() {
        return endpointAndRoles;
    }

    public void setEndpointAndRoles(Map<String, Map<String, Object>> endpointAndRoles) {
        this.endpointAndRoles = endpointAndRoles;
    }
}
