package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.PRODUCT_QUANTITY;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.SELLER_INVENTORY_TABLE_NAME;

@Builder
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = SELLER_INVENTORY_TABLE_NAME)
public class DBSellerInventory {

    public static final String CATEGORY_SUB_CATEGORY_INDEX = "productCategory-productSubCategory-index";
    public static final String USERNAME_CATEGORY_INDEX = "username-productCategory-index";

    public static final String PRODUCT_ID = "productId";

    @DynamoDBIndexHashKey(
            attributeName = "username",
            globalSecondaryIndexName = USERNAME_CATEGORY_INDEX)
    public String username;

    @DynamoDBAttribute(attributeName = "productDescription")
    public String productDescription;

    @DynamoDBAttribute(attributeName = PRODUCT_QUANTITY)
    public Integer productQuantity;

    @DynamoDBAttribute(attributeName = "productSubCategory")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = CATEGORY_SUB_CATEGORY_INDEX)
    public String productSubCategory;

    @DynamoDBIndexHashKey(
            attributeName = "productCategory",
            globalSecondaryIndexName = CATEGORY_SUB_CATEGORY_INDEX)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = USERNAME_CATEGORY_INDEX)
    public String productCategory;

    @DynamoDBAttribute(attributeName = "productPrice")
    public Double productPrice;

    @DynamoDBAttribute(attributeName = "productWeightPerUnitInGrams")
    public Integer productWeightPerUnitInGrams;

    @DynamoDBHashKey(attributeName = PRODUCT_ID)
    @DynamoDBAutoGeneratedKey
    public String productId;

    @DynamoDBAttribute(attributeName = "productName")
    public String productName;
}
