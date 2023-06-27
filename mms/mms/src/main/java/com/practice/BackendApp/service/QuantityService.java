package com.practice.BackendApp.service;

import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.Quantity;
import com.practice.BackendApp.repository.QuantityRepository;
import com.practice.BackendApp.utils.QuantityRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuantityService {

    private final ProductService productService;
    private final QuantityRepository quantityRepository;

    public QuantityService(ProductService productService, QuantityRepository quantityRepository) {
        this.productService = productService;
        this.quantityRepository = quantityRepository;
    }

    public Quantity registerQuantity(QuantityRequest quantityRequest) throws Exception {
        Product product = productService.getProductByCode(quantityRequest.getProductCode());
        product.setQuantity(product.getQuantity() + quantityRequest.getQuantity());
        productService.updateProduct(product);

        Quantity quantity = new Quantity();
        quantity.setProductCode(product.getId());
        quantity.setProduct(product);
        quantity.setQuantity(quantityRequest.getQuantity());
        quantity.setOperation("ADD");
        quantity.setDate(new Date());

        return quantityRepository.save(quantity);
    }
}
