package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.Product;
import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBProduct;

import java.util.ArrayList;
import java.util.List;

public class InventoryDBAdapter {

    public DBInventory convertInventoryToDBModel(UpdateInventoryForUserRequest updateInventoryForUserRequest) {
        final DBInventory dbInventory = new DBInventory();
        dbInventory.setUsername(updateInventoryForUserRequest.getUsername());
        final DBProduct dbProduct = new DBProduct();
        dbProduct.setProductName(updateInventoryForUserRequest.getUsername());
        dbInventory.setInventory(getDBProducts(updateInventoryForUserRequest.getInventory()));
        return dbInventory;
    }

    private List<DBProduct> getDBProducts(List<Product> productList) {
        List<DBProduct> dbProductList = new ArrayList<>();
        for (Product product : productList) {
            dbProductList.add(new DBProduct().toDBProduct(product));
        }
        return dbProductList;
    }

    public DBInventory convertGetInventoryForUserRequestToDBModel(GetInventoryForUserRequest getInventoryForUserRequest) {
        final DBInventory inventory = new DBInventory();
        inventory.setUsername(getInventoryForUserRequest.getUsername());
        return inventory;
    }

    public GetInventoryForUserResponse convertInventoryToUserResponse(DBInventory dbInventory) {
        final GetInventoryForUserResponse getInventoryForUserResponse = new GetInventoryForUserResponse();
        getInventoryForUserResponse.setUsername(dbInventory.getUsername());
        getInventoryForUserResponse.setInventory(getUserProducts(dbInventory.getInventory()));
        return getInventoryForUserResponse;
    }

    private List<Product> getUserProducts(List<DBProduct> dbProductList) {
        List<Product> productList = new ArrayList<>();
        for (DBProduct dbProduct : dbProductList) {
            productList.add(new Product().toUserProduct(dbProduct));
        }
        return productList;
    }
}
