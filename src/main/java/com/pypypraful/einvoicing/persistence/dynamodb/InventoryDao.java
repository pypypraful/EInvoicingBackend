package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;

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

    public void updateUserProfileInDB(DBUserProfile dbUserProfile)
            throws ConditionalCheckFailedException {
        dynamoDBMapper.save(dbUserProfile);
    }

    public DBUserProfile getUserProfileFromDB(String username)
            throws ConditionalCheckFailedException {
        return dynamoDBMapper.load(DBUserProfile.class, username);
    }
}
