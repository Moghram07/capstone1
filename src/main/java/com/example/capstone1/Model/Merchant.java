package com.example.capstone1.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "name can not be empty")
    private String merchantName;

    @NotNull(message = "id can not be null")
    private int merchantId;

    @NotEmpty(message = "address can not be empty")
    private String address;

    @NotEmpty(message = "phone can not be empty")
    @Pattern(regexp = "^05[\\d]{8}$", message = "phone has to start with 05 and 8 other numbers")
    private String phone;

    @NotEmpty(message = "email can not be empty")
    @Email
    private String email;
}
