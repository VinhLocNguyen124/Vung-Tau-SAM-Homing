package com.locslender.detaiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diadiem {

    @SerializedName("Tentp")
    @Expose
    private String tentp;
    @SerializedName("Motatp")
    @Expose
    private String motatp;
    @SerializedName("Hinhtp")
    @Expose
    private String hinhtp;
    @SerializedName("Icontp")
    @Expose
    private String icontp;

    public Diadiem(String tentp, String motatp, String hinhtp, String icontp) {
        this.tentp = tentp;
        this.motatp = motatp;
        this.hinhtp = hinhtp;
        this.icontp = icontp;
    }

    public String getTentp() {
        return tentp;
    }

    public void setTentp(String tentp) {
        this.tentp = tentp;
    }

    public String getMotatp() {
        return motatp;
    }

    public void setMotatp(String motatp) {
        this.motatp = motatp;
    }

    public String getHinhtp() {
        return hinhtp;
    }

    public void setHinhtp(String hinhtp) {
        this.hinhtp = hinhtp;
    }

    public String getIcontp() {
        return icontp;
    }

    public void setIcontp(String icontp) {
        this.icontp = icontp;
    }

}
