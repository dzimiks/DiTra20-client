package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "services")
public class ComplexServiceDomain extends ServiceDomain {

    private ComplexServiceType type;

    public ComplexServiceType getType() {
        return type;
    }

    public void setType(ComplexServiceType type) {
        this.type = type;
    }
}
