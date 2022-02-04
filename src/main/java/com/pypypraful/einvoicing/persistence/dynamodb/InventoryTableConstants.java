package com.pypypraful.einvoicing.persistence.dynamodb;

public class InventoryTableConstants {

    public final static String SELLER_INVENTORY_TABLE_NAME = "SellerInventory";
    public final static String USER_PROFILE_TABLE_NAME = "UserProfile";
    public final static String CUSTOMER_CART_TABLE_NAME = "CustomerCart";
    public final static String WORKFLOW_METADATA_TABLE_NAME = "WorkflowMetadata";
    public final static String ORDER_TABLE_NAME = "Order";

    public static final String PRODUCT_QUANTITY = "productQuantity";
    public static final String USERNAME = "username";
    public static final String PROFILE_TYPE = "profileType";
    public static final String PINCODE = "pincode";
    public static final String ORDER_ID = "orderId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String ORDER_STATUS = "orderStatus";

    public static final String VAL_1 = ":val1";
    public static final String VAL_2 = ":val2";
    public static final String AND = " and ";
    public static final String GREATER_THAN_EQUALS = " >= ";
    public static final String EQUALS = " = ";
    public static final String ADD = " + ";
    public static final String SUBTRACT = " - ";
    public static final String SET = " SET ";

    public static final String EXECUTION_NAME = "executionName";
    public static final String EXECUTION_ARN = "executionArn";
}
