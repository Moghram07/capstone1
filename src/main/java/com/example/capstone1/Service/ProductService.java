package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    ArrayList<Product> products = new ArrayList<Product>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductId() == id){
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductId() == id){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public Product getProductById(int id) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductId() == id){
                return products.get(i);
            }
        }
        return null;
    }
}
