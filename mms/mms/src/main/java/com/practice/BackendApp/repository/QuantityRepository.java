package com.practice.BackendApp.repository;

import com.practice.BackendApp.model.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
}
