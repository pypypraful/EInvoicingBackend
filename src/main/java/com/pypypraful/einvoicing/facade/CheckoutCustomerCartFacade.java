package com.pypypraful.einvoicing.facade;

import com.pypypraful.einvoicing.model.common.Product;
import com.pypypraful.einvoicing.model.request.CheckoutCustomerCartRequest;
import com.pypypraful.einvoicing.model.response.CheckoutCustomerCartResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;
import com.pypypraful.einvoicing.workflows.stateMachine.StateMachineRepository;
import com.pypypraful.einvoicing.workflows.stateMachine.StateMachineRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CheckoutCustomerCartFacade {

    private final InventoryRepository inventoryRepository;
    private final CheckoutCustomerCartResponse checkoutCustomerCartResponse;
    private final StateMachineRepository stateMachineRepository;
    private List<DBCustomerCart> dbCustomerCart;

    public CheckoutCustomerCartFacade() {
        inventoryRepository = new DynamoDBInventoryRepository();
        checkoutCustomerCartResponse = new CheckoutCustomerCartResponse();
        stateMachineRepository = new StateMachineRepositoryImpl();
    }

    public CheckoutCustomerCartResponse checkoutCustomerCart(CheckoutCustomerCartRequest checkoutCustomerCartRequest) {
        checkoutCustomerCartResponse.setCustomerId(checkoutCustomerCartRequest.getCustomerId());
        dbCustomerCart = inventoryRepository.getCustomerCartByCustomerId(checkoutCustomerCartRequest.getCustomerId());
        String orderId = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        stateMachineRepository.startCheckoutStateMachine(orderId, checkoutCustomerCartRequest.getCustomerId());
        for (DBCustomerCart customerCart : dbCustomerCart) {
            DBSellerInventory dbSellerInventory = inventoryRepository.getSellerProductByProductId(customerCart.productId);
            try {
                if (dbSellerInventory.productQuantity < customerCart.quantity) {
                    throw new Exception("Not Enough Products available!");
                }
                inventoryRepository.checkoutProductFromCustomerCart(dbSellerInventory, customerCart, orderId);
                addAcceptedProduct(dbSellerInventory, customerCart);
            } catch (Exception ex) {
                System.out.println(ex);
                addRejectedProduct(dbSellerInventory, customerCart);
            }
        }
        return checkoutCustomerCartResponse;
    }

    private void addAcceptedProduct(DBSellerInventory dbSellerInventory, DBCustomerCart customerCart) {
        if (checkoutCustomerCartResponse.getAcceptedProducts() == null)
            checkoutCustomerCartResponse.setAcceptedProducts(new ArrayList<Product>());
        checkoutCustomerCartResponse.getAcceptedProducts().add(Product.builder()
                .productId(dbSellerInventory.productId)
                .productName(dbSellerInventory.productName)
                .price(dbSellerInventory.productPrice)
                .quantity(customerCart.quantity)
                .build());
    }

    private void addRejectedProduct(DBSellerInventory dbSellerInventory, DBCustomerCart customerCart) {
        if (checkoutCustomerCartResponse.getRejectedProducts() == null)
            checkoutCustomerCartResponse.setRejectedProducts(new ArrayList<Product>());
        checkoutCustomerCartResponse.getRejectedProducts().add(Product.builder()
                .productId(dbSellerInventory.productId)
                .productName(dbSellerInventory.productName)
                .price(dbSellerInventory.productPrice)
                .quantity(customerCart.quantity)
                .build());
    }
}
