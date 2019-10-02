package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.adapter.PhongghepListViewAdapter;
import com.locslender.detaiandroid.fragment.FragmentBandoPhongThuong;
import com.locslender.detaiandroid.fragment.FragmentTimPhongthuong;
import com.locslender.detaiandroid.model.LatLngPhongThuong;
import com.locslender.detaiandroid.model.Phongghep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhongThuongActivity extends AppCompatActivity implements FragmentTimPhongthuong.OnTransferArrayListener {

    private static final String TAG = "PhongThuongActivity1";

    //Keys
    public static final String KEY_MANG_THO = "MangTho";
    public static final String KEY_MANG_THO_LATLNG = "MangThoLatLng";

    //Vars
    private ArrayList<Phongghep> arrPhongthuong=new ArrayList<>();
    private PhongghepListViewAdapter phongghepListViewAdapter;
    private  ArrayList<Phongghep> arrPhongthuong_SauSearch;
    private  ArrayList<LatLngPhongThuong> arrLatlng=new ArrayList<>();

    //Widgets
    private Fragment fragment = null;
    private BottomNavigationView bnavPhongthuong;
    private ListView lvPhongthuong;

    //urls
    private String urlThanhpho;
    private String urlLatlng ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_phongthuong);
        mapped();
        setUrlThanhPho();
        getDataPhongthuong(urlThanhpho);
        setAdapterListViewPhongThuong(arrPhongthuong);
        batSukienItemListPhongthuong();
        batSukienTabPhongthuong();
        if (findViewById(R.id.fragment_container_phongthuong) != null) {

            if (savedInstanceState != null) {
                return;
            }
        }
        getDataLatLng(urlLatlng);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setAdapterListViewPhongThuong(arrPhongthuong);
    }

    public void getDataLatLng(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //    Idkhutro,Tenkhutro, Lat, Lng, Tentp, Img, Slphong, Diachi, Mota
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrLatlng.add(new LatLngPhongThuong(jsonObject.getString("Idkhutro"),
                                        jsonObject.getString("Tenkhutro"),
                                        jsonObject.getString("Lat"),
                                        jsonObject.getString("Lng"),
                                        jsonObject.getString("Tentp"),
                                        jsonObject.getString("Img")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("0000", "onResponse: "+e.toString());
                            }
                        }
                        phongghepListViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhongThuongActivity.this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void batSukienTabPhongthuong() {
        bnavPhongthuong.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_listPG:
                        if (fragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        } else {
                            return true;
                        }
                        break;
                    case R.id.nav_searchPG:
                        //gửi dữ liệu qua cho Fragment tìm kiếm
                        Log.d(TAG, "onNavigationItemSelected: ");
                        fragment = new FragmentTimPhongthuong();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_MANG_THO, arrPhongthuong);
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_phongthuong, fragment).commit();
                        break;
                    case R.id.nav_mapPG:
                        Log.d(TAG, "onNavigationItemSelected: "+arrLatlng.size());
                        //gửi dữ liệu qua cho Fragment bản đồ
                        fragment = new FragmentBandoPhongThuong();
                        Bundle bundle11=new Bundle();
                        bundle11.putSerializable(KEY_MANG_THO_LATLNG,arrLatlng);
                        fragment.setArguments(bundle11);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_phongthuong, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    private void batSukienItemListPhongthuong() {
        lvPhongthuong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PhongThuongActivity.this,ChiTietPhongActivity.class);
                intent.putExtra("DuLieuPhongghep",arrPhongthuong.get(position).getIdphong()+"");
                startActivity(intent);
            }
        });
    }

    private void setAdapterListViewPhongThuong(ArrayList<Phongghep> arrayList) {
        phongghepListViewAdapter =new PhongghepListViewAdapter(this,R.layout.layout_phongghep_listviewitem,arrayList);
        lvPhongthuong.setAdapter(phongghepListViewAdapter);
    }

    private void mapped() {
        lvPhongthuong=findViewById(R.id.lvPhongthuong);
        bnavPhongthuong=findViewById(R.id.bnavTabPT);
    }

    private void setUrlThanhPho() {

        Intent intent = getIntent();

        String tentp = intent.getStringExtra("DIADIEM");
        Toast.makeText(this, tentp, Toast.LENGTH_SHORT).show();
        switch (tentp) {
            case "Vũng Tàu":
                urlThanhpho = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongThanhPhoVungTau.php";
                urlLatlng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngVungTau.php";
                break;
            case "Đà Nẵng":
                urlThanhpho = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongThanhPhoDaNang.php";
                urlLatlng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngDaNang.php";
                break;
            case "Sài Gòn":
                urlThanhpho = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongThanhPhoSaiGon.php";
                urlLatlng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngSaiGon.php";
                break;
            case "Hội An":
                urlThanhpho = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongThanhPhoHoiAn.php";
                urlLatlng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngHoiAn.php";
                break;
            case "Hà Nội":
                urlThanhpho = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongThanhPhoHaNoi.php";
                urlLatlng="https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngHaNoi.php";
                break;

        }
    }


    public void getDataPhongthuong(String urls) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);
                                arrPhongthuong.add(new Phongghep(jsonObject.getInt("Idphong"),
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
                        phongghepListViewAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhongThuongActivity.this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void setArrayFromFragmenttoPhongThuongActivity(ArrayList<Phongghep> arrayList) {
        arrPhongthuong_SauSearch = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrPhongthuong_SauSearch.add(arrayList.get(i));
        }
        phongghepListViewAdapter.notifyDataSetChanged();
        setAdapterListViewPhongThuong(arrPhongthuong_SauSearch);
        Toast.makeText(PhongThuongActivity.this, arrPhongthuong_SauSearch.size()+"", Toast.LENGTH_SHORT).show();
    }
}
