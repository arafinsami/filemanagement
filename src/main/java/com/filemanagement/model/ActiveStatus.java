package com.filemanagement.model;

public enum ActiveStatus {

    ACTIVE("Active"),
    IN_ACTIVE("In Active");


    private String label;

    ActiveStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}