package com.practice.BackendApp.service;

import com.practice.BackendApp.model.Purchased;
import com.practice.BackendApp.model.User;
import com.practice.BackendApp.repository.PurchasedRepository;
import com.practice.BackendApp.repository.UserRepository;
import com.practice.BackendApp.utils.PurchasedRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasedService {
    private final UserRepository userRepository;
    private final PurchasedRepository purchasedRepository;

    public PurchasedService(PurchasedRepository purchasedRepository, UserRepository userRepository) {
        this.purchasedRepository = purchasedRepository;
        this.userRepository = userRepository;
    }

    public void addPurchasedItems(List<Purchased> purchasedItems) {
        List<Purchased> purchases = new ArrayList<>();
        for (Purchased purchasedItem : purchasedItems) {
            purchases.add(purchasedItem);
        }
        purchasedRepository.saveAll(purchases);
    }

    public List<Purchased> getPurchasedItemsByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        return purchasedRepository.findByUser(user);
    }
}
