package com.practice.BackendApp.service;

import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.ProductRequest;

import java.util.List;

public interface ProductService {
    public Product registerProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
//    void updateProductQuantity(String productCode, int quantity);
    Product getProductByCode(Long id);
    Product updateProduct(Product product) throws Exception;

}
