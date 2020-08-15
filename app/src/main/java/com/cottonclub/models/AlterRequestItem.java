package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AlterRequestItem implements Parcelable {
    private String alterId;
    private String alterRequestDate;
    private String alterRequestCreationDate;
    private String brandName;
    private String designCode;
    private String designNumber;
    private String alterQuantity;
    private String selectedParts;
    private String otherParts;
    private String master;
    private String totalPieces;
    private String cuttingIssueDate;
    private String selectSize;
    private SizeListItem sizeListItem;
    private String isUpdatedByCuttingInCharge;

    private String cuttingInChargeId;
    private String cuttingCompleteDate;
    private String wastage;
    private String wastageUnit;
    private String jobCardUpdatedByCuttingInChargeDate;
    private FabricListItem fabricListItem;

    public AlterRequestItem(Parcel in) {
        alterId = in.readString();
        alterRequestDate = in.readString();
        alterRequestCreationDate = in.readString();
        brandName = in.readString();
        designCode = in.readString();
        designNumber = in.readString();
        alterQuantity = in.readString();
        selectedParts = in.readString();
        otherParts = in.readString();
        master = in.readString();
        totalPieces = in.readString();
        cuttingIssueDate = in.readString();
        selectSize = in.readString();
        isUpdatedByCuttingInCharge = in.readString();
        sizeListItem = in.readParcelable(SizeListItem.class.getClassLoader());
    }

    public AlterRequestItem() {
    }

    public static final Creator<AlterRequestItem> CREATOR = new Creator<AlterRequestItem>() {
        @Override
        public AlterRequestItem createFromParcel(Parcel in) {
            return new AlterRequestItem(in);
        }

        @Override
        public AlterRequestItem[] newArray(int size) {
            return new AlterRequestItem[size];
        }
    };

    public String getAlterRequestCreationDate() {
        return alterRequestCreationDate;
    }

    public void setAlterRequestCreationDate(String alterRequestCreationDate) {
        this.alterRequestCreationDate = alterRequestCreationDate;
    }

    public String getDesignNumber() {
        return designNumber;
    }

    public void setDesignNumber(String designNumber) {
        this.designNumber = designNumber;
    }

    public String getAlterQuantity() {
        return alterQuantity;
    }

    public void setAlterQuantity(String alterQuantity) {
        this.alterQuantity = alterQuantity;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getCuttingIssueDate() {
        return cuttingIssueDate;
    }

    public void setCuttingIssueDate(String cuttingIssueDate) {
        this.cuttingIssueDate = cuttingIssueDate;
    }

    public String getAlterId() {
        return alterId;
    }

    public void setAlterId(String alterId) {
        this.alterId = alterId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesignCode() {
        return designCode;
    }

    public void setDesignCode(String designCode) {
        this.designCode = designCode;
    }

    public SizeListItem getSizeListItem() {
        return sizeListItem;
    }

    public void setSizeListItem(SizeListItem sizeListItem) {
        this.sizeListItem = sizeListItem;
    }

    public String getSelectedParts() {
        return selectedParts;
    }

    public void setSelectedParts(String selectedParts) {
        this.selectedParts = selectedParts;
    }

    public String getOtherParts() {
        return otherParts;
    }

    public void setOtherParts(String otherParts) {
        this.otherParts = otherParts;
    }

    public String getAlterRequestDate() {
        return alterRequestDate;
    }

    public void setAlterRequestDate(String alterRequestDate) {
        this.alterRequestDate = alterRequestDate;
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
        parcel.writeString(alterId);
        parcel.writeString(alterRequestDate);
        parcel.writeString(alterRequestCreationDate);
        parcel.writeString(brandName);
        parcel.writeString(designCode);
        parcel.writeString(designNumber);
        parcel.writeString(alterQuantity);
        parcel.writeString(selectedParts);
        parcel.writeString(otherParts);
        parcel.writeString(master);
        parcel.writeString(totalPieces);
        parcel.writeString(cuttingIssueDate);
        parcel.writeString(selectSize);
        parcel.writeString(isUpdatedByCuttingInCharge);
        parcel.writeParcelable(sizeListItem, i);
    }

    public String getSelectSize() {
        return selectSize;
    }

    public void setSelectSize(String selectSize) {
        this.selectSize = selectSize;
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

    public String getJobCardUpdatedByCuttingInChargeDate() {
        return jobCardUpdatedByCuttingInChargeDate;
    }

    public void setJobCardUpdatedByCuttingInChargeDate(String jobCardUpdatedByCuttingInChargeDate) {
        this.jobCardUpdatedByCuttingInChargeDate = jobCardUpdatedByCuttingInChargeDate;
    }

    public FabricListItem getFabricListItem() {
        return fabricListItem;
    }

    public void setFabricListItem(FabricListItem fabricListItem) {
        this.fabricListItem = fabricListItem;
    }

    public String getIsUpdatedByCuttingInCharge() {
        return isUpdatedByCuttingInCharge;
    }

    public void setIsUpdatedByCuttingInCharge(String isUpdatedByCuttingInCharge) {
        this.isUpdatedByCuttingInCharge = isUpdatedByCuttingInCharge;
    }
}
