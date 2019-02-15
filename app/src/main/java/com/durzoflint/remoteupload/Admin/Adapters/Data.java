package com.durzoflint.remoteupload.Admin.Adapters;

public class Data {
    String id, address, measurementImage, installationImage, shopName, details;

    public Data(String id, String address, String measurementImage, String installationImage,
                String shopName, String details) {
        this.id = id;
        this.address = address;
        this.measurementImage = measurementImage;
        this.installationImage = installationImage;
        this.shopName = shopName;
        this.details = details;
    }
}