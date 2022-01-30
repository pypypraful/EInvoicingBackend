package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBClientAdditionalDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.PINCODE;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.PROFILE_TYPE;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.USERNAME;
import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.USER_PROFILE_TABLE_NAME;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamoDBTable(tableName = USER_PROFILE_TABLE_NAME)
public class DBUserProfile {

    public static final String PINCODE_PROFILE_TYPE_INDEX = "pincode-profileType-index";

    @DynamoDBHashKey(attributeName = USERNAME)
    private String username;

    @DynamoDBRangeKey(attributeName = PROFILE_TYPE)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = PINCODE_PROFILE_TYPE_INDEX)
    private String profileType;

    @DynamoDBIndexHashKey(attributeName = PINCODE,
            globalSecondaryIndexName = PINCODE_PROFILE_TYPE_INDEX)
    private Integer pincode;

    @DynamoDBAttribute(attributeName = "clientAdditionalDetail")
    private DBClientAdditionalDetail clientAdditionalDetail;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "phoneNumber")
    private String phoneNumber;

    @DynamoDBAttribute(attributeName = "addressLine")
    private String addressLine;

    @DynamoDBAttribute(attributeName = "city")
    private String city;

    @DynamoDBAttribute(attributeName = "state")
    private String state;
}
