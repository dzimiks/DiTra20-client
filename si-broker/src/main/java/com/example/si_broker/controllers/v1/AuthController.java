package com.example.si_broker.controllers.v1;

import com.example.si_broker.domain.Log;
import com.example.si_broker.domain.MyUserDetails;
import com.example.si_broker.domain.User;
import com.example.si_broker.payload.requests.LoginRequest;
import com.example.si_broker.payload.requests.SignupRequest;
import com.example.si_broker.payload.responses.JWTResponse;
import com.example.si_broker.repositories.LogRepository;
import com.example.si_broker.repositories.RoleRepository;
import com.example.si_broker.repositories.UserRepository;
import com.example.si_broker.security.JWTUtils;
import com.example.si_broker.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.AUTH_BASE_URL)
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    LogRepository logRepository;
    PasswordEncoder passwordEncoder;
    JWTUtils jwtUtils;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("MyUserDetails: " + ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities());

        String jwt = jwtUtils.generateJWTToken(authentication);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JWTResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
            )
        );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        System.out.println(signUpRequest);

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body("Error: Email is already in use!");
        }

        User user = new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword())
        );

        System.out.println("User: " + user);
        System.out.println(signUpRequest);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        user.setRoles(signUpRequest.getRoles());
        userRepository.save(user);
        logRepository.save(new Log(UUID.randomUUID().toString(),"register",user.getUsername(),dateFormat.format(date),"User registered",true));
        return ResponseEntity.ok("User registered successfully!");
    }
}
