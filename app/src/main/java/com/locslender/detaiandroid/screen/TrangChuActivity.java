package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.adapter.DiadiemAdapter;
import com.locslender.detaiandroid.adapter.PhongghepAdapter;
import com.locslender.detaiandroid.adapter.PhongghepListViewAdapter;
import com.locslender.detaiandroid.fragment.FragmentBando;
import com.locslender.detaiandroid.model.Diadiem;
import com.locslender.detaiandroid.model.LatLngPhongGhep;
import com.locslender.detaiandroid.model.Phongghep;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity implements DiadiemAdapter.OnPlaceClickListener {

    //keys
    private static final int ERROR_DIALOG_REQUEST = 9001;
    public static final String KEY_MANG_LATLNG_NHATRO = "manglatlngnhatro";

    //StringUrl
    private String url = "https://nguyenvinhloc124.000webhostapp.com/Server/layThongtinThanhPho.php";
    private String urlphongghep = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongGhep.php";
    private String urlphongtatca = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongTatCa.php";
    String urlLatLng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngNhaTro.php";

    //vars
    int minPTGTN;
    int idPTGTN;
    private Handler handler;
    private Runnable runnable;
    private int currentItem;
    private ArrayList<Diadiem> arrDiadiem;
    private DiadiemAdapter diadiemAdapter;
    private ArrayList<Phongghep> arrPhongghep;
    private ArrayList<Phongghep> arrTatcaphong=new ArrayList<>();
    private PhongghepAdapter phongghepAdapter;
    private ArrayList<LatLngPhongGhep> arrLatLngNhatro=new ArrayList<>();

    //widget
    private ImageView imgBestdeal;
    private TextView tvBestdeal;
    private RelativeLayout rlBestdeal;
    private ViewPager vpPhongghep;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Fragment fragment = null;
    private RecyclerView recyclerView;
    private NavigationView navTrangchu;
    private ImageView imgHinhPTGTN;
    private TextView tvTenntPTGTN, tvDiachintPTGTN;
    private FrameLayout layoutTagPTGTN;
    private PhongghepListViewAdapter phongghepListViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trangchu);
        mapped();
        getDataLatLngNhaTro(urlLatLng);
        setmActionBarDrawerToggle();
        setDiadiemAdapter();
        setPhongghepAdapter();
        setChuyenManHinhchoViewPager();
        batSukienPhongTroGiaTotNhat();
        batSukienTimKiemPhongQuaBanDo();
        getDataTatcaPhong(urlphongtatca);
    }


    //Bắt sự kiện hiển thị Fragment khi nháp vào item trong NavigationView
    public  void batSukienTimKiemPhongQuaBanDo(){
        navTrangchu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_trangchu:
                        if (fragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        } else {
                            return true;
                        }
                        break;
                    case R.id.nav_bando:
                        fragment = new FragmentBando();
                        Bundle bundle11=new Bundle();
                        bundle11.putSerializable(KEY_MANG_LATLNG_NHATRO,arrLatLngNhatro);
                        fragment.setArguments(bundle11);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void getDataLatLngNhaTro(String urls) {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrLatLngNhatro.add(new LatLngPhongGhep(jsonObject.getString("Idkhutro"),
                                        jsonObject.getString("Tenkhutro"),
                                        jsonObject.getString("Lat"),
                                        jsonObject.getString("Lng"),
                                        jsonObject.getString("Tentp"),
                                        jsonObject.getString("Img"),
                                        jsonObject.getString("Slphong"),
                                        jsonObject.getString("Diachi"),
                                        jsonObject.getString("Mota"),
                                        jsonObject.getString("Rate")));
                            } catch (JSONException e) {
                                Toast.makeText(TrangChuActivity.this, "Không parse được", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrangChuActivity.this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //Khi bấm vào địa điểm có giá tốt nhất thì sẽ vào ChitietPhongActvity cùng với đối tượng phòng ghép được truyền
    public void batSukienPhongTroGiaTotNhat() {
        layoutTagPTGTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phongghep phongGTN = new Phongghep();
                for (int i = 0; i < arrTatcaphong.size(); i++) {
                    if (arrTatcaphong.get(i).getIdphong() == idPTGTN) {
                        phongGTN = arrTatcaphong.get(i);
                    }
                }
                Intent intent = new Intent(TrangChuActivity.this, ChiTietPhongActivity.class);
                intent.putExtra("DuLieuPhongghep", phongGTN.getIdphong()+"");
                startActivity(intent);
            }
        });
    }

    //Tìm phòng có giá thấp nhất rồi lấy ra Id để dò tìm trong hàm batSukienPhongTroGiaTotNhat()
    public void xuLyDiadiemCoGiaTotNhat() {
        minPTGTN = Integer.parseInt(arrTatcaphong.get(0).getGiaphong() + "");
        idPTGTN = 0;
        for (int i = 0; i < arrTatcaphong.size(); i++) {
            if (Integer.parseInt(arrTatcaphong.get(i).getGiaphong() + "") < minPTGTN) {
                minPTGTN = Integer.parseInt(arrTatcaphong.get(i).getGiaphong() + "");
                idPTGTN = Integer.parseInt(arrTatcaphong.get(i).getIdphong() + "");
            }
        }
        Log.d("ABC", minPTGTN + "");
        Log.d("ABC", idPTGTN + "");

        for (int i = 0; i < arrTatcaphong.size(); i++) {
            if (Integer.parseInt(arrTatcaphong.get(i).getIdphong() + "") == idPTGTN) {
                Picasso.with(getApplicationContext()).load(arrTatcaphong.get(i).getImgpt()).into(imgHinhPTGTN);
                tvTenntPTGTN.setText(arrTatcaphong.get(i).getTenkhutro());
                tvDiachintPTGTN.setText(arrTatcaphong.get(i).getDiachi());
            }
        }
    }


    public void setChuyenManHinhchoViewPager() {
        vpPhongghep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrangChuActivity.this, PhongGhepActivity.class));
            }
        });
    }

    //set Adapter cho panel quang cáo phòng ghép
    public void setPhongghepAdapter() {
        arrPhongghep = new ArrayList<>();
        getDataPhongghep(urlphongghep);
        phongghepAdapter = new PhongghepAdapter(this, arrPhongghep);
        vpPhongghep.setAdapter(phongghepAdapter);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = vpPhongghep.getCurrentItem();
                currentItem++;
                if (currentItem >= vpPhongghep.getAdapter().getCount()) {
                    currentItem = 0;
                }
                vpPhongghep.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);
            }
        };
        handler.postDelayed(runnable, 4500);

    }

    //Lấy dữ liệu phòng ghép từ hosting
    public void getDataPhongghep(String urlpg) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlpg, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrPhongghep.add(new Phongghep(jsonObject.getInt("Idphong"),
                                        jsonObject.getString("Tenkhutro"),
                                        jsonObject.getString("Diachi"),
                                        jsonObject.getString("Img"),
                                        jsonObject.getString("Giaphong"),
                                        jsonObject.getString("Dientich"),
                                        jsonObject.getString("Imgpt"),
                                        jsonObject.getString("Tentp"),
                                        jsonObject.getString("Phuong"),
                                        jsonObject.getString("Trangthaigac"),
                                        jsonObject.getString("Lat"),
                                        jsonObject.getString("Lng")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        phongghepAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrangChuActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("LOC", error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void getDataTatcaPhong(String urlpg) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlpg, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrTatcaphong.add(new Phongghep(jsonObject.getInt("Idphong"),
                                        jsonObject.getString("Tenkhutro"),
                                        jsonObject.getString("Diachi"),
                                        jsonObject.getString("Img"),
                                        jsonObject.getString("Giaphong"),
                                        jsonObject.getString("Dientich"),
                                        jsonObject.getString("Imgpt"),
                                        jsonObject.getString("Tentp"),
                                        jsonObject.getString("Phuong"),
                                        jsonObject.getString("Trangthaigac"),
                                        jsonObject.getString("Lat"),
                                        jsonObject.getString("Lng")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        xuLyDiadiemCoGiaTotNhat();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrangChuActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("LOC", error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void setDiadiemAdapter() {
        arrDiadiem = new ArrayList<>();
        getDataThanhPho(url);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        diadiemAdapter = new DiadiemAdapter(this, arrDiadiem, this);
        recyclerView.setAdapter(diadiemAdapter);
    }

    public void getDataThanhPho(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrDiadiem.add(new Diadiem(object.getString("Tentp"),
                                        object.getString("Motatp"),
                                        object.getString("Hinhtp"),
                                        object.getString("Icontp")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        diadiemAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrangChuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("LOC", "onErrorResponse: " + error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //Ánh xạ các view
    private void mapped() {
        tvTenntPTGTN = findViewById(R.id.tvTenntPTGTN);
        tvDiachintPTGTN = findViewById(R.id.tvDiachintPTGTN);
        imgHinhPTGTN = findViewById(R.id.imgHinhPTGTN);
        layoutTagPTGTN = findViewById(R.id.layoutTagPTGTN);
        rlBestdeal = findViewById(R.id.rlBestdealoftheday);
        vpPhongghep = findViewById(R.id.vpPhongghep);
        navTrangchu = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer);
        recyclerView = findViewById(R.id.recycleView);
    }

    //Hiển thị cái nút 3 gạch để show/hide NavigationView
    private void setmActionBarDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Bắt sự kiện cho nút 3 que
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.about:
                Toast.makeText(this, "Thông tin chưa xử lý", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this, "Giúp đỡ chưa xử lý", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //Bắt sự kiện cho nút 3 que, khi bấm nút mũi tên quay về
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //khởi tạo menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_trangchu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Bắt sự kiện click cho địa điểm trong Horizontal RecyclerView
    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(TrangChuActivity.this, PhongThuongActivity.class);
        intent.putExtra("DIADIEM",arrDiadiem.get(position).getTentp());
        startActivity(intent);
    }
}
