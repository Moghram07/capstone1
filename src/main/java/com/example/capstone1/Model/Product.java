package com.example.capstone1.Model;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotNull(message = "productId can not be null")
    private int productId;

    @NotEmpty(message = "product name can not be empty")
    private String productName;

    @NotNull(message = "price can not be null")
    @Positive(message = "price must be a positive number")
    private double price;

    @NotEmpty(message = "description can not be null")
    private String description;

    @NotEmpty(message = "product category can not be empty")
    private String productCategory;

    @NotNull(message = "categoryId can not be empty")
    private int categoryId;

    @AssertFalse
    private boolean addToShoppingCart;

}
