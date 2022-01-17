package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.SellerInventory;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryDBAdapter {

    public List<DBSellerInventory> convertSellerInventoryToDBModel(UpdateSellerInventoryRequest updateSellerInventoryRequest) {
        final List<DBSellerInventory> dbSellerInventories = new ArrayList<>();
        for (SellerInventory sellerInventory: updateSellerInventoryRequest.getSellerInventories()) {
            dbSellerInventories.add(DBSellerInventory.builder()
                    .productCategory(sellerInventory.getProductCategory())
                    .productPrice(sellerInventory.getProductPrice())
                    .productName(sellerInventory.getProductName())
                    .productDescription(sellerInventory.getProductDescription())
                    .productQuantity(sellerInventory.getProductQuantity())
                    .productSubCategory(sellerInventory.getProductSubCategory())
                    .productWeightPerUnitInGrams(sellerInventory.getProductWeightPerUnitInGrams())
                    .username(sellerInventory.getUsername())
                    .build());
        }
        return dbSellerInventories;
    }

    public GetProductListResponse convertDBSellerInventoryToProductListResponse(List<DBSellerInventory> dbSellerInventories) {
        final GetProductListResponse getProductListResponse = new GetProductListResponse();
        getProductListResponse.setProductList(new ArrayList<SellerInventory>(dbSellerInventories.size()));
        for (DBSellerInventory dbSellerInventory : dbSellerInventories) {
            getProductListResponse.getProductList()
                    .add(SellerInventory.builder()
                            .productId(dbSellerInventory.getProductId())
                            .productCategory(dbSellerInventory.getProductCategory())
                            .productPrice(dbSellerInventory.getProductPrice())
                            .productName(dbSellerInventory.getProductName())
                            .productDescription(dbSellerInventory.getProductDescription())
                            .productQuantity(dbSellerInventory.getProductQuantity())
                            .productSubCategory(dbSellerInventory.getProductSubCategory())
                            .productWeightPerUnitInGrams(dbSellerInventory.getProductWeightPerUnitInGrams())
                            .username(dbSellerInventory.getUsername())
                            .build()
                    );
        }
        return getProductListResponse;
    }
}
