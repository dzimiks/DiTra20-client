package com.example.si_broker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@Data
@Document(collection = "log")
public class Log {

    @Id
    private String id;
    private String serviceName;
    private String username;
    private String date;
    private Boolean succesful;

}
