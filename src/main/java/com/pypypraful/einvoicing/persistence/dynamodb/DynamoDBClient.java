package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBClient {

    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDB client;
    private static DynamoDB dynamoDBDocumentClient;

    public static AmazonDynamoDB getDynamoDbClient() {
        if (client == null) {
            client = AmazonDynamoDBClientBuilder.defaultClient();
        }
        return client;
    }

    public static DynamoDBMapper getDynamoDBMapperClient() {
        if (dynamoDBMapper == null) {
            dynamoDBMapper = new DynamoDBMapper(getDynamoDbClient());
        }
        return dynamoDBMapper;
    }

    public static DynamoDB getDynamoDBDocumentClient() {
        if (dynamoDBDocumentClient == null) {
            dynamoDBDocumentClient = new DynamoDB(getDynamoDbClient());
        }
        return dynamoDBDocumentClient;
    }
}
