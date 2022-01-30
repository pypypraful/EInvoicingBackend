package com.pypypraful.einvoicing.model.enums;

public enum OrderStatus {
    CHECKOUT("CHECKOUT"), ORDERED("ORDERED"), CONFIRMED("CONFIRMED"),
    SHIPPED("SHIPPED"), IN_TRANSIT("IN_TRANSIT"), DELIVERED("DELIVERED");

    private final String orderStatus;

    OrderStatus(final String orderStatus) { this.orderStatus = orderStatus; }

    public String toString() { return this.orderStatus; }
}
