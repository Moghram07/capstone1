package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public void addMerchantStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
    }

    public boolean updateMerchantStock(int id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
             if (merchantStocks.get(i).getId() == id) {
                    merchantStocks.set(i, merchantStock);
                    return true;
             }
        }
        return false;
    }

    public boolean deleteMerchantStock(int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
        if (merchantStocks.get(i).getId() == id) {
            merchantStocks.remove(i);
             return true;
            }
        }
        return false;
    }

    public boolean additionalMerchantStock(int merchantId, int productId, int amount) {
        for (MerchantStock m : merchantStocks) {
            if(m.getMerchantId() == merchantId && m.getProductId() == productId) {
                m.setQuantity(m.getQuantity() + amount);
                return true;
            }
        }
        return false;
    }

    public MerchantStock getMerchantStockByIds(int merchantId) {
        for (int i = 0; i < merchantStocks.size(); i++) {
        if (merchantStocks.get(i).getMerchantId() == merchantId) {
                return merchantStocks.get(i);
             }
        }
        return null;
    }

    public MerchantStock getMerchantStockByProductId(int productId) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getProductId() == productId) {
                return merchantStocks.get(i);
            }
        }
        return null;
    }

    public boolean reduceStock(int merchantId, int productId, int amount) {
        MerchantStock ms = getMerchantStockByIds(merchantId);
        if (ms != null && ms.getQuantity() >= amount) {
            ms.setQuantity(ms.getQuantity() - amount);
            return true;
        }
        return false;
    }
}
