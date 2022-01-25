package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.SELLER_INVENTORY_TABLE_NAME;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory.CUSTOMER_ID;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory.PRODUCT_ID;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory.USERNAME_CATEGORY_INDEX;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile.PINCODE_PROFILE_TYPE_INDEX;

public class InventoryDao {

    private final DynamoDBMapper dynamoDBMapper;
    private final Map<String, AttributeValue> eav = new HashMap<>();

    public InventoryDao(){
        this.dynamoDBMapper = DynamoDBClient.getDynamoDbClient();
    }

    public void saveSellerInventory(List<DBSellerInventory> dbSellerInventories)
            throws ConditionalCheckFailedException {
        dynamoDBMapper.batchSave(dbSellerInventories);
    }

    public List<DBSellerInventory> getSellerInventoryByUsername(String username)
            throws ConditionalCheckFailedException {
        eav.put(":val1", new AttributeValue().withS(username));
        DynamoDBQueryExpression<DBSellerInventory> queryExpression = new DynamoDBQueryExpression<DBSellerInventory>()
                .withIndexName(USERNAME_CATEGORY_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("username = :val1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBSellerInventory.class, queryExpression);
    }

    public void updateUserProfileInDB(DBUserProfile dbUserProfile)
            throws ConditionalCheckFailedException {
        dynamoDBMapper.save(dbUserProfile);
    }

    public List<DBUserProfile> getUserProfileByUsernameFromDB(String username, String profileType)
            throws ConditionalCheckFailedException {
        eav.put(":val1", new AttributeValue().withS(username));
        eav.put(":val2", new AttributeValue().withS(profileType));
        DynamoDBQueryExpression<DBUserProfile> queryExpression = new DynamoDBQueryExpression<DBUserProfile>()
                .withKeyConditionExpression("username = :val1 and profileType = :val2")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBUserProfile.class, queryExpression);
    }

    public List<DBUserProfile> getUserProfilesByPincodeFromDB(Integer pincode, String profileType)
            throws ConditionalCheckFailedException {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(pincode.toString()));
        eav.put(":val2", new AttributeValue().withS(profileType));
        DynamoDBQueryExpression<DBUserProfile> queryExpression = new DynamoDBQueryExpression<DBUserProfile>()
                .withIndexName(PINCODE_PROFILE_TYPE_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("pincode = :val1 and profileType = :val2")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBUserProfile.class, queryExpression);
    }

    public void updateProductInCustomerCart(DBCustomerCart customerCart) throws ConditionalCheckFailedException {
        dynamoDBMapper.save(customerCart);
    }

    public void updateMultipleProductsInCustomerCart(List<DBCustomerCart> customerCart)
            throws ConditionalCheckFailedException {
        List<Object> objectsToWrite = new ArrayList<>();
        List<Object> objectsToDelete = new ArrayList<>();
        for (DBCustomerCart dbCustomerCart : customerCart) {
            if (dbCustomerCart.quantity == 0)
                objectsToDelete.add(dbCustomerCart);
            else
                objectsToWrite.add(dbCustomerCart);
        }
        dynamoDBMapper.batchWrite(objectsToWrite, objectsToDelete);
    }

    public List<DBSellerInventory> getSellerProductByProductId(String productId)
            throws ConditionalCheckFailedException {
        eav.put(":val1", new AttributeValue().withS(productId));
        DynamoDBQueryExpression<DBSellerInventory> queryExpression = new DynamoDBQueryExpression<DBSellerInventory>()
                .withKeyConditionExpression(PRODUCT_ID + " = :val1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBSellerInventory.class, queryExpression);
    }

    public List<Object> getSellerProductByMultipleProductIds(List<String> productIds)
            throws ConditionalCheckFailedException {
        if (productIds.size() == 0) return new ArrayList<>();
        ArrayList<Object> itemsToGet = new ArrayList<>();
        for (String productId : productIds) {
            itemsToGet.add(DBSellerInventory.builder().productId(productId).build());
        }
        return dynamoDBMapper.batchLoad(itemsToGet).get(SELLER_INVENTORY_TABLE_NAME);
    }

    public List<DBCustomerCart> getCustomerCartByCustomerId(String customerId)
            throws ConditionalCheckFailedException {
        eav.put(":val1", new AttributeValue().withS(customerId));
        DynamoDBQueryExpression<DBCustomerCart> queryExpression = new DynamoDBQueryExpression<DBCustomerCart>()
                .withKeyConditionExpression(CUSTOMER_ID + " = :val1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBCustomerCart.class, queryExpression);
    }
}
