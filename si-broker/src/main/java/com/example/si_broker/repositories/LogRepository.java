package com.example.si_broker.repositories;


import com.example.si_broker.domain.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface LogRepository extends MongoRepository<Log, String> {
}
