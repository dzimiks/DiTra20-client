package com.example.si_broker.bootstrap;

import com.example.si_broker.domain.*;
import com.example.si_broker.repositories.ServiceRepository;
import com.example.si_broker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Bootstrap implements CommandLineRunner {

    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Bootstrap(
            UserRepository userRepository,
            ServiceRepository serviceRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {
        // TODO: Run to delete all users
        serviceRepository.deleteAll();
        userRepository.deleteAll();

        System.out.println("Users are deleted!");
        System.out.println("Services are deleted!");

        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setFirstName("first name");
        user1.setLastName("last name");
        user1.setUsername("123");
        user1.setPassword(bCryptPasswordEncoder.encode("123"));
        user1.setEmail("123@gmail.com");
        user1.getRoles().add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_ADMIN));

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setFirstName("Vanja");
        user2.setLastName("Paunović");
        user2.setUsername("dzimiks");
        user2.setPassword(bCryptPasswordEncoder.encode("dzimiks"));
        user2.setEmail("vana997@gmail.com");
        user2.getRoles().add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_ADMIN));
        user2.getRoles().add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_USER));

        User user3 = new User();
        user3.setId(UUID.randomUUID().toString());
        user3.setFirstName("Milan");
        user3.setLastName("Mitić");
        user3.setUsername("miki");
        user3.setPassword(bCryptPasswordEncoder.encode("milan"));
        user3.setEmail("tkemi@gmail.com");
        user3.getRoles().add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_ADMIN));

        Provider provider1 = new Provider();
        provider1.setId(UUID.randomUUID().toString());
        provider1.setFirstName("Aleksa");
        provider1.setLastName("Aleksic");
        provider1.setUsername("alexa");
        provider1.setPassword(bCryptPasswordEncoder.encode("alexa"));
        provider1.setEmail("alexa@gmail.com");
        provider1.getRoles().add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_PROVIDER));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(provider1);

        ServiceDomain serviceDomain1 = new ServiceDomain();
        serviceDomain1.setId(UUID.randomUUID().toString());
        serviceDomain1.setName("sql-service");
        System.out.println("localhost:8080/dodajRecordUBazu");
        serviceDomain1.setRoute("localhost:8081/sql-service/baza");
        serviceDomain1.setHttpMethod("POST");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_USER));
        roles.add(new Role(UUID.randomUUID().toString(),RoleType.ROLE_ADMIN));
        serviceDomain1.setRoles(roles);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("roles", roles);
        objectMap.put("method", "POST");

        serviceDomain1.getEndpointAndRoles().put("/endpoint", objectMap);

        serviceRepository.save(serviceDomain1);

        System.out.println("Services loaded: " + serviceRepository.count());
        System.out.println("Users loaded: " + userRepository.count());
    }
}
