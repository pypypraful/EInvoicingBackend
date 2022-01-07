package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.InventoryDao;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.InventoryDBAdapter;

public class DynamoDBInventoryRepository implements InventoryRepository{

    private final InventoryDao inventoryDao;
    private final InventoryDBAdapter inventoryDBAdapter;

    public DynamoDBInventoryRepository(){
        inventoryDao = new InventoryDao();
        inventoryDBAdapter = new InventoryDBAdapter();
    }

    public void updateInventoryRecord(UpdateInventoryForUserRequest updateInventoryForUserRequest) {
        inventoryDao.persistData(inventoryDBAdapter.convertInventoryToDBModel(updateInventoryForUserRequest));
    }

    public GetInventoryForUserResponse getInventoryRecord(GetInventoryForUserRequest getInventoryForUserRequest) {
        DBInventory dbInventory = inventoryDao.getData(inventoryDBAdapter.convertGetInventoryForUserRequestToDBModel(getInventoryForUserRequest));
        return inventoryDBAdapter.convertInventoryToUserResponse(dbInventory);
    }
}
