package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private RoleType name;

    public Role(String id,RoleType name){
        this.id = id;
        this.name = name;
    }

}