package com.pypypraful.einvoicing.persistence.dynamodb.model.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class DBBusinessProfile {

    @DynamoDBAttribute(attributeName = "businessName")
    private String businessName;

    @DynamoDBAttribute(attributeName = "businessAddress")
    private DBAddress businessAddress;

    @DynamoDBAttribute(attributeName = "businessPhoneNumber")
    private String businessPhoneNumber;

    @DynamoDBAttribute(attributeName = "panNumber")
    private String panNumber;

    @DynamoDBAttribute(attributeName = "gstIN")
    private String gstIN;
}
