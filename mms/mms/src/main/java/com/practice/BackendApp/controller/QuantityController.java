package com.practice.BackendApp.controller;

import com.practice.BackendApp.model.Quantity;
import com.practice.BackendApp.service.QuantityService;
import com.practice.BackendApp.utils.QuantityRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/quantity")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class QuantityController {

    private final QuantityService quantityService;

    public QuantityController(QuantityService quantityService) {
        this.quantityService = quantityService;
    }

    @PostMapping
    public ResponseEntity<Quantity> registerProductQuantity(@Valid @RequestBody QuantityRequest quantityRequest) throws Exception {
        Quantity registeredQuantity = quantityService.registerQuantity(quantityRequest);
        return ResponseEntity.ok(registeredQuantity);
    }
}