package com.pypypraful.einvoicing.persistence.dynamodb;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBClient {

    private final static String REGION = "ap-south-1";
    private static DynamoDBMapper dynamoDBMapper;

    public static DynamoDBMapper getDynamoDbClient() {
        if (dynamoDBMapper == null) {
            AmazonDynamoDBClient client = new AmazonDynamoDBClient();
            client.setRegion(RegionUtils.getRegion(REGION));
            dynamoDBMapper = new DynamoDBMapper(client);
        }
        return dynamoDBMapper;
    }
}
