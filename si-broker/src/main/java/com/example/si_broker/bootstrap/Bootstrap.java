package com.example.si_broker.bootstrap;

import com.example.si_broker.domain.*;
import com.example.si_broker.repositories.ComplexServiceRepository;
import com.example.si_broker.repositories.LogRepository;
import com.example.si_broker.repositories.ServiceRepository;
import com.example.si_broker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Bootstrap implements CommandLineRunner {

    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ComplexServiceRepository complexServiceRepository;
    private LogRepository logRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Bootstrap(
            UserRepository userRepository,
            ServiceRepository serviceRepository,
            ComplexServiceRepository complexServiceRepository,
            LogRepository logRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.complexServiceRepository = complexServiceRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.logRepository = logRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bootstrap init!");
//        init();
    }

    private void init() {
        // TODO: Run to delete all users
        serviceRepository.deleteAll();
        userRepository.deleteAll();
        complexServiceRepository.deleteAll();
        logRepository.deleteAll();

        System.out.println("Users are deleted!");
        System.out.println("Services are deleted!");

        generateUsers();
        generateServicesAndEndpoints();
    }

    private void generateUsers() {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setFirstName("first name");
        user1.setLastName("last name");
        user1.setUsername("123");
        user1.setPassword(bCryptPasswordEncoder.encode("123"));
        user1.setEmail("123@gmail.com");
        user1.getRoles().add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_ADMIN));

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setFirstName("Vanja");
        user2.setLastName("Paunović");
        user2.setUsername("dzimiks");
        user2.setPassword(bCryptPasswordEncoder.encode("dzimiks"));
        user2.setEmail("vana997@gmail.com");
        user2.getRoles().add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_ADMIN));
        user2.getRoles().add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_USER));

        User user3 = new User();
        user3.setId(UUID.randomUUID().toString());
        user3.setFirstName("Milan");
        user3.setLastName("Mitić");
        user3.setUsername("miki");
        user3.setPassword(bCryptPasswordEncoder.encode("milan"));
        user3.setEmail("tkemi@gmail.com");
        user3.getRoles().add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_ADMIN));

        Provider provider1 = new Provider();
        provider1.setId(UUID.randomUUID().toString());
        provider1.setFirstName("Aleksa");
        provider1.setLastName("Aleksic");
        provider1.setUsername("alexa");
        provider1.setPassword(bCryptPasswordEncoder.encode("alexa"));
        provider1.setEmail("alexa@gmail.com");
        provider1.getRoles().add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_PROVIDER));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(provider1);

        System.out.println("Users loaded: " + userRepository.count());
    }

    private void generateServicesAndEndpoints() {
        ServiceDomain serviceDomain1 = new ServiceDomain();
        serviceDomain1.setId(UUID.randomUUID().toString());
        serviceDomain1.setName("sql-service");
        serviceDomain1.setPort(8081);
//        serviceDomain1.setRoute("localhost:8081/sql-service/baza");
        serviceDomain1.setRoute("localhost");
        serviceDomain1.setHttpMethod("POST");

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_USER));
        roles.add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_ADMIN));
        serviceDomain1.setRoles(roles);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("roles", roles);
        objectMap.put("method", "POST");

        serviceDomain1.getEndpointAndRoles().put("/", objectMap);
        serviceDomain1.getEndpointAndRoles().put("/select", objectMap);

        ServiceDomain serviceDomain2 = new ServiceDomain();
        serviceDomain2.setId(UUID.randomUUID().toString());
        serviceDomain2.setName("sqlWebService");
        serviceDomain2.setPort(3000);
        serviceDomain2.setRoute("/tables");
        serviceDomain2.setHttpMethod("GET");

        Set<Role> roles2 = new HashSet<>();
        roles2.add(new Role(UUID.randomUUID().toString(), RoleType.ROLE_ADMIN));
        serviceDomain2.setRoles(roles2);

        Map<String, Object> objectMap2 = new HashMap<>();
        objectMap2.put("roles", roles2);
        objectMap2.put("method", "GET");

        serviceDomain2.getEndpointAndRoles().put("/select", objectMap2);
        serviceDomain2.getEndpointAndRoles().put("/delete", objectMap2);
        serviceDomain2.getEndpointAndRoles().put("/insert", objectMap2);
        serviceDomain2.getEndpointAndRoles().put("/update", objectMap2);

        ComplexService complexService1 = new ComplexService();
        complexService1.setId(UUID.randomUUID().toString());
        complexService1.setName("complexService");
        complexService1.setPort(3030);
        complexService1.setRoute("/complexService1");
        complexService1.setHttpMethod("GET");
        List<ServiceDomain> serviceDomainList = new ArrayList<>();
        serviceDomainList.add(serviceDomain1);
//        serviceDomainList.add(serviceDomain2);
        complexService1.setRoles(roles2);
        complexService1.setServiceDomainList(serviceDomainList);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Log log = new Log(UUID.randomUUID().toString(),"serviceName","milan",dateFormat.format(date),"User logged in",true);



        logRepository.save(log);
        serviceRepository.save(serviceDomain1);
        serviceRepository.save(serviceDomain2);
        complexServiceRepository.save(complexService1);


        System.out.println("Services loaded: " + serviceRepository.count());
    }
}
