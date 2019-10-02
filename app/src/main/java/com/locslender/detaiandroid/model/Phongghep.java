package com.locslender.detaiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phongghep implements Serializable {

    @SerializedName("Idphong")
    @Expose
    private int idphong;
    @SerializedName("Tenkhutro")
    @Expose
    private String tenkhutro;
    @SerializedName("Diachi")
    @Expose
    private String diachi;
    @SerializedName("Img")
    @Expose
    private String img;
    @SerializedName("Giaphong")
    @Expose
    private String giaphong;
    @SerializedName("Dientich")
    @Expose
    private String dientich;
    @SerializedName("Imgpt")
    @Expose
    private String imgpt;
    @SerializedName("Tentp")
    @Expose
    private String tentp;
    @SerializedName("Phuong")
    @Expose
    private String phuong;
    @SerializedName("Trangthaigac")
    @Expose
    private String trangthaigac;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Lng")
    @Expose
    private String lng;

    /**
     * No args constructor for use in serialization
     *
     */
    public Phongghep() {
    }

    public Phongghep(int idphong, String tenkhutro, String diachi, String img, String giaphong, String dientich, String imgpt, String tentp, String phuong, String trangthaigac, String lat, String lng) {
        this.idphong = idphong;
        this.tenkhutro = tenkhutro;
        this.diachi = diachi;
        this.img = img;
        this.giaphong = giaphong;
        this.dientich = dientich;
        this.imgpt = imgpt;
        this.tentp = tentp;
        this.phuong = phuong;
        this.trangthaigac = trangthaigac;
        this.lat = lat;
        this.lng = lng;
    }


    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public String getTenkhutro() {
        return tenkhutro;
    }

    public void setTenkhutro(String tenkhutro) {
        this.tenkhutro = tenkhutro;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(String giaphong) {
        this.giaphong = giaphong;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getImgpt() {
        return imgpt;
    }

    public void setImgpt(String imgpt) {
        this.imgpt = imgpt;
    }

    public String getTentp() {
        return tentp;
    }

    public void setTentp(String tentp) {
        this.tentp = tentp;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getTrangthaigac() {
        return trangthaigac;
    }

    public void setTrangthaigac(String trangthaigac) {
        this.trangthaigac = trangthaigac;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}