package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean updateMerchant(int id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getMerchantId() == id){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    public boolean removeMerchant(int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getMerchantId() == id){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

}
