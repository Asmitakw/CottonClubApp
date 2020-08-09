package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderItem implements Parcelable {
    private String orderId;
    private String orderCreationDate;
    private String partyName;
    private String brandName;
    private String designCode;
    private String designNumber;
    private String orderNumber;
    private String deliveryDate;
    private String selectSize;
    private String orderDate;
    private String quantity;
    private String totalPieces;
    private String type;
    private SizeListItem sizeItem;

    public OrderItem(Parcel in) {
        orderId = in.readString();
        orderCreationDate = in.readString();
        partyName = in.readString();
        brandName = in.readString();
        designNumber = in.readString();
        orderNumber = in.readString();
        deliveryDate = in.readString();
        selectSize = in.readString();
        orderDate = in.readString();
        quantity = in.readString();
        totalPieces = in.readString();
        type = in.readString();
    }

    public OrderItem() {
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(String totalPieces) {
        this.totalPieces = totalPieces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SizeListItem getSizeItem() {
        return sizeItem;
    }

    public void setSizeItem(SizeListItem sizeItem) {
        this.sizeItem = sizeItem;
    }

    @NonNull
    public String toString(){
        return partyName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderId);
        parcel.writeString(orderCreationDate);
        parcel.writeString(partyName);
        parcel.writeString(brandName);
        parcel.writeString(designNumber);
        parcel.writeString(orderNumber);
        parcel.writeString(deliveryDate);
        parcel.writeString(selectSize);
        parcel.writeString(orderDate);
        parcel.writeString(quantity);
        parcel.writeString(totalPieces);
        parcel.writeString(type);
    }

    public String getDesignCode() {
        return designCode;
    }

    public void setDesignCode(String designCode) {
        this.designCode = designCode;
    }
}
