package com.example.capstone1.Service;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList<User>();

    private final MerchantStockService merchantStockService;
    private final ProductService productService;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(int id, User user) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUserId() == id){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUserId() == id){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public User getUserById(int id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUserId() == id){
                return users.get(i);
            }
        }
        return null;
    }
    // new method add to shopping cart
    public ResponseEntity addToShoppingCart(int userId, int productId) {

        String cartMessage;
        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            cartMessage = "User with id " + userId + " not found";
            return ResponseEntity.status(400).body(cartMessage);
        }

        // Check if product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            cartMessage = "Product with id " + productId + " not found";
            return ResponseEntity.status(400).body(cartMessage);
        }

        // Check if merchant stock exists and is sufficient
        MerchantStock merchantStock = merchantStockService.getMerchantStockByProductId(productId);
        if (merchantStock.getQuantity() <= 0) {
            cartMessage = "Insufficient stock for productId " + productId;
            return ResponseEntity.status(400).body(cartMessage);
        }
        // add to user shopping cart
            product.setAddToShoppingCart(true);
            user.getShoppingCart().add(product);
            updateUser(userId, user);
        // calculate the total amount of all products
        double total = 0;
        for (Product p : user.getShoppingCart()) {
            total += p.getPrice();
        }
        // print selected product
        StringBuilder cartDetails = new StringBuilder();
        for (Product p : user.getShoppingCart()) {
            cartDetails.append("Product ID: ").append(p.getProductId())
                    .append(", Product Name: ").append(p.getProductName())
                    .append(", Product Price: ").append(p.getPrice()).append("\n");
        }
        String responseMessage = "User id " + userId + " added to shopping cart:\n"
                + cartDetails.toString() + "Total price: " + String.format("%.2f", total);
        return ResponseEntity.status(200).body(responseMessage);
    }

    // New method to remove from shopping cart
    public ResponseEntity removeFromShoppingCart(int userId, int productId) {
        String cartMessage;

        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            cartMessage = "User with id " + userId + " not found";
            return ResponseEntity.status(400).body(cartMessage);
        }

        // Check if product is in shopping cart
        Product productToRemove = null;
        for (Product product : user.getShoppingCart()) {
            if (product.getProductId() == productId) {
                productToRemove = product;
                break;
            }
        }

        if (productToRemove == null) {
            cartMessage = "Product with id " + productId + " not found in shopping cart";
            return ResponseEntity.status(400).body(cartMessage);
        }

        // Remove product from shopping cart
        user.getShoppingCart().remove(productToRemove);
        updateUser(userId, user);

        cartMessage = "Product with id " + productId + " removed from shopping cart";
        return ResponseEntity.status(200).body(cartMessage);
    }

    public ResponseEntity buyProduct(int userId, int productId, int merchantId) {
        String resultMessage;

        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            resultMessage = "User with id " + userId + " not found";
            return ResponseEntity.status(400).body(resultMessage);
        }

        // Check if product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            resultMessage = "Product with id " + productId + " not found";
            return ResponseEntity.status(400).body(resultMessage);
        }

        // Check if merchant stock exists and is sufficient
        MerchantStock merchantStock = merchantStockService.getMerchantStockByIds(merchantId);
        if (merchantStock == null) {
            resultMessage = "Merchant stock with merchantId " + merchantId + " and productId " + productId + " not found";
            return ResponseEntity.status(400).body(resultMessage);
        }

        if (merchantStock.getQuantity() <= 0) {
            resultMessage = "Insufficient stock for productId " + productId + " at merchantId " + merchantId;
            return ResponseEntity.status(400).body(resultMessage);
        }

        // Check if user has enough balance
        if (user.getBalance() < product.getPrice()) {
            resultMessage = "Insufficient balance for userId " + userId;
            return ResponseEntity.status(400).body(resultMessage);
        }

        // Reduce stock and update user balance
        if (!merchantStockService.reduceStock(merchantId, productId, 1)) {
            resultMessage = "Failed to reduce stock for productId " + productId + " at merchantId " + merchantId;
            return ResponseEntity.status(400).body(resultMessage);
        }

        user.setBalance(user.getBalance() - product.getPrice());
        updateUser(userId, user);

        resultMessage = "Product purchased successfully";
        return ResponseEntity.status(200).body(resultMessage);
    }
}


