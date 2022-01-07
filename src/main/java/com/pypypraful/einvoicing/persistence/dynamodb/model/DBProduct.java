package com.pypypraful.einvoicing.persistence.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.pypypraful.einvoicing.model.common.Product;

@DynamoDBDocument
public class DBProduct {

    @DynamoDBAttribute(attributeName = "productName")
    public String productName;

    @DynamoDBAttribute(attributeName = "Quantity")
    public Integer Quantity;

    @DynamoDBAttribute(attributeName = "price")
    public Double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DBProduct toDBProduct(Product product) {
        DBProduct dbProduct = new DBProduct();
        dbProduct.setProductName(product.getProductName());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setQuantity(product.getQuantity());
        return dbProduct;
    }
}
