package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotNull(message = "id can not be null")
    private int id;

    @NotNull(message = "product id can not be null")
    private int productId;

    @NotNull(message = "merchant id can not be null")
    private int merchantId;

    @NotNull(message = "quantity can not be null")
    private int quantity;
}
