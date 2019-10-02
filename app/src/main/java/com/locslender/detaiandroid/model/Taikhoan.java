package com.locslender.detaiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taikhoan {

@SerializedName("Idtk")
@Expose
private String idtk;
@SerializedName("Tentk")
@Expose
private String tentk;
@SerializedName("Matkhau")
@Expose
private String matkhau;
@SerializedName("Idct")
@Expose
private String idct;

public String getIdtk() {
return idtk;
}

public void setIdtk(String idtk) {
this.idtk = idtk;
}

public String getTentk() {
return tentk;
}

public void setTentk(String tentk) {
this.tentk = tentk;
}

public String getMatkhau() {
return matkhau;
}

public void setMatkhau(String matkhau) {
this.matkhau = matkhau;
}

public String getIdct() {
return idct;
}

public void setIdct(String idct) {
this.idct = idct;
}

}