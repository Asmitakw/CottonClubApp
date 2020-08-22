package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FabricListItem implements Parcelable {
    private String fabricCode;
    private String fabricQuantity;
    private String fabricUnit;
    private String isUpdated;

    protected FabricListItem(Parcel in) {
        fabricCode = in.readString();
        fabricQuantity = in.readString();
        fabricUnit = in.readString();
        isUpdated = in.readString();
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
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }
}
