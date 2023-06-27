package com.practice.BackendApp.controller;

import com.practice.BackendApp.model.Product;
import com.practice.BackendApp.model.Purchased;
import com.practice.BackendApp.model.User;
import com.practice.BackendApp.service.AuthService;
import com.practice.BackendApp.service.ProductService;
import com.practice.BackendApp.service.PurchasedService;
import com.practice.BackendApp.utils.PurchasedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchased")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class PurchasedController {

    private final PurchasedService purchasedService;
    private final ProductService productService;
    private final AuthService userService;

    @Autowired
    public PurchasedController(PurchasedService purchasedService, ProductService productService, AuthService userService) {
        this.purchasedService = purchasedService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPurchasedItems(@Valid @RequestBody List<PurchasedRequest> purchasedItems, Authentication authentication) throws Exception {
        // Retrieve the authenticated user's details
        User user = (User) authentication.getPrincipal();

        // Create a list to hold the Purchased entities
        List<Purchased> purchasedList = new ArrayList<>();

        // Iterate over the purchaseRequests and create Purchased entities
        for (PurchasedRequest request : purchasedItems) {
            Purchased purchased = new Purchased();
            purchased.setUser(user);
            purchasedList.add(purchased);
            // Retrieve the product based on the provided product code
            Product product = productService.getProductByCode(request.getProductCode());
            if (product == null) {
                // Product not found, return an error response
                return ResponseEntity.notFound().build();
            }
            purchased.setQuantity(request.getQuantity());
            purchased.setProductCode(request.getProductCode());
            purchased.setProduct(product);

            int remainingQuantity = product.getQuantity() - request.getQuantity();
            product.setQuantity(remainingQuantity);
            productService.updateProduct(product);


        }
        purchasedService.addPurchasedItems(purchasedList);
        return ResponseEntity.ok("Purchased items added successfully.");
    }

    @GetMapping
    public List<Purchased> getPurchasedItemsForUser(Principal principal) {
        String username = principal.getName(); // Get the logged-in username
        User user = userService.getUserByUsername(username); // Get the User entity based on the username

        List<Purchased> purchasedItems = purchasedService.getPurchasedItemsByUserId(user.getId());
        System.out.println("These are the returned purchase items::"+purchasedItems);

//         Populate product associations
        for (Purchased purchased : purchasedItems) {
            Product product = productService.getProductByCode(purchased.getProductCode());
            purchased.setProduct(product);
        }

        return purchasedItems;
    }
}
