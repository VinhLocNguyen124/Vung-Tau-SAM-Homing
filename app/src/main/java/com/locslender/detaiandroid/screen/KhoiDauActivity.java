package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.locslender.detaiandroid.R;

public class KhoiDauActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNguoiThueTro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_khoidau);
        mapped();
        dangKiSukien();
    }

    private void mapped() {
        btnNguoiThueTro = findViewById(R.id.btnNguoiThueTro);
    }

    private void dangKiSukien() {
        btnNguoiThueTro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnNguoiThueTro:
                startActivity(new Intent(KhoiDauActivity.this,TrangChuActivity.class));
                break;
        }
    }
}
