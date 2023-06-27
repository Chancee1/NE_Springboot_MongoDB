package com.practice.BackendApp.service;

import com.practice.BackendApp.model.User;
import com.practice.BackendApp.repository.UserRepository;
import com.practice.BackendApp.roles.Role;
import com.practice.BackendApp.utils.AuthenticationRequest;
import com.practice.BackendApp.utils.AuthenticationResponse;
import com.practice.BackendApp.utils.JWTService;
import com.practice.BackendApp.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        Check if the email already exists
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
     var user = User.builder()
             .firstname(registerRequest.getFirstname())
             .phone(registerRequest.getPhone())
             .email(registerRequest.getEmail())
             .password(
                     passwordEncoder.encode(registerRequest.getPassword())
             )
             .role(Role.USER)
             .build();
     userRepository.save(user);
    return AuthenticationResponse.
             builder()
            .message("User registered successfully")
             .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest){
//        Check if user exists
         Optional<User> user = userRepository.findByEmail(authRequest.getEmail());
         if(user.isEmpty()){
             throw new IllegalArgumentException("Incorrect Username or Password");
         }
         if(!passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())){
             throw new IllegalArgumentException("Incorrect Username or Password");
         }
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        var userDetails = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        return AuthenticationResponse.
                builder()
                .token(jwtService.generateToken(userDetails))
                .message("User Logged in successfully")
                .build();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username).get();
    }
}
