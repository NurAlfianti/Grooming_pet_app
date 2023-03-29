package com.androidlaika.groomingpets.Models;

public class MainModel {
    int mainImage;
    String mainGroomingPacket, mainPrice, mainDescription;

    // Constructor
    public MainModel(int mainImage, String mainGroomingPacket, String mainPrice, String mainDescription) {
        this.mainImage = mainImage;
        this.mainGroomingPacket = mainGroomingPacket;
        this.mainPrice = mainPrice;
        this.mainDescription = mainDescription;
    }

    // Getters and Setters methods
    public int getMainImage() {

        return mainImage;
    }

    public void setMainImage(int mainImage) {

        this.mainImage = mainImage;
    }

    public String getMainGroomingPacket() {

        return mainGroomingPacket;
    }

    public void setMainGroomingPacket(String mainGroomingPacket) {

        this.mainGroomingPacket = mainGroomingPacket;
    }

    public String getMainPrice() {

        return mainPrice;
    }

    public void setMainPrice(String mainPrice) {

        this.mainPrice = mainPrice;
    }

    public String getMainDescription() {

        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

}
