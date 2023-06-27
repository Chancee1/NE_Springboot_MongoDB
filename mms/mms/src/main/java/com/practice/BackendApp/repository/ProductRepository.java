package com.practice.BackendApp.repository;

import com.practice.BackendApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//If it was in mysql, you cause JpaRepository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
