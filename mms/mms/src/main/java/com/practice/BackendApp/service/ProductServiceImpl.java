package com.practice.BackendApp.service;

import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.ProductRequest;
import com.practice.BackendApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product registerProduct(ProductRequest productRequest) {
        System.out.println("You are here again");
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setProductType(productRequest.getProductType());
        product.setPrice(productRequest.getPrice());
        product.setInDate(new Date());
        System.out.println("You are about to save");

        return productRepository.save(product);
    }

//    @Override
//    public void updateProductQuantity(String productCode, int quantity) {
//        Product product = productRepository.findByCode(productCode);
//        if (product != null) {
//            int newQuantity = product.getQuantity() + quantity;
//            product.setQuantity(newQuantity);
//            productRepository.save(product);
//        } else {
//            return;
//        }
//    }

    public Product getProductByCode(Long id) {
        return productRepository.findById(id).get();
    }
    @Override
    public Product updateProduct(Product product) throws Exception {
        if (!productRepository.existsById(product.getId())) {
            throw new Exception("Product not found with code: " + product.getId());
        }

        return productRepository.save(product) ;
    }

}
