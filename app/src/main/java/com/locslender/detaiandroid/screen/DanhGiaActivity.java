package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.locslender.detaiandroid.R;

import java.util.HashMap;
import java.util.Map;

public class DanhGiaActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DanhGiaActivity";

    //vars
    private String idkt;
    private String danhgia;
    private float mDanhgiatam;

    //widgets
    private RelativeLayout rltKHL,rltCHL,rltHL,rltHHL,rltRHL;
    private Button btnDanhgia,btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        mapped();
        nhanDulieu();
        dangKiSuKienClickChoRLTDG();
    }

    private void nhanDulieu() {
        Intent intent=getIntent();
        idkt=intent.getStringExtra("idkhutro");
        danhgia=intent.getStringExtra("ratekhutro");

    }

    private void dangKiSuKienClickChoRLTDG() {
        rltKHL.setOnClickListener(this);
        rltCHL.setOnClickListener(this);
        rltHL.setOnClickListener(this);
        rltHHL.setOnClickListener(this);
        rltRHL.setOnClickListener(this);
        btnDanhgia.setOnClickListener(this);
        btnHuy.setOnClickListener(this);
    }

    private void mapped() {
        rltKHL=findViewById(R.id.rltKHL);
        rltCHL=findViewById(R.id.rltCHL);
        rltHL=findViewById(R.id.rltHL);
        rltHHL=findViewById(R.id.rltHHL);
        rltRHL=findViewById(R.id.rltRHL);
        btnDanhgia=findViewById(R.id.btnDanhgia);
        btnHuy=findViewById(R.id.btnHuy);
    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.rltKHL:
                rltKHL.setBackgroundResource(R.drawable.nen_tim);
                rltCHL.setBackgroundResource(R.drawable.nen_bt);
                rltHL.setBackgroundResource(R.drawable.nen_bt);
                rltHHL.setBackgroundResource(R.drawable.nen_bt);
                rltRHL.setBackgroundResource(R.drawable.nen_bt);
                mDanhgiatam=1;
                Log.d(TAG, "onClick: "+mDanhgiatam);
                break;
            case R.id.rltCHL:
                rltKHL.setBackgroundResource(R.drawable.nen_bt);
                rltCHL.setBackgroundResource(R.drawable.nen_xanh_dam);
                rltHL.setBackgroundResource(R.drawable.nen_bt);
                rltHHL.setBackgroundResource(R.drawable.nen_bt);
                rltRHL.setBackgroundResource(R.drawable.nen_bt);
                mDanhgiatam=2;
                Log.d(TAG, "onClick: "+mDanhgiatam);
                break;
            case R.id.rltHL:
                rltKHL.setBackgroundResource(R.drawable.nen_bt);
                rltCHL.setBackgroundResource(R.drawable.nen_bt);
                rltHL.setBackgroundResource(R.drawable.nen_xanh_nhat);
                rltHHL.setBackgroundResource(R.drawable.nen_bt);
                rltRHL.setBackgroundResource(R.drawable.nen_bt);
                mDanhgiatam=3;
                Log.d(TAG, "onClick: "+mDanhgiatam);
                break;
            case R.id.rltHHL:
                rltKHL.setBackgroundResource(R.drawable.nen_bt);
                rltCHL.setBackgroundResource(R.drawable.nen_bt);
                rltHL.setBackgroundResource(R.drawable.nen_bt);
                rltHHL.setBackgroundResource(R.drawable.nen_hong);
                rltRHL.setBackgroundResource(R.drawable.nen_bt);
                mDanhgiatam=4;
                Log.d(TAG, "onClick: "+mDanhgiatam);
                break;
            case R.id.rltRHL:
                rltKHL.setBackgroundResource(R.drawable.nen_bt);
                rltCHL.setBackgroundResource(R.drawable.nen_bt);
                rltHL.setBackgroundResource(R.drawable.nen_bt);
                rltHHL.setBackgroundResource(R.drawable.nen_bt);
                rltRHL.setBackgroundResource(R.drawable.nen_cam);
                mDanhgiatam=5;
                Log.d(TAG, "onClick: "+mDanhgiatam);
                break;
            case R.id.btnDanhgia:
                final float rateTB=(Float.parseFloat(danhgia)+mDanhgiatam)/2;
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://dbnhatrovungtau.000webhostapp.com/Server/Server/CapnhatRateKhuTro.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("Success")) {
                                    Toast.makeText(DanhGiaActivity.this,"Đánh giá khu trọ thành công !!",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DanhGiaActivity.this,"Đánh giá khu trọ không thành công",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DanhGiaActivity.this, "Lỗi kết nối vui lòng thử lại sau ít phút nữa !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idktDG", idkt);
                        params.put("rateDG", String.valueOf(rateTB));
                        return params;
                    }
                };
                //nhớ add cái này nếu không toàn bộ hệ thống sẽ bị sập
                requestQueue.add(stringRequest);
                break;
            case R.id.btnHuy:
                finish();
                break;
        }
    }
}
