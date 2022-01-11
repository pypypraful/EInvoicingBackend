package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.pypypraful.einvoicing.model.enums.ProfileType;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBClientAdditionalDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.pypypraful.einvoicing.persistence.dynamodb.InventoryTableConstants.USER_PROFILE_TABLE_NAME;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = USER_PROFILE_TABLE_NAME)
public class DBUserProfile {

    public static final String PINCODE_INDEX = "pincode-index";
    public static final String PROFILE_TYPE = "profileType";
    public static final String USERNAME = "username";
    public static final String PINCODE = "pincode";

    @DynamoDBHashKey(attributeName = USERNAME)
    private String username;

    @DynamoDBIndexRangeKey(attributeName = PROFILE_TYPE)
    private String profileType;

    @DynamoDBIndexHashKey(attributeName = PINCODE,
            globalSecondaryIndexName = PINCODE_INDEX)
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
