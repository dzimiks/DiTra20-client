package com.example.si_broker.repositories;

import com.example.si_broker.domain.ComplexService;
import com.example.si_broker.domain.ServiceDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ComplexServiceRepository extends MongoRepository<ComplexService, String> {
    Optional<ComplexService> findById(String id);

    Optional<ComplexService> findByName(String name);
}
