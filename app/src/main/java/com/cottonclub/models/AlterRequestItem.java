package com.cottonclub.models;

public class AlterRequestItem {
    private String alterRequestCreationDate;
    private String designNumber;
    private String alterQuantity;
    private String master;
    private String cuttingIssueDate;

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
}
