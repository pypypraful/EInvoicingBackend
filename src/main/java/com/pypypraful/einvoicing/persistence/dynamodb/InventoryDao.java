package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
