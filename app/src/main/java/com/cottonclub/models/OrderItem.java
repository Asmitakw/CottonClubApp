package com.cottonclub.models;

public class OrderItem {
    private String orderCreationDate;
    private String partyName;
    private String brandName;
    private String designNumber;
    private String orderNumber;
    private String deliveryDate;
    private String selectSize;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesignNumber() {
        return designNumber;
    }

    public void setDesignNumber(String designNumber) {
        this.designNumber = designNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSelectSize() {
        return selectSize;
    }

    public void setSelectSize(String selectSize) {
        this.selectSize = selectSize;
    }

    public String getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(String orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }
}
