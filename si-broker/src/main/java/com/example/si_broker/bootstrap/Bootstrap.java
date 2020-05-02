package com.example.si_broker.bootstrap;

import com.example.si_broker.domain.ServiceDomain;
import com.example.si_broker.domain.User;
import com.example.si_broker.repositories.ServiceRepository;
import com.example.si_broker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Bootstrap implements CommandLineRunner {

    private UserRepository userRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public Bootstrap(UserRepository userRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setFirstName("first name");
        user1.setLastName("last name");
        user1.setUsername("123");
        user1.setPassword("123");
        user1.setEmail("123@gmail.com");
        user1.setRole("normal");

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setFirstName("Vanja");
        user2.setLastName("Paunović");
        user2.setUsername("dzimiks");
        user2.setPassword("dzimiks");
        user2.setEmail("vana997@gmail.com");
        user2.setRole("admin");

        User user3 = new User();
        user3.setId(UUID.randomUUID().toString());
        user3.setFirstName("Milan");
        user3.setLastName("Mitić");
        user3.setUsername("miki");
        user3.setPassword("milan");
        user3.setEmail("tkemi@gmail.com");
        user3.setRole("admin");

        ServiceDomain serviceDomain1= new ServiceDomain();
        serviceDomain1.setId(UUID.randomUUID().toString());
        serviceDomain1.setName("MikiMilan");
        serviceDomain1.setRoute("localhost:8081/MikiMilan/baza");
        serviceDomain1.setHttpMethod("POST");
        List<String> role = new ArrayList();
        role.add("Dzimiks");
        role.add("Miha");
        serviceDomain1.getEndpointAndRoles().put("/endpoint",role);

        serviceRepository.save(serviceDomain1);
        // TODO: insert()
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);

        System.out.println("Services loaded: "+ serviceRepository.count());
        System.out.println("Users loaded: " + userRepository.count());
    }
}
