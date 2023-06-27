package com.practice.BackendApp.controller;

import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.ProductRequest;
import com.practice.BackendApp.service.ProductService;
import com.practice.BackendApp.utils.QuantityRequest;
import com.practice.BackendApp.utils.ValidationErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<?> registerProduct(@RequestBody ProductRequest productRequest, BindingResult bindingResult) {
        Product product = productService.registerProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    private String getValidationErrors(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append("; ");
        }
        return sb.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        // Get the list of field errors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // Create a response object with the validation error details
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setMessage("Validation failed");
        for (FieldError fieldError : fieldErrors) {
            response.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Return the response with a 400 Bad Request status
        return ResponseEntity.badRequest().body(response);
    }
}
