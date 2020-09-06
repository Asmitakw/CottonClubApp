package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FabricListItem implements Parcelable {
    private String fabricCode;
    private String fabricQuantity;
    private String fabricUnit;
    private String isUpdated;
    private String wastage;
    private String wastageUnit;


    protected FabricListItem(Parcel in) {
        fabricCode = in.readString();
        fabricQuantity = in.readString();
        fabricUnit = in.readString();
        isUpdated = in.readString();
        wastage = in.readString();
        wastageUnit = in.readString();
    }

    public FabricListItem(){

    }

    public static final Creator<FabricListItem> CREATOR = new Creator<FabricListItem>() {
        @Override
        public FabricListItem createFromParcel(Parcel in) {
            return new FabricListItem(in);
        }

        @Override
        public FabricListItem[] newArray(int size) {
            return new FabricListItem[size];
        }
    };

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getFabricQuantity() {
        return fabricQuantity;
    }

    public void setFabricQuantity(String fabricQuantity) {
        this.fabricQuantity = fabricQuantity;
    }

    public String getFabricUnit() {
        return fabricUnit;
    }

    public void setFabricUnit(String fabricUnit) {
        this.fabricUnit = fabricUnit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fabricCode);
        parcel.writeString(fabricQuantity);
        parcel.writeString(fabricUnit);
        parcel.writeString(isUpdated);
        parcel.writeString(wastage);
        parcel.writeString(wastageUnit);
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getWastage() {
        return wastage;
    }

    public void setWastage(String wastage) {
        this.wastage = wastage;
    }

    public String getWastageUnit() {
        return wastageUnit;
    }

    public void setWastageUnit(String wastageUnit) {
        this.wastageUnit = wastageUnit;
    }
}
