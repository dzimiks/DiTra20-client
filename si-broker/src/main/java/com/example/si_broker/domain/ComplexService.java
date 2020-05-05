package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.*;

@Data
@Document(collection = "complexservice")
public class ComplexService {

    @Id
    private String id;

    private String name;

    private String route;

    private Integer port;

    private String httpMethod;

    private Set<Role> roles = new HashSet<>();

    private List<ServiceDomain> serviceDomainList = new ArrayList<>();

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}