package com.example.otsmaindesign.models;

import android.graphics.Bitmap;

public class ModelClass {
    private String imageName;
    private Bitmap image;
    private String spName;
    private String totalPrice;

    public ModelClass(Bitmap image, String name, String spname) {
        this.imageName = name;
        this.image = image;
        this.spName = spname;
    }
    public ModelClass(Bitmap image, String name, Integer total_amt) {
        this.imageName = name;
        this.image = image;
        this.totalPrice = total_amt.toString();
    }
    public ModelClass(String name) {
        this.imageName = name;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getspName() {
        return spName;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}
