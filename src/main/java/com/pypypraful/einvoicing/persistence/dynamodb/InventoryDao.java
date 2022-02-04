package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.Delete;
import com.amazonaws.services.dynamodbv2.model.Put;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItem;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.Update;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBWorkflowMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.*;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder.CUSTOMER_ID_ORDER_ID_INDEX;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory.PRODUCT_ID;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory.USERNAME_CATEGORY_INDEX;
import static com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile.PINCODE_PROFILE_TYPE_INDEX;

public class InventoryDao {

    private final DynamoDBMapper dynamoDBMapper;
    private final AmazonDynamoDB dynamoDBDocumentClient;
    private final Map<String, AttributeValue> eav = new HashMap<>();

    public InventoryDao(){
        dynamoDBMapper = DynamoDBClient.getDynamoDBMapperClient();
        dynamoDBDocumentClient = DynamoDBClient.getDynamoDbClient();
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
        eav.put(VAL_1, new AttributeValue().withS(username));
        eav.put(VAL_2, new AttributeValue().withS(profileType));
        DynamoDBQueryExpression<DBUserProfile> queryExpression = new DynamoDBQueryExpression<DBUserProfile>()
                .withKeyConditionExpression(USERNAME + EQUALS + VAL_1 + AND + PROFILE_TYPE + EQUALS + VAL_2)
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBUserProfile.class, queryExpression);
    }

    public List<DBUserProfile> getUserProfilesByPincodeFromDB(Integer pincode, String profileType)
            throws ConditionalCheckFailedException {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withN(pincode.toString()));
        eav.put(VAL_2, new AttributeValue().withS(profileType));
        DynamoDBQueryExpression<DBUserProfile> queryExpression = new DynamoDBQueryExpression<DBUserProfile>()
                .withIndexName(PINCODE_PROFILE_TYPE_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression(PINCODE + EQUALS + VAL_1 + AND + PROFILE_TYPE + EQUALS + VAL_2)
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBUserProfile.class, queryExpression);
    }

    public void updateProductInCustomerCart(DBCustomerCart customerCart) throws ConditionalCheckFailedException {
        dynamoDBMapper.save(customerCart);
    }

    public void removeProductFromSellerInventoryAndAddToOrder(DBOrder dbOrder)
            throws ConditionalCheckFailedException{
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withN(dbOrder.quantity.toString()));
        Map<String, AttributeValue> sellerInventoryPrimaryKey = new HashMap<>();
        sellerInventoryPrimaryKey.put(PRODUCT_ID, new AttributeValue(dbOrder.productId));
        Update update = new Update()
                .withTableName(SELLER_INVENTORY_TABLE_NAME)
                .withKey(sellerInventoryPrimaryKey)
                .withUpdateExpression(SET + PRODUCT_QUANTITY + EQUALS + PRODUCT_QUANTITY + SUBTRACT + VAL_1)
                .withExpressionAttributeValues(eav);
        Map<String, AttributeValue> dbOrderKey = new HashMap<>();
        dbOrderKey.put(ORDER_ID, new AttributeValue(dbOrder.orderId));
        dbOrderKey.put(PRODUCT_ID, new AttributeValue(dbOrder.productId));
        dbOrderKey.put("sellerId", new AttributeValue(dbOrder.sellerId));
        dbOrderKey.put("price", new AttributeValue().withN(dbOrder.price.toString()));
        dbOrderKey.put(CUSTOMER_ID, new AttributeValue(dbOrder.customerId));
        dbOrderKey.put("orderStatus", new AttributeValue(dbOrder.orderStatus));
        dbOrderKey.put("quantity", new AttributeValue().withN(dbOrder.quantity.toString()));
        dbOrderKey.put("createdDate", new AttributeValue().withN(String.valueOf(System.currentTimeMillis())));
        dbOrderKey.put("lastUpdateDate", new AttributeValue().withN(String.valueOf(System.currentTimeMillis())));
        Put put = new Put()
                .withTableName(ORDER_TABLE_NAME)
                .withItem(dbOrderKey);
        Collection<TransactWriteItem> actions = Arrays.asList(
                new TransactWriteItem().withUpdate(update),
                new TransactWriteItem().withPut(put));
        TransactWriteItemsRequest placeOrderTransaction = new TransactWriteItemsRequest()
                .withTransactItems(actions)
                .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
        dynamoDBDocumentClient.transactWriteItems(placeOrderTransaction);
    }

    public void removeProductFromOrderAndAddToInventory(DBOrder dbOrder)
            throws ConditionalCheckFailedException {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withN(dbOrder.quantity.toString()));
        Map<String, AttributeValue> sellerInventoryPrimaryKey = new HashMap<>();
        sellerInventoryPrimaryKey.put(PRODUCT_ID, new AttributeValue(dbOrder.productId));
        Update update = new Update()
                .withTableName(SELLER_INVENTORY_TABLE_NAME)
                .withKey(sellerInventoryPrimaryKey)
                .withUpdateExpression(SET + PRODUCT_QUANTITY + EQUALS + PRODUCT_QUANTITY + ADD + VAL_1)
                .withExpressionAttributeValues(eav);
        Map<String, AttributeValue> orderPrimaryKey = new HashMap<>();
        orderPrimaryKey.put(ORDER_ID, new AttributeValue(dbOrder.orderId));
        orderPrimaryKey.put(PRODUCT_ID, new AttributeValue(dbOrder.productId));
        Delete delete = new Delete()
                .withTableName(ORDER_TABLE_NAME)
                .withKey(orderPrimaryKey);
        Collection<TransactWriteItem> actions = Arrays.asList(
                new TransactWriteItem().withUpdate(update),
                new TransactWriteItem().withDelete(delete));
        TransactWriteItemsRequest placeOrderTransaction = new TransactWriteItemsRequest()
                .withTransactItems(actions)
                .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
        dynamoDBDocumentClient.transactWriteItems(placeOrderTransaction);
    }

    public void updateOrderStatus(DBOrder dbOrder) {
        final Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withS(dbOrder.orderId));
        final DynamoDBQueryExpression<DBOrder> queryExpression = new DynamoDBQueryExpression<DBOrder>()
                .withKeyConditionExpression(ORDER_ID + EQUALS + VAL_1)
                .withExpressionAttributeValues(eav);
        final PaginatedQueryList<DBOrder> dbOrders = dynamoDBMapper.query(DBOrder.class, queryExpression);
        TransactionWriteRequest request = new TransactionWriteRequest();
        for (DBOrder order : dbOrders) {
            order.setOrderStatus(dbOrder.getOrderStatus());
            request.addUpdate(order);
        }
        dynamoDBMapper.transactionWrite(request);
    }

    public List<DBOrder> getDBOrderByOrderIdAndCustomerId(String orderId, String customerId)
            throws ConditionalCheckFailedException {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withS(customerId));
        eav.put(VAL_2, new AttributeValue().withS(orderId));
        DynamoDBQueryExpression<DBOrder> queryExpression = new DynamoDBQueryExpression<DBOrder>()
                .withIndexName(CUSTOMER_ID_ORDER_ID_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression(CUSTOMER_ID + EQUALS + VAL_1 + AND + ORDER_ID + EQUALS + VAL_2)
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBOrder.class, queryExpression);

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

    public void storeWorkflowMetadata(DBWorkflowMetadata dbWorkflowMetadata)
            throws ConditionalCheckFailedException {
        dynamoDBMapper.save(dbWorkflowMetadata, new DynamoDBMapperConfig(UPDATE_SKIP_NULL_ATTRIBUTES));
    }

    public List<DBWorkflowMetadata> getWorkflowMetadata(String executionName)
            throws ConditionalCheckFailedException {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(VAL_1, new AttributeValue().withS(executionName));
        DynamoDBQueryExpression<DBWorkflowMetadata> queryExpression = new DynamoDBQueryExpression<DBWorkflowMetadata>()
                .withKeyConditionExpression(EXECUTION_NAME + EQUALS + VAL_1)
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(DBWorkflowMetadata.class, queryExpression);
    }
}
