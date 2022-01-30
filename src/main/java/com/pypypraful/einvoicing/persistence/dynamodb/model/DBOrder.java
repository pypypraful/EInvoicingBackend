package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedTimestamp;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.CUSTOMER_ID;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.ORDER_TABLE_NAME;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = ORDER_TABLE_NAME)
public class DBOrder {

    public static final String CUSTOMER_ID_ORDER_ID_INDEX = "customerId-orderId-index";
    public static final String SELLER_ID_ORDER_ID_INDEX = "sellerId-orderId-index";

    @DynamoDBHashKey(attributeName = "orderId")
    @DynamoDBIndexRangeKey(globalSecondaryIndexNames = {CUSTOMER_ID_ORDER_ID_INDEX, SELLER_ID_ORDER_ID_INDEX})
    public String orderId;

    @DynamoDBAttribute(attributeName = CUSTOMER_ID)
    @DynamoDBIndexHashKey(globalSecondaryIndexName = CUSTOMER_ID_ORDER_ID_INDEX)
    public String customerId;

    @DynamoDBAttribute(attributeName = "sellerId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = SELLER_ID_ORDER_ID_INDEX)
    public String sellerId;

    @DynamoDBRangeKey(attributeName = "productId")
    public String productId;

    @DynamoDBHashKey(attributeName = "quantity")
    public Integer quantity;

    @DynamoDBAttribute(attributeName = "price")
    public Double price;

    @DynamoDBAttribute(attributeName = "orderStatus")
    public String orderStatus;

    @DynamoDBAttribute(attributeName = "createdDate")
    @DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.CREATE)
    public Date createdDate;

    @DynamoDBAttribute(attributeName = "lastUpdateDate")
    @DynamoDBAutoGeneratedTimestamp(strategy= DynamoDBAutoGenerateStrategy.ALWAYS)
    public Date lastUpdatedDate;
}
