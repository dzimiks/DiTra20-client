package com.example.si_broker.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "services")
public class ServiceDomain {

    @Id
    private String id;

    private String name;

    private String route;

    // TODO: 2.5.20. Mozda ne treba jer ruta ukljucuje port
//    private String port;

    private String httpMethod;

    // TODO: 2.5.20. value treba da bude lista rola, promeni kad vanja doda role
    private Map<String,List<String>> endpointAndRoles = new HashMap<>();
}
