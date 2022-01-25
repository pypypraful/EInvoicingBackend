package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.Product;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;

import java.util.ArrayList;
import java.util.List;

public class CustomerCartDBAdapter {

    public static List<Product> getCartProductsFromDBCustomerCart(
            List<DBCustomerCart> dbCustomerCartList, List<Object> dbSellerInventoryList) {
        List<Product> productList = new ArrayList<>();
        for (Object dbSellerInventory : dbSellerInventoryList) {
            for (DBCustomerCart dbCustomerCart: dbCustomerCartList) {
                if (((DBSellerInventory) dbSellerInventory).productId.equals(dbCustomerCart.getProductId())) {
                    productList.add(Product.builder()
                            .productId(dbCustomerCart.productId)
                            .price(((DBSellerInventory) dbSellerInventory).productPrice.doubleValue())
                            .productName(((DBSellerInventory) dbSellerInventory).productName)
                            .quantity(dbCustomerCart.getQuantity())
                            .build()
                    );
                }
            }
        }
        return productList;
    }

}
