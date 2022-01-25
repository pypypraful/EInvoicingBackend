package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.CUSTOMER_CART_TABLE_NAME;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = CUSTOMER_CART_TABLE_NAME)
public class DBCustomerCart {

    @DynamoDBHashKey(attributeName = "customerId")
    public String customerId;

    @DynamoDBRangeKey(attributeName = "productId")
    public String productId;

    @DynamoDBAttribute(attributeName = "quantity")
    public Integer quantity;
}
