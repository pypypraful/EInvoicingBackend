package com.pypypraful.einvoicing.model.common;

import com.pypypraful.einvoicing.persistence.dynamodb.model.DBProduct;

public class Product {
    private String productName;
    private Integer quantity;
    private Double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product toUserProduct(DBProduct dbProduct){
        Product product = new Product();
        product.setProductName(dbProduct.getProductName());
        product.setQuantity(dbProduct.getQuantity());
        product.setPrice(dbProduct.getPrice());
        return product;
    }
}
