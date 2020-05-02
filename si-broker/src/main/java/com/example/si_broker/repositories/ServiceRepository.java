package com.example.si_broker.repositories;

import com.example.si_broker.domain.ServiceDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ServiceRepository extends MongoRepository<ServiceDomain, String> {
    Optional<ServiceDomain> findById(String id);
}
