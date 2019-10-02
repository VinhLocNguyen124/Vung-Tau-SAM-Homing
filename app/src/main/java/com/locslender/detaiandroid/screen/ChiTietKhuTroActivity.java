package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.adapter.KhuTroAdapter;
import com.locslender.detaiandroid.model.LatLngPhongGhep;
import com.locslender.detaiandroid.model.Phong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietKhuTroActivity extends AppCompatActivity implements KhuTroAdapter.OnItemPhongClickListener {
    private static final String TAG = "ChiTietKhuTroActivity";

    //widgets
    private TextView tvTenkhutroCTKT, tvDiachikhutroCTKT, tvLatCTKT, tvLngCTKT, tvSophongCTKT, tvPhongtrongCTKT, tvTrangthaighepCTKT, tvDanhgiaCTKT;
    private ImageView imgHinhkhutroCTKT;
    private RecyclerView rclPhongtro;
    private RatingBar rtBarCTKT;

    //vars
    private ArrayList<LatLngPhongGhep> arrKhutro = new ArrayList<>();
    private ArrayList<Phong> arrayListPhong = new ArrayList<>();
    private ArrayList<Phong> arrayListPhongSauxuly = new ArrayList<>();
    private String Idkt, Tenkt, Diachikt, Sophongtrong, Trangthaighep, Danhgia;
    KhuTroAdapter khuTroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khu_tro);
        mapped();
        nhanDulieuvaGandulieu();
        setAdapterChoRecyclerPhongtro();
        batSukienDanhgia();
    }

    private void batSukienDanhgia() {
        tvDanhgiaCTKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietKhuTroActivity.this, DanhGiaActivity.class);
                intent.putExtra("idkhutro", Idkt);
                intent.putExtra("ratekhutro", Danhgia);
                startActivity(intent);
            }
        });
    }

    private void setAdapterChoRecyclerPhongtro() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclPhongtro.setLayoutManager(layoutManager);
        khuTroAdapter = new KhuTroAdapter(this, arrayListPhongSauxuly, this);
        rclPhongtro.setAdapter(khuTroAdapter);
    }

    private void mapped() {
        tvTenkhutroCTKT = findViewById(R.id.tvTenkhutroCTKT);
        tvDiachikhutroCTKT = findViewById(R.id.tvDiachikhutroCTKT);
        tvLatCTKT = findViewById(R.id.tvLatCTKT);
        tvLngCTKT = findViewById(R.id.tvLngCTKT);
        tvSophongCTKT = findViewById(R.id.tvSoluongphongCTKT);
        tvPhongtrongCTKT = findViewById(R.id.tvSophongtrongCTKT);
        tvTrangthaighepCTKT = findViewById(R.id.tvTrangthaighepCTKT);
        imgHinhkhutroCTKT = findViewById(R.id.imgHinhchitietkhutroCTKT);
        rclPhongtro = findViewById(R.id.rcl_Phongtro);
        rtBarCTKT = findViewById(R.id.rtbarNhatroCTKT);
        tvDanhgiaCTKT = findViewById(R.id.tvDanhgiaCTKT);
    }

    public void nhanDulieuvaGandulieu() {
        Intent intent = getIntent();
        arrKhutro = (ArrayList<LatLngPhongGhep>) intent.getSerializableExtra("mangkhutro");
        arrayListPhong = (ArrayList<Phong>) intent.getSerializableExtra("mangphongtro");
        Idkt = intent.getStringExtra("Idkhutro");
        Tenkt = intent.getStringExtra("Tenkhutro");
        Diachikt = intent.getStringExtra("Diachi");
        Sophongtrong = intent.getStringExtra("Sophongtrong");
        Trangthaighep = intent.getStringExtra("Trangthaighep");
        Danhgia = intent.getStringExtra("Danhgia");

        tvTenkhutroCTKT.setText(Tenkt);
        tvDiachikhutroCTKT.setText(Diachikt);
        for (int i = 0; i < arrKhutro.size(); i++) {
            if (arrKhutro.get(i).getIdnt().equals(Idkt)) {
                tvLatCTKT.setText(arrKhutro.get(i).getLat());
                tvLngCTKT.setText(arrKhutro.get(i).getLng());
                tvSophongCTKT.setText(arrKhutro.get(i).getSlphong());
                Picasso.with(this).load(arrKhutro.get(i).getImg()).into(imgHinhkhutroCTKT);
            }
        }
        tvPhongtrongCTKT.setText(Sophongtrong);
        tvTrangthaighepCTKT.setText(Trangthaighep);
        for (int i = 0; i < arrayListPhong.size(); i++) {
            if (arrayListPhong.get(i).getIdkhutro().equals(Idkt)) {
                arrayListPhongSauxuly.add(arrayListPhong.get(i));
            }
        }
        rtBarCTKT.setRating(Float.parseFloat(Danhgia));

    }

    @Override
    public void onItemPhongClick(int position) {
        Intent intent = new Intent(ChiTietKhuTroActivity.this, ChiTietPhongActivity.class);
        intent.putExtra("DuLieuPhongghep", arrayListPhongSauxuly.get(position).getIdphong() + "");
        startActivity(intent);
    }
}
