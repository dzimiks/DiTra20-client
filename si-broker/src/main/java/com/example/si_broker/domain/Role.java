package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private RoleType name;
}