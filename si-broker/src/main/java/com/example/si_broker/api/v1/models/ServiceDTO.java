package com.example.si_broker.api.v1.models;

import com.example.si_broker.domain.Role;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class ServiceDTO {

    private String id;
    private String name;
    private String route;
    private Integer port;
    private String httpMethod;
    private Set<Role> roles;
    private Map<String, Map<String, Object>> endpointAndRoles;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Map<String, Map<String, Object>> getEndpointAndRoles() {
        return endpointAndRoles;
    }

    public void setEndpointAndRoles(Map<String, Map<String, Object>> endpointAndRoles) {
        this.endpointAndRoles = endpointAndRoles;
    }
}
