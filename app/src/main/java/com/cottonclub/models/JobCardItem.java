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
    private String totalFabricConsumed;
    private String totalWastage;


    private String productionManagerId;
    private String printerName;
    private String printerIssueDate;
    private String selectParts;
    private String otherParts;
    private String printerReceiveDate;
    private String printingReceiveQuantity;
    private String approvedQuantityToEmbroidery;
    private String currentAlterQuantityAfterPrinting;
    private String embroiderName;
    private String receivedQuantityToEmbroider;
    private String approvedQuantityIssuedToMaker;
    private String currentAlterQuantityAfterEmbroidery;
    private String makerName;
    private String makerIssueDate;
    private String isUpdatedByProductionManager;
    private String note;


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
        totalFabricConsumed = in.readString();
        totalWastage = in.readString();
        isUpdatedByProductionManager = in.readString();
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


    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterIssueDate() {
        return printerIssueDate;
    }

    public void setPrinterIssueDate(String printerIssueDate) {
        this.printerIssueDate = printerIssueDate;
    }

    public String getSelectParts() {
        return selectParts;
    }

    public void setSelectParts(String selectParts) {
        this.selectParts = selectParts;
    }

    public String getPrinterReceiveDate() {
        return printerReceiveDate;
    }

    public void setPrinterReceiveDate(String printerReceiveDate) {
        this.printerReceiveDate = printerReceiveDate;
    }

    public String getPrintingReceiveQuantity() {
        return printingReceiveQuantity;
    }

    public void setPrintingReceiveQuantity(String printingReceiveQuantity) {
        this.printingReceiveQuantity = printingReceiveQuantity;
    }

    public String getApprovedQuantityToEmbroidery() {
        return approvedQuantityToEmbroidery;
    }

    public void setApprovedQuantityToEmbroidery(String approvedQuantityToEmbroidery) {
        this.approvedQuantityToEmbroidery = approvedQuantityToEmbroidery;
    }

    public String getCurrentAlterQuantityAfterPrinting() {
        return currentAlterQuantityAfterPrinting;
    }

    public void setCurrentAlterQuantityAfterPrinting(String currentAlterQuantityAfterPrinting) {
        this.currentAlterQuantityAfterPrinting = currentAlterQuantityAfterPrinting;
    }

    public String getEmbroiderName() {
        return embroiderName;
    }

    public void setEmbroiderName(String embroiderName) {
        this.embroiderName = embroiderName;
    }

    public String getReceivedQuantityToEmbroider() {
        return receivedQuantityToEmbroider;
    }

    public void setReceivedQuantityToEmbroider(String receivedQuantityToEmbroider) {
        this.receivedQuantityToEmbroider = receivedQuantityToEmbroider;
    }

    public String getApprovedQuantityIssuedToMaker() {
        return approvedQuantityIssuedToMaker;
    }

    public void setApprovedQuantityIssuedToMaker(String approvedQuantityIssuedToMaker) {
        this.approvedQuantityIssuedToMaker = approvedQuantityIssuedToMaker;
    }

    public String getCurrentAlterQuantityAfterEmbroidery() {
        return currentAlterQuantityAfterEmbroidery;
    }

    public void setCurrentAlterQuantityAfterEmbroidery(String currentAlterQuantityAfterEmbroidery) {
        this.currentAlterQuantityAfterEmbroidery = currentAlterQuantityAfterEmbroidery;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getMakerIssueDate() {
        return makerIssueDate;
    }

    public void setMakerIssueDate(String makerIssueDate) {
        this.makerIssueDate = makerIssueDate;
    }

    public String getOtherParts() {
        return otherParts;
    }

    public void setOtherParts(String otherParts) {
        this.otherParts = otherParts;
    }

    public String getIsUpdatedByProductionManager() {
        return isUpdatedByProductionManager;
    }

    public void setIsUpdatedByProductionManager(String isUpdatedByProductionManager) {
        this.isUpdatedByProductionManager = isUpdatedByProductionManager;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProductionManagerId() {
        return productionManagerId;
    }

    public void setProductionManagerId(String productionManagerId) {
        this.productionManagerId = productionManagerId;
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
        parcel.writeString(totalFabricConsumed);
        parcel.writeString(totalWastage);
        parcel.writeString(isUpdatedByProductionManager);
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

    public String getTotalFabricConsumed() {
        return totalFabricConsumed;
    }

    public void setTotalFabricConsumed(String totalFabricConsumed) {
        this.totalFabricConsumed = totalFabricConsumed;
    }

    public String getTotalWastage() {
        return totalWastage;
    }

    public void setTotalWastage(String totalWastage) {
        this.totalWastage = totalWastage;
    }

}
