package com.androidlaika.groomingpets.Models;

public class OrderModel {
    int orderImage;
    String orderGroomingPacket, orderNumber, orderPrice;

    // Constructor
    public OrderModel(){

    }

    // Getters and Setters methods
    public int getOrderImage() {

        return orderImage;
    }

    public void setOrderImage(int orderImage) {

        this.orderImage = orderImage;
    }

    public String getOrderGroomingPacket() {

        return orderGroomingPacket;
    }

    public void setOrderGroomingPacket(String orderGroomingPacket) {

        this.orderGroomingPacket = orderGroomingPacket;
    }

    public String getOrderNumber() {

        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {

        this.orderNumber = orderNumber;
    }

    public String getOrderPrice() {

        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {

        this.orderPrice = orderPrice;
    }

}
