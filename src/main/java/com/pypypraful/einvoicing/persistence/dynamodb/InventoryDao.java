package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBInventory;

public class InventoryDao {

    private final DynamoDBMapper dynamoDBMapper;

    public InventoryDao(){
        this.dynamoDBMapper = DynamoDBClient.getDynamoDbClient();
    }

    public void persistData(DBInventory dbInventory)
            throws ConditionalCheckFailedException {
        dynamoDBMapper.save(dbInventory);
    }

    public DBInventory getData(DBInventory inventory)
            throws ConditionalCheckFailedException {
        return dynamoDBMapper.load(DBInventory.class, inventory.getUsername());
    }
}
