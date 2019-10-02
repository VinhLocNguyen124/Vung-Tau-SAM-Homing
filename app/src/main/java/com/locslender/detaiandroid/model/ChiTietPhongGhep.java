package com.locslender.detaiandroid.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietPhongGhep {

    @SerializedName("Idphong")
    @Expose
    private int idpt;
    @SerializedName("Tennt")
    @Expose
    private String tennt;
    @SerializedName("Diachi")
    @Expose
    private String diachi;
    @SerializedName("Mota")
    @Expose
    private String mota;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Lng")
    @Expose
    private String lng;
    @SerializedName("Giaphong")
    @Expose
    private String giapt;
    @SerializedName("Dientich")
    @Expose
    private String dientichpt;
    @SerializedName("Trangthaigac")
    @Expose
    private String trangthaigac;
    @SerializedName("Hoten")
    @Expose
    private String hoten;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Sdt")
    @Expose
    private String sdt;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChiTietPhongGhep() {
    }

    /**
     *
     * @param tennt
     * @param giapt
     * @param sdt
     * @param email
     * @param trangthaigac
     * @param mota
     * @param dientichpt
     * @param hoten
     * @param lng
     * @param lat
     * @param diachi
     */
    public ChiTietPhongGhep(int idpt,String tennt, String diachi, String mota, String lat, String lng, String giapt, String dientichpt, String trangthaigac, String hoten, String email, String sdt) {
        super();
        this.idpt=idpt;
        this.tennt = tennt;
        this.diachi = diachi;
        this.mota = mota;
        this.lat = lat;
        this.lng = lng;
        this.giapt = giapt;
        this.dientichpt = dientichpt;
        this.trangthaigac = trangthaigac;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;
    }

    public int getIdpt() {
        return idpt;
    }

    public void setIdpt(int idpt) {
        this.idpt = idpt;
    }

    public String getTennt() {
        return tennt;
    }

    public void setTennt(String tennt) {
        this.tennt = tennt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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

    public String getGiapt() {
        return giapt;
    }

    public void setGiapt(String giapt) {
        this.giapt = giapt;
    }

    public String getDientichpt() {
        return dientichpt;
    }

    public void setDientichpt(String dientichpt) {
        this.dientichpt = dientichpt;
    }

    public String getTrangthaigac() {
        return trangthaigac;
    }

    public void setTrangthaigac(String trangthaigac) {
        this.trangthaigac = trangthaigac;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

}