package com.locslender.detaiandroid.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.model.LatLngPhongGhep;
import com.locslender.detaiandroid.model.Phong;
import com.locslender.detaiandroid.model.Phongghep;
import com.locslender.detaiandroid.screen.ChiTietKhuTroActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class FragmentBando extends Fragment implements OnMapReadyCallback {


    //keys
    private static final String TAG = "FragmentBando";
    public static final String KEY_MANG_LATLNG_NHATRO = "manglatlngnhatro";

    //maps
    View viewBando;
    GoogleMap map;
    Marker marker;

    //widgets
    ImageView imgHinhnhatroBTS;
    RatingBar rtBarnhatroBTS;
    TextView tvTennhatroBTS, tvDiachinhatroBTS, tvSophongtrongBTS, tvTrangthaighepBTS, tvIdkhutroBTS, btnXemthemCTKT;
    Button btnChuyenden, btnAnhvetinh;
    CardView btnXemthemBTS;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    //vars
    private ArrayList<LatLngPhongGhep> arrLatLngNhatro1 = new ArrayList<>();
    private ArrayList<LatLng> arrLatLng = new ArrayList<>();
    private ArrayList<Phong> arrayListPhong = new ArrayList<>();

    //urls
    String urlLatLng = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layMangLatlngNhaTro.php";
    String urlphong = "https://dbnhatrovungtau.000webhostapp.com/Server/Server/layTatCaThongtinPhong.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBando = inflater.inflate(R.layout.fragment_bando, container, false);
        layoutBottomSheet = viewBando.findViewById(R.id.bottom_sheet);
        getDuLieuLatLng(urlLatLng);
        getDuLieuPhong(urlphong);
        mapped();
        taoDuLieuMau();
        return viewBando;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }


    private void getDuLieuLatLng(String urls) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //    Idkhutro,Tenkhutro, Lat, Lng, Tentp, Img, Slphong, Diachi, Mota
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrLatLngNhatro1.add(new LatLngPhongGhep(jsonObject.getString("Idkhutro"),
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
                                Toast.makeText(getContext(), "Không parse được", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                        Log.d(TAG, "onResponse: đã nhận dữ liệu nhà trọ" + arrLatLngNhatro1.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuPhong(String urls) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //Idphong,Giaphong,Trangthai,Ghep,Dientich,Idkhutro,Imgpt,Mota
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrayListPhong.add(new Phong(jsonObject.getString("Idphong"),
                                        jsonObject.getString("Giaphong"),
                                        jsonObject.getString("Trangthai"),
                                        jsonObject.getString("Ghep"),
                                        jsonObject.getString("Dientich"),
                                        jsonObject.getString("Idkhutro"),
                                        jsonObject.getString("Imgpt"),
                                        jsonObject.getString("Mota")));
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "Không parse được", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                        Log.d(TAG, "onResponse: Đã nhận dữ liệu phòng trọ " + arrayListPhong.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void taoDuLieuMau() {
        arrLatLng.add(new LatLng(10.354307, 107.081310));
        arrLatLng.add(new LatLng(10.366435, 107.081326));
        arrLatLng.add(new LatLng(10.363619, 107.091156));
        arrLatLng.add(new LatLng(10.353987, 107.091931));
    }


    private void batSukienLocVaXemBanDo() {
        final LatLng vt = new LatLng(10.360517, 107.085335);
        final LatLng ha = new LatLng(15.878330, 108.336408);
        final LatLng dn = new LatLng(16.050676, 108.207483);
        final LatLng hn = new LatLng(21.025688, 105.837115);
        final LatLng sg = new LatLng(10.804583, 106.647094);

        btnChuyenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChuyenden.setBackgroundResource(R.drawable.bg_setting);
                btnAnhvetinh.setBackgroundResource(R.drawable.bogoc_btn_phai);
                PopupMenu popupMenu = new PopupMenu(getActivity(), btnChuyenden);
                //cú pháp trên là dùng để hiện popup menu trong this và trong view cụ thể ví dụ btnmenu
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_dichuyen, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itemVungtau:
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(vt, 12));
                                break;
                            case R.id.itemDanang:
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(dn, 12));
                                break;
                            case R.id.itemHanoi:
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(hn, 12));
                                break;
                            case R.id.itemHoian:
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(ha, 12));
                                break;
                            case R.id.itemSaigon:
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sg, 12));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        btnAnhvetinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChuyenden.setBackgroundResource(R.drawable.bogoc_btn_trai);
                btnAnhvetinh.setBackgroundResource(R.drawable.bg_setting);
                if (btnAnhvetinh.getText().equals("ẢNH VỆ TINH")) {
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    btnAnhvetinh.setText("ẢNH BẢN ĐỒ");
                } else {
                    if (btnAnhvetinh.getText().equals("ẢNH BẢN ĐỒ")) {
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        btnAnhvetinh.setText("ẢNH VỆ TINH");
                    }
                }

            }
        });
    }

    private void mapped() {
        btnChuyenden = viewBando.findViewById(R.id.btnChuyenden);
        btnAnhvetinh = viewBando.findViewById(R.id.btnLoctheophuong);
        imgHinhnhatroBTS = viewBando.findViewById(R.id.imgHinhnhatroBTS);
        rtBarnhatroBTS = viewBando.findViewById(R.id.rtbarNhatroBTS);
        tvTennhatroBTS = viewBando.findViewById(R.id.tvTennhatroBTS);
        tvDiachinhatroBTS = viewBando.findViewById(R.id.tvDiachinhatroBTS);
        tvSophongtrongBTS = viewBando.findViewById(R.id.tvSophongtrongBTS);
        tvTrangthaighepBTS = viewBando.findViewById(R.id.tvTrangthaighepBTS);
        btnXemthemCTKT = viewBando.findViewById(R.id.btnXemthemCTKT);
        tvIdkhutroBTS = viewBando.findViewById(R.id.tvIdkhutroBTS);
    }

    public int xulySophongcontrong(int position) {
        int sophongtrong = 0;
        for (int i = 0; i < arrayListPhong.size(); i++) {
            if (arrayListPhong.get(i).getIdkhutro().equals(arrLatLngNhatro1.get(position).getIdnt()) && arrayListPhong.get(i).getTrangthai().equals("0")) {
                sophongtrong = sophongtrong + 1;
            }
        }
        Log.d(TAG, "xulySophongcontrong: " + sophongtrong);
        return sophongtrong;
    }

    public String xulyTrangthaighep(int position) {
        String trangthaighep = "";
        int dem = 0;
        for (int i = 0; i < arrayListPhong.size(); i++) {
            if (arrayListPhong.get(i).getIdkhutro().equals(arrLatLngNhatro1.get(position).getIdnt()) && arrayListPhong.get(i).getGhep().equals("1")) {
                dem++;
            }
        }

        if (dem > 0) {
            trangthaighep = "Có phòng ghép";
        } else {
            trangthaighep = "Không có phòng ghép";
        }
        return trangthaighep;
    }

    public void batSuKienBottomSheet() {
        ButterKnife.bind(getActivity());
        try {
            sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        } catch (Exception e) {
            Log.d(TAG, "batSuKienBottomSheet: " + e.toString());
        }
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                String idkt = marker.getTitle().trim();
                for (int i = 0; i < arrLatLngNhatro1.size(); i++) {
                    if (arrLatLngNhatro1.get(i).getIdnt().equals(idkt)) {
                        Picasso.with(getContext()).load(arrLatLngNhatro1.get(i).getImg()).into(imgHinhnhatroBTS);
                        tvTennhatroBTS.setText(arrLatLngNhatro1.get(i).getTennt());
                        tvDiachinhatroBTS.setText(arrLatLngNhatro1.get(i).getDiachi());
                        tvSophongtrongBTS.setText("Còn trống " + xulySophongcontrong(i) + " phòng");
                        tvTrangthaighepBTS.setText(xulyTrangthaighep(i));
                        tvIdkhutroBTS.setText(arrLatLngNhatro1.get(i).getIdnt());
                        rtBarnhatroBTS.setRating(Float.parseFloat(arrLatLngNhatro1.get(i).getRate()));
                    }
                }
                //Trang thái kiểm tra bottom sheet
//                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//
//                } else {
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
                return true;
            }
        });

        //Xử lý khi bấm vào nút xem thêm
        btnXemthemCTKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Idkhutro,Tenkhutro, Lat, Lng, Tentp, Img, Slphong, Diachi, Mota
                Log.d(TAG, "onClick: " + tvTennhatroBTS.getText().toString());
                Intent intent = new Intent(getActivity(), ChiTietKhuTroActivity.class);
                intent.putExtra("mangkhutro", arrLatLngNhatro1);
                intent.putExtra("mangphongtro", arrayListPhong);
                intent.putExtra("Idkhutro", tvIdkhutroBTS.getText().toString());
                intent.putExtra("Tenkhutro", tvTennhatroBTS.getText().toString());
                intent.putExtra("Diachi", tvDiachinhatroBTS.getText().toString());
                intent.putExtra("Sophongtrong", tvSophongtrongBTS.getText().toString());
                intent.putExtra("Trangthaighep", tvTrangthaighepBTS.getText().toString());
                intent.putExtra("Danhgia", rtBarnhatroBTS.getRating() + "");
                startActivity(intent);
            }
        });


        //Xử lý sự kiện khi bottom sheet mở lên hoặc xuống
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //làm gì đó khi bottom sheet ẩn đi
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //làm gì đó khi bottom sheet mở lên
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //làm gì đó khi bottom sheet đóng xuống
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //làm gì đó khi bottom sheet đang lướt
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");
        map = googleMap;
        LatLng vt = new LatLng(10.360517, 107.085335);
        map.moveCamera(CameraUpdateFactory.newLatLng(vt));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(vt, 12));
        map.getUiSettings().setZoomControlsEnabled(true);



        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                for (int i = 0; i < arrLatLngNhatro1.size(); i++) {
                    LatLng point = new LatLng(Float.parseFloat(arrLatLngNhatro1.get(i).getLat() + ""), Float.parseFloat(arrLatLngNhatro1.get(i).getLng() + ""));
                    marker = map.addMarker(new MarkerOptions().position(point).title(arrLatLngNhatro1.get(i).getIdnt()).icon(BitmapDescriptorFactory.fromBitmap(createMarker(getActivity(), arrLatLngNhatro1.get(i).getTennt()))));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
                }
                batSukienLocVaXemBanDo();
                batSuKienBottomSheet();
            }
        });
    }

    public static Bitmap createMarker(Context context, String tennt) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView tvTenkhutro = marker.findViewById(R.id.tvTenkhutroMarker);
        tvTenkhutro.setText(tennt);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }
}
