package com.pypypraful.einvoicing.persistence.dynamodb.model.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class DBCustomerProfile {

    @DynamoDBAttribute(attributeName = "customerName")
    private String customerName;

    @DynamoDBAttribute(attributeName = "homeAddress")
    private DBAddress homeAddress;

    @DynamoDBAttribute(attributeName = "customerPhoneNumber")
    private String customerPhoneNumber;
}
