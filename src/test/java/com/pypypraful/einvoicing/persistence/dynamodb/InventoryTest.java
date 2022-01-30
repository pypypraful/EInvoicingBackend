package com.pypypraful.einvoicing.persistence.dynamodb;

import com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder;
import org.junit.Ignore;
import org.junit.Test;

public class InventoryTest {

    @Test
    @Ignore
    public void testInventoryDao() {
        InventoryDao inventoryDao = new InventoryDao();
        inventoryDao.removeProductFromOrderAndAddToInventory(DBOrder.builder().quantity(2).productId("3456").orderId("1234").build());
    }
}
