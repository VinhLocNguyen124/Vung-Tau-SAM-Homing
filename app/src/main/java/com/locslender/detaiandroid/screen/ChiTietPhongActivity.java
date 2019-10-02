package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.locslender.detaiandroid.ImageGallery.FullScreenImageActivity;
import com.locslender.detaiandroid.Interface.IRecyclerViewClickListener;
import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.adapter.GalleryImageAdapter;
import com.locslender.detaiandroid.fragment.FragmentBandoChiTietKhuTro;
import com.locslender.detaiandroid.model.ChiTietPhongGhep;
import com.locslender.detaiandroid.model.Phongghep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class ChiTietPhongActivity extends AppCompatActivity {
    private static final String TAG = "ChiTietPhongActivity";

    //vars
    private String url = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongTinChiTietPhongThuong.php";
    private ArrayList<ChiTietPhongGhep> arrChiTietPhongGhep = new ArrayList<>();
    private Phongghep phongghep;
    public float lat;
    public float lng;
    public String idphong;
    FragmentBandoChiTietKhuTro fragobj;

    //Widget
    private RecyclerView rclImageGallery;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvTennt, tvDiachint, tvKhoangcach, tvMota, tvGia, tvDientich, tvTrangthaigac, tvHoten, tvEmail, tvSdt;
    private FrameLayout frBandonho;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chitietphong);
        mapped();
        nhanDulieuPhongghep();
        layThongtinChitietPG(url);
        goiMiniMap();
        setAdapterImageGalleryView();
    }

    public void setAdapterImageGalleryView() {
        layoutManager = new GridLayoutManager(this, 2);
        rclImageGallery.setHasFixedSize(true);
        rclImageGallery.setLayoutManager(layoutManager);


        Random random = new Random();
        final String[] images = new String[10];
        for (int i = 0; i < images.length; i++) {
            images[i] = "https://picsum.photos/600?image=" + random.nextInt(1000 + 1);
        }

        //bắt sự kiện khi click vào một ảnh
        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(ChiTietPhongActivity.this, FullScreenImageActivity.class);
                i.putExtra("IMAGES", images);
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        };

        GalleryImageAdapter adapter = new GalleryImageAdapter(this, images, listener);
        rclImageGallery.setAdapter(adapter);
    }


    //Gọi bản đồ nhỏ
    public void goiMiniMap() {
        FragmentBandoChiTietKhuTro fragmentBandoChiTietKhuTro = new FragmentBandoChiTietKhuTro();
        getSupportFragmentManager().beginTransaction().add(R.id.mapFragment_containerCTP, fragmentBandoChiTietKhuTro).commit();
    }


    public void ganDulieu() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        for (int i = 0; i < arrChiTietPhongGhep.size(); i++) {
            if (idphong.equals(arrChiTietPhongGhep.get(i).getIdpt()+"")) {
                tvTennt.setText(arrChiTietPhongGhep.get(i).getTennt());
                tvDiachint.setText("Địa chỉ: " + arrChiTietPhongGhep.get(i).getDiachi());
                tvMota.setText("  " + arrChiTietPhongGhep.get(i).getMota());
                tvGia.setText("Giá thuê: " + formatter.format(Integer.parseInt(arrChiTietPhongGhep.get(i).getGiapt())) + "vnđ");
                tvDientich.setText("Diện tích phòng: " + arrChiTietPhongGhep.get(i).getDientichpt()+"m");
                tvTrangthaigac.setText(traTrangthaigac(i));
                tvHoten.setText("Họ tên: "+arrChiTietPhongGhep.get(i).getHoten());
                tvEmail.setText("Email: "+arrChiTietPhongGhep.get(i).getEmail());
                tvSdt.setText("Liên lạc: " + arrChiTietPhongGhep.get(i).getSdt());
                lat = Float.valueOf(arrChiTietPhongGhep.get(i).getLat().trim());
                lng = Float.valueOf(arrChiTietPhongGhep.get(i).getLng().trim());

                fragobj = FragmentBandoChiTietKhuTro.newInstance(lat, lng, arrChiTietPhongGhep.get(i).getTennt());
                getSupportFragmentManager().beginTransaction().replace(R.id.mapFragment_containerCTP, fragobj).commit();
            }
        }
    }

    public String traTrangthaigac(int pos) {
        if (arrChiTietPhongGhep.get(pos).getTrangthaigac().equals("1")) {
            return "Có gác";
        } else {
            return "Không có gác";
        }
    }

    public void layThongtinChitietPG(String urls) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrChiTietPhongGhep.add(new ChiTietPhongGhep(jsonObject.getInt("Idphong"),
                                        jsonObject.getString("Tenkhutro"),
                                        jsonObject.getString("Diachi"),
                                        jsonObject.getString("Mota"),
                                        jsonObject.getString("Lat"),
                                        jsonObject.getString("Lng"),
                                        jsonObject.getString("Giaphong"),
                                        jsonObject.getString("Dientich"),
                                        jsonObject.getString("Trangthaigac"),
                                        jsonObject.getString("Hoten"),
                                        jsonObject.getString("Email"),
                                        jsonObject.getString("Sdt")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ganDulieu();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietPhongActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void nhanDulieuPhongghep() {
        Intent intent = getIntent();
        idphong=intent.getStringExtra("DuLieuPhongghep");
        Toast.makeText(ChiTietPhongActivity.this, idphong + "", Toast.LENGTH_SHORT).show();
    }

    public void mapped() {
        rclImageGallery = findViewById(R.id.recyclerImageGalleryViewCTP);
        tvTennt = findViewById(R.id.tvTenKhuTroCTP);
        tvDiachint = findViewById(R.id.tvDiachiKhuTroCTP);
        tvMota = findViewById(R.id.tvMotaCTP);
        tvGia = findViewById(R.id.tvGiaptCTP);
        tvDientich = findViewById(R.id.tvDientichptCTP);
        tvTrangthaigac = findViewById(R.id.tvTrangthaigacCTP);
        tvHoten = findViewById(R.id.tvTenchutroCTP);
        tvEmail = findViewById(R.id.tvEmailchutroCTP);
        tvSdt = findViewById(R.id.tvSdtchutroCTP);
    }

}
