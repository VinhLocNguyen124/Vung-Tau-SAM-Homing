package com.locslender.detaiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LatLngPhongThuong implements Serializable {
    //    Idkhutro,Tenkhutro, Lat, Lng, Tentp, Img, Slphong, Diachi, Mota
    @SerializedName("Idkhutro")
    @Expose
    private String idkhutro;
    @SerializedName("Tenkhutro")
    @Expose
    private String tenkhutro;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Lng")
    @Expose
    private String lng;
    @SerializedName("Tentp")
    @Expose
    private String tentp;
    @SerializedName("Img")
    @Expose
    private String img;

    /**
     * No args constructor for use in serialization
     */
    public LatLngPhongThuong() {
    }

    public LatLngPhongThuong(String idkhutro, String tenkhutro, String lat, String lng, String tentp, String img) {
        this.idkhutro = idkhutro;
        this.tenkhutro = tenkhutro;
        this.lat = lat;
        this.lng = lng;
        this.tentp = tentp;
        this.img = img;

    }

    public String getIdnt() {
        return idkhutro;
    }

    public void setIdnt(String idnt) {
        this.idkhutro = idnt;
    }

    public String getTennt() {
        return tenkhutro;
    }

    public void setTennt(String tennt) {
        this.tenkhutro = tennt;
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

    public String getTentp() {
        return tentp;
    }

    public void setTentp(String tentp) {
        this.tentp = tentp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
