package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
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
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public User getUserById(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public String addToShoppingCart(int userId, int productId) {
        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            return "User with id " + userId + " not found";
        }

        // Check if product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product with id " + productId + " not found";
        }

        // Check if merchant stock exists and is sufficient
        MerchantStock merchantStock = merchantStockService.getMerchantStockByProductId(productId);
        if (merchantStock.getQuantity() <= 0) {
            return "Insufficient stock for productId " + productId;
        }

        // Add to user shopping cart
        product.setAddToShoppingCart(true);
        user.getShoppingCart().add(product);
        updateUser(userId, user);

        // Calculate the total amount of all products
        double total = 0;
        for (Product p : user.getShoppingCart()) {
            total += p.getPrice();
        }

        // Print selected product
        StringBuilder cartDetails = new StringBuilder();
        for (Product p : user.getShoppingCart()) {
            cartDetails.append("Product ID: ").append(p.getProductId())
                    .append(", Product Name: ").append(p.getProductName())
                    .append(", Product Price: ").append(p.getPrice()).append("\n");
        }

        return "User id " + userId + " added to shopping cart:\n" + cartDetails.toString() +
                "Total price: " + String.format("%.2f", total);
    }

    public String buyProduct(int userId, int productId, int merchantId) {
        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            return "User with id " + userId + " not found";
        }

        // Check if product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product with id " + productId + " not found";
        }

        // Check if merchant stock exists and is sufficient
        MerchantStock merchantStock = merchantStockService.getMerchantStockByIds(merchantId);
        if (merchantStock == null) {
            return "Merchant stock with merchantId " + merchantId + " and productId " + productId + " not found";
        }

        if (merchantStock.getQuantity() <= 0) {
            return "Insufficient stock for productId " + productId + " at merchantId " + merchantId;
        }

        // Check if user has enough balance
        if (user.getBalance() < product.getPrice()) {
            return "Insufficient balance for userId " + userId;
        }

        // Reduce stock and update user balance
        if (!merchantStockService.reduceStock(merchantId, productId, 1)) {
            return "Failed to reduce stock for productId " + productId + " at merchantId " + merchantId;
        }

        user.setBalance(user.getBalance() - product.getPrice());
        updateUser(userId, user);

        return "Product purchased successfully";
    }

    // New method to remove from shopping cart
    public String removeFromShoppingCart(int userId, int productId) {
        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            return "User with id " + userId + " not found";
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
            return "Product with id " + productId + " not found in shopping cart";
        }

        // Remove product from shopping cart
        user.getShoppingCart().remove(productToRemove);
        updateUser(userId, user);

        return "Product with id " + productId + " removed from shopping cart";
    }

    // New method to get student discount
    public String getStudentDiscount(int userId) {
        // Check if user exists
        User user = getUserById(userId);
        if (user == null) {
            return "User with id " + userId + " not found";
        }

        // Apply a discount of 10% for students
        double discount = 0.1;
        double total = 0;
        for (Product product : user.getShoppingCart()) {
            total += product.getPrice();
        }

        double discountedTotal = total - (total * discount);
        return "User id " + userId + " has a total price of " + String.format("%.2f", total)
                + " with a student discount of 10%, the total is: " + String.format("%.2f", discountedTotal);
    }
}
