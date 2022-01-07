package com.pypypraful.einvoicing.model.request;

import com.pypypraful.einvoicing.model.common.Product;

import java.util.List;

public class UpdateInventoryForUserRequest {

    private String username;
    private List<Product> inventory;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        this.inventory = inventory;
    }
}
