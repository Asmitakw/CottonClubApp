package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class JobCardItem implements Parcelable {
    private String jobCardId;
    private String jobCardCreateDate;
    private String brand;
    private String jobCardNumber;
    private String designCode;
    private String designNumber;
    private String quantity;
    private String size;
    private String fabricType;
    private String fabricUnit;
    private String masterName;
    private String totalPieces;
    private String cuttingIssueDate;
    private String jobCardDate;
    private String cuttingInchargeNumber;
    private SizeListItem sizeItem;
    private String jobCardFilePath;
    private String isUpdatedByCuttingInCharge;

    private String cuttingInChargeId;
    private String cuttingCompleteDate;
    private String wastage;
    private String wastageUnit;
    private String jobCardUpdatedByCuttingInChargeDate;
    private FabricListItem fabricListItem;

    private String jobCardUpdatedByAdminDate;

    protected JobCardItem(Parcel in) {
        jobCardId = in.readString();
        jobCardCreateDate = in.readString();
        brand = in.readString();
        jobCardNumber = in.readString();
        designCode = in.readString();
        designNumber = in.readString();
        quantity = in.readString();
        size = in.readString();
        fabricType = in.readString();
        fabricUnit = in.readString();
        masterName = in.readString();
        totalPieces = in.readString();
        cuttingIssueDate = in.readString();
        jobCardDate = in.readString();
        cuttingInchargeNumber = in.readString();
        sizeItem = in.readParcelable(SizeListItem.class.getClassLoader());
        fabricListItem = in.readParcelable(FabricListItem.class.getClassLoader());
        jobCardFilePath = in.readString();
        isUpdatedByCuttingInCharge = in.readString();
        wastage = in.readString();
        wastageUnit = in.readString();
        cuttingCompleteDate = in.readString();
        jobCardUpdatedByAdminDate = in.readString();
    }

    public JobCardItem() {
    }

    public static final Creator<JobCardItem> CREATOR = new Creator<JobCardItem>() {
        @Override
        public JobCardItem createFromParcel(Parcel in) {
            return new JobCardItem(in);
        }

        @Override
        public JobCardItem[] newArray(int size) {
            return new JobCardItem[size];
        }
    };

    public String getJobCardCreateDate() {
        return jobCardCreateDate;
    }

    public void setJobCardCreateDate(String jobCardCreateDate) {
        this.jobCardCreateDate = jobCardCreateDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getJobCardNumber() {
        return jobCardNumber;
    }

    public void setJobCardNumber(String jobCardNumber) {
        this.jobCardNumber = jobCardNumber;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getCuttingIssueDate() {
        return cuttingIssueDate;
    }

    public void setCuttingIssueDate(String cuttingIssueDate) {
        this.cuttingIssueDate = cuttingIssueDate;
    }

    public String getDesignNumber() {
        return designNumber;
    }

    public void setDesignNumber(String designNumber) {
        this.designNumber = designNumber;
    }

    public String getJobCardDate() {
        return jobCardDate;
    }

    public void setJobCardDate(String jobCardDate) {
        this.jobCardDate = jobCardDate;
    }

    public String getCuttingInchargeNumber() {
        return cuttingInchargeNumber;
    }

    public void setCuttingInchargeNumber(String cuttingInchargeNumber) {
        this.cuttingInchargeNumber = cuttingInchargeNumber;
    }

    public String getDesignCode() {
        return designCode;
    }

    public void setDesignCode(String designCode) {
        this.designCode = designCode;
    }

    public SizeListItem getSizeItem() {
        return sizeItem;
    }

    public void setSizeItem(SizeListItem sizeItem) {
        this.sizeItem = sizeItem;
    }

    public String getFabricUnit() {
        return fabricUnit;
    }

    public void setFabricUnit(String fabricUnit) {
        this.fabricUnit = fabricUnit;
    }

    public String getJobCardFilePath() {
        return jobCardFilePath;
    }

    public void setJobCardFilePath(String jobCardFilePath) {
        this.jobCardFilePath = jobCardFilePath;
    }

    public String getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(String totalPieces) {
        this.totalPieces = totalPieces;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(jobCardId);
        parcel.writeString(jobCardCreateDate);
        parcel.writeString(brand);
        parcel.writeString(jobCardNumber);
        parcel.writeString(designCode);
        parcel.writeString(designNumber);
        parcel.writeString(quantity);
        parcel.writeString(size);
        parcel.writeString(fabricType);
        parcel.writeString(fabricUnit);
        parcel.writeString(masterName);
        parcel.writeString(totalPieces);
        parcel.writeString(cuttingIssueDate);
        parcel.writeString(jobCardDate);
        parcel.writeString(cuttingInchargeNumber);
        parcel.writeParcelable(sizeItem, i);
        parcel.writeParcelable(fabricListItem, i);
        parcel.writeString(jobCardFilePath);
        parcel.writeString(isUpdatedByCuttingInCharge);
        parcel.writeString(wastage);
        parcel.writeString(wastageUnit);
        parcel.writeString(cuttingCompleteDate);
        parcel.writeString(jobCardUpdatedByAdminDate);
    }

    public String getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(String jobCardId) {
        this.jobCardId = jobCardId;
    }

    public String getCuttingInChargeId() {
        return cuttingInChargeId;
    }

    public void setCuttingInChargeId(String cuttingInChargeId) {
        this.cuttingInChargeId = cuttingInChargeId;
    }

    public String getCuttingCompleteDate() {
        return cuttingCompleteDate;
    }

    public void setCuttingCompleteDate(String cuttingCompleteDate) {
        this.cuttingCompleteDate = cuttingCompleteDate;
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

    public FabricListItem getFabricListItem() {
        return fabricListItem;
    }

    public void setFabricListItem(FabricListItem fabricListItem) {
        this.fabricListItem = fabricListItem;
    }

    public String getJobCardUpdatedByCuttingInChargeDate() {
        return jobCardUpdatedByCuttingInChargeDate;
    }

    public void setJobCardUpdatedByCuttingInChargeDate(String jobCardUpdatedByCuttingInChargeDate) {
        this.jobCardUpdatedByCuttingInChargeDate = jobCardUpdatedByCuttingInChargeDate;
    }

    public String getIsUpdatedByCuttingInCharge() {
        return isUpdatedByCuttingInCharge;
    }

    public void setIsUpdatedByCuttingInCharge(String isUpdatedByCuttingInCharge) {
        this.isUpdatedByCuttingInCharge = isUpdatedByCuttingInCharge;
    }

    public String getJobCardUpdatedByAdminDate() {
        return jobCardUpdatedByAdminDate;
    }

    public void setJobCardUpdatedByAdminDate(String jobCardUpdatedByAdminDate) {
        this.jobCardUpdatedByAdminDate = jobCardUpdatedByAdminDate;
    }
}
