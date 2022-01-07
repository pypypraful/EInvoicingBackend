package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.common.Product;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SaveInventoryHandlerTest {

    private SaveInventoryHandler saveInventoryHandler;

    @Test
    @Ignore
    public void testHandler(){
        saveInventoryHandler = new SaveInventoryHandler();
        Product product = new Product();
        product.setProductName("Sugar");
        product.setPrice(2000.00);
        product.setQuantity(200);
        List<Product> productList = Arrays.asList(product);
        UpdateInventoryForUserRequest updateInventoryForUserRequest = new UpdateInventoryForUserRequest();
        updateInventoryForUserRequest.setUsername("praful");
        updateInventoryForUserRequest.setInventory(productList);
        saveInventoryHandler.handleRequest(updateInventoryForUserRequest, null);
    }
}
