package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBBusinessProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBCustomerProfile;
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
    @DynamoDBHashKey(attributeName = "username")
    private String username;

    @DynamoDBAttribute(attributeName = "businessProfile")
    private DBBusinessProfile businessProfile;

    @DynamoDBAttribute(attributeName = "customerProfile")
    private DBCustomerProfile customerProfile;
}
