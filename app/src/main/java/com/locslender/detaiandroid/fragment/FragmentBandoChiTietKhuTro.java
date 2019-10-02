package com.locslender.detaiandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.locslender.detaiandroid.R;

public class FragmentBandoChiTietKhuTro extends Fragment implements OnMapReadyCallback {
    private static final String ARG_LAT = "ArgLat";
    private static final String ARG_LNG = "ArgLng";
    private static final String ARG_TENNT = "Tennt";
    private View view;
    private GoogleMap map;
    public float lat;
    public float lng;
    public String tennt;

    public  static FragmentBandoChiTietKhuTro newInstance(float lat, float lng,String tennt) {
        FragmentBandoChiTietKhuTro fragment = new FragmentBandoChiTietKhuTro();
        Bundle args = new Bundle();
        args.putFloat(ARG_LAT, lat);
        args.putFloat(ARG_LNG, lng);
        args.putString(ARG_TENNT,tennt);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bando_chitietkhutro, container, false);
        if (getArguments()!=null){
            lat=getArguments().getFloat(ARG_LAT);
            lng=getArguments().getFloat(ARG_LNG);
            tennt=getArguments().getString(ARG_TENNT);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_mini_screen);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng ntvt = new LatLng(lat, lng);
        Marker nt = map.addMarker(new MarkerOptions().position(ntvt).icon(BitmapDescriptorFactory.fromBitmap(createMarker(getActivity(), tennt))));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ntvt,15));
    }
    public static Bitmap createMarker(Context context, String tennt) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout_tab_map, null);
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
