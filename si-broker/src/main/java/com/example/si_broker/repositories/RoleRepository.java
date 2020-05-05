package com.example.si_broker.repositories;

import com.example.si_broker.domain.Role;
import com.example.si_broker.domain.RoleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleType name);
}
