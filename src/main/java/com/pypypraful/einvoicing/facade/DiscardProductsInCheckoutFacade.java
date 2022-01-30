package com.pypypraful.einvoicing.facade;

import com.pypypraful.einvoicing.model.enums.OrderStatus;
import com.pypypraful.einvoicing.model.request.DiscardProductsInCheckoutRequest;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

import java.util.List;

public class DiscardProductsInCheckoutFacade {

    private final InventoryRepository inventoryRepository;

    public DiscardProductsInCheckoutFacade() {
        inventoryRepository = new DynamoDBInventoryRepository();
    }

    public void discardProductsInOrder(DiscardProductsInCheckoutRequest discardProductsInCheckoutRequest) {
        List<DBOrder> dbOrders = inventoryRepository.getDBOrderByOrderIdAndCustomerId(
                discardProductsInCheckoutRequest.getCustomerId(),
                discardProductsInCheckoutRequest.getOrderId());
        for (DBOrder dbOrder : dbOrders) {
            try {
                if (dbOrder.orderStatus.equals(OrderStatus.CHECKOUT.toString())) {
                    inventoryRepository.discardProductFromCheckout(dbOrder);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("The product with productId " + dbOrder
                        + " cannot be discarded!" + ex.getMessage());
            }
        }
    }
}
