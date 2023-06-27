package com.practice.BackendApp.repository;

import com.practice.BackendApp.model.Purchased;
import com.practice.BackendApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedRepository extends JpaRepository<Purchased, Long> {

    List<Purchased> findByUser(User user);

}
