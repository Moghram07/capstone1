package com.example.capstone1.Controller;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getStock(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        return ResponseEntity.status(200).body(merchantStocks);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(errorMessage);
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(201).body("Merchant Stock Added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStock(@PathVariable int id, @Valid @RequestBody MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(errorMessage);
        }
        boolean stockUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if(stockUpdated){
            return ResponseEntity.status(201).body("Merchant Stock Updated");
        }
        return ResponseEntity.status(404).body("Merchant Stock with id "+ id +" Not Found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStock(@PathVariable int id){
        boolean stockDeleted = merchantStockService.deleteMerchantStock(id);
        if(stockDeleted){
            return ResponseEntity.status(201).body("Merchant Stock Deleted");
        }
        return ResponseEntity.status(404).body("Merchant Stock with id "+ id +" Not Found");
    }

    @PutMapping("/additional/{merchantId}/{productId}/{Amount}")
    public ResponseEntity additionalStock(@PathVariable int merchantId, @PathVariable int productId, @PathVariable int Amount){
        boolean additionalAdded = merchantStockService.additionalMerchantStock(merchantId, productId, Amount);
        if(additionalAdded){
            return ResponseEntity.status(201).body("Additional Merchant Stock Added");
        }
        return ResponseEntity.status(404).body("Additional Merchant Stock Not Found");
    }
}
