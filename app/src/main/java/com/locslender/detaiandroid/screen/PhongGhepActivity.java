package com.locslender.detaiandroid.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.locslender.detaiandroid.fragment.FragmentBandoPhongGhep;
import com.locslender.detaiandroid.fragment.FragmentBandoPhongThuong;
import com.locslender.detaiandroid.fragment.FragmentTimPhongghep;
import com.locslender.detaiandroid.model.LatLngPhongGhep;
import com.locslender.detaiandroid.model.Phongghep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhongGhepActivity extends AppCompatActivity implements FragmentTimPhongghep.OnTransferArrayListener {
    private static final String TAG = "PhongGhepActivity";
    //Keys
    public static final String KEY_MANG_THO = "MangTho";
    public static final String KEY_MANG_THO_LATLNG = "MangThoLatLng";

    //widgets
    private Fragment fragment = null;
    private static ListView lvPhongghep;
    private BottomNavigationView bnavPhongghep;

    //vars
    private static PhongghepListViewAdapter phongghepListViewAdapter;
    private  ArrayList<Phongghep> arrPhongghep = new ArrayList<>();
    private  ArrayList<Phongghep> arrPhongghep_SauSearch;
    private  ArrayList<LatLngPhongGhep> arrLatlng=new ArrayList<>();

    //urls
    private String urlpg = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layThongtinPhongGhep.php";
    private String urlLatlng = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngNhaTroGhep.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_phongghep);
        mapped();
        getDataPhongghep(urlpg);
        setPhongghepListViewAdapter(arrPhongghep);
        batSukienItemListPhongghep();
        batSukienTabPhongghep();
        if (findViewById(R.id.fragment_container_phongghep) != null) {

            if (savedInstanceState != null) {
                return;
            }
        }
        getDataLatLng(urlLatlng);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    setPhongghepListViewAdapter(arrPhongghep);
    }

    public void batSukienTabPhongghep() {
        bnavPhongghep.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        fragment = new FragmentTimPhongghep();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_MANG_THO, arrPhongghep);
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_phongghep, fragment).commit();
                        break;
                    case R.id.nav_mapPG:
                        //gửi dữ liệu qua cho Fragment bản đồ
                        Log.d(TAG, "onNavigationItemSelected: chuyển qua map"+arrLatlng.get(0).getIdnt());
                        fragment = new FragmentBandoPhongGhep();
                        Bundle bundle11=new Bundle();
                        bundle11.putSerializable(KEY_MANG_THO_LATLNG,arrLatlng);
                        fragment.setArguments(bundle11);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_phongghep, fragment).commit();
                        break;
                }
                return true;
            }
        });

    }

    public void batSukienItemListPhongghep() {
        lvPhongghep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhongGhepActivity.this, ChiTietPhongActivity.class);
                intent.putExtra("DuLieuPhongghep", arrPhongghep.get(position).getIdphong()+"");
                startActivity(intent);
            }
        });
    }

    public void setPhongghepListViewAdapter(ArrayList<Phongghep> arrayList) {
        phongghepListViewAdapter = new PhongghepListViewAdapter(PhongGhepActivity.this, R.layout.layout_phongghep_listviewitem, arrayList);
        lvPhongghep.setAdapter(phongghepListViewAdapter);
    }

    public void mapped() {
        bnavPhongghep = findViewById(R.id.bnavTab);
        lvPhongghep = findViewById(R.id.lvPhongghep);
    }

    public void getDataPhongghep(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
//                                String tennt, String diachi, String urlimg, String giapt, String dientichpt, String urlanhdaidien
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
                        phongghepListViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhongGhepActivity.this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void getDataLatLng(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("LOOO",response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //    Idkhutro,Tenkhutro, Lat, Lng, Tentp, Img, Slphong, Diachi, Mota
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrLatlng.add(new LatLngPhongGhep(jsonObject.getString("Idkhutro"),
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
                                e.printStackTrace();
                                Log.d("LOOO",e.toString());
                            }
                        }
                        phongghepListViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhongGhepActivity.this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //Nhận mảng sau khi xử lý và
    @Override
    public void setArrayFromFragmenttoPhongGhepActivity(ArrayList<Phongghep> arrayList) {
        arrPhongghep_SauSearch = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrPhongghep_SauSearch.add(arrayList.get(i));
        }
        phongghepListViewAdapter.notifyDataSetChanged();
        setPhongghepListViewAdapter(arrPhongghep_SauSearch);
        Toast.makeText(PhongGhepActivity.this, arrPhongghep_SauSearch.size()+"", Toast.LENGTH_SHORT).show();
    }


}
