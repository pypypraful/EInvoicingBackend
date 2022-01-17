package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.common.SellerInventory;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class SaveInventoryHandlerTest {

    private SaveInventoryHandler saveInventoryHandler;

    @Test
    @Ignore
    public void testHandler(){
        saveInventoryHandler = new SaveInventoryHandler();
        UpdateSellerInventoryRequest updateSellerInventoryRequest = new UpdateSellerInventoryRequest();
        updateSellerInventoryRequest.setSellerInventories(Arrays.asList(SellerInventory.builder()
                .username("Praful")
                .productName("Ruchi Almonds")
                .productSubCategory("Almonds")
                .productCategory("Dry Fruits")
                .productQuantity(100)
                .productPrice(2000)
                .productDescription("Description for the Almonds")
                .productWeightPerUnitInGrams(200)
                .build()));
        saveInventoryHandler.handleRequest(updateSellerInventoryRequest, null);
    }
}
