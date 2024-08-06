package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    private ArrayList<Product> shoppingCart = new ArrayList<>();
    
    @NotNull(message = "userId can not be null")
    private int userId;

    @NotEmpty(message = "userName can not be empty")
    private String userName;

    @NotEmpty(message = "password can not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[!@#$%^&*]).{8,}$", message = "password must be 8 characters minimum and must have one of each of the following [a-z, A-Z, 0-9, !@#$%^&*]")
    private String password;

    @NotEmpty(message = "email can not be empty")
    @Email
    private String email;

    @NotEmpty(message = "role can not be empty")
    @Pattern(regexp = "admin|customer", message = "role must be admin or customer")
    private String role;

    @NotNull(message = "balance can not be empty")
    @Positive(message = "balance must be a positive number")
    private double balance;


}
