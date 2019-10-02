package com.locslender.detaiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phong implements Serializable {
//Idphong,Giaphong,Trangthai,Ghep,Dientich,Idkhutro,Imgpt,Mota
    @SerializedName("Idphong")
    @Expose
    private String idphong;
    @SerializedName("Giaphong")
    @Expose
    private String giaphong;
    @SerializedName("Trangthai")
    @Expose
    private String trangthai;
    @SerializedName("Ghep")
    @Expose
    private String ghep;
    @SerializedName("Dientich")
    @Expose
    private String dientich;
    @SerializedName("Idkhutro")
    @Expose
    private String idkhutro;
    @SerializedName("Imgpt")
    @Expose
    private String imgpt;
    @SerializedName("Mota")
    @Expose
    private String mota;

    /**
     * No args constructor for use in serialization
     *
     */
    public Phong() {
    }

    /**
     *
     * @param dientich
     * @param ghep
     * @param mota
     * @param imgpt
     * @param trangthai
     * @param giaphong
     * @param idphong
     * @param idkhutro
     */
    public Phong(String idphong, String giaphong, String trangthai, String ghep, String dientich, String idkhutro, String imgpt, String mota) {
        super();
        this.idphong = idphong;
        this.giaphong = giaphong;
        this.trangthai = trangthai;
        this.ghep = ghep;
        this.dientich = dientich;
        this.idkhutro = idkhutro;
        this.imgpt = imgpt;
        this.mota = mota;
    }

    public String getIdphong() {
        return idphong;
    }

    public void setIdphong(String idphong) {
        this.idphong = idphong;
    }

    public String getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(String giaphong) {
        this.giaphong = giaphong;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhep() {
        return ghep;
    }

    public void setGhep(String ghep) {
        this.ghep = ghep;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getIdkhutro() {
        return idkhutro;
    }

    public void setIdkhutro(String idkhutro) {
        this.idkhutro = idkhutro;
    }

    public String getImgpt() {
        return imgpt;
    }

    public void setImgpt(String imgpt) {
        this.imgpt = imgpt;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

}