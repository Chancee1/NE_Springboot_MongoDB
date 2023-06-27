package com.practice.BackendApp.controller;

import com.practice.BackendApp.service.AuthService;
import com.practice.BackendApp.utils.AuthenticationRequest;
import com.practice.BackendApp.utils.AuthenticationResponse;
import com.practice.BackendApp.utils.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
public class AuthenticationController {

    @Autowired
    public AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult){
       try{
           if (bindingResult.hasErrors()) {
               // If validation errors exist, return a bad request response with the error messages
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(bindingResult));
           }
           AuthenticationResponse authResponse = authService.register(registerRequest);
           return ResponseEntity.ok(authResponse);
       }catch (IllegalArgumentException e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authRequest, BindingResult bindingResult){
        try{
            if (bindingResult.hasErrors()) {
                // If validation errors exist, return a bad request response with the error messages
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(bindingResult));
            }
            return ResponseEntity.ok(authService.authenticate(authRequest));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    private String getValidationErrors(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append("; ");
        }
        return sb.toString();
    }
}
