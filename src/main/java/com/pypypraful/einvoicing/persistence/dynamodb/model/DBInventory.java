package com.pypypraful.einvoicing.persistence.dynamodb.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.INVENTORY_TABLE_NAME;

@DynamoDBTable(tableName = INVENTORY_TABLE_NAME)
public class DBInventory {

    @DynamoDBHashKey(attributeName = "username")
    public String username;

    @DynamoDBAttribute(attributeName = "inventory")
    public List<DBProduct> inventory;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DBProduct> getInventory() {
        return inventory;
    }

    public void setInventory(List<DBProduct> inventory) {
        this.inventory = inventory;
    }
}
