package com.locslender.detaiandroid.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.locslender.detaiandroid.model.LatLngPhongThuong;
import com.locslender.detaiandroid.model.Phongghep;
import com.locslender.detaiandroid.screen.PhongGhepActivity;

import java.util.ArrayList;

public class FragmentBandoPhongGhep extends Fragment implements OnMapReadyCallback {

    public OnTransferLatlngArrayListener mOnTransferLatlngArrayListener;

    public interface OnTransferLatlngArrayListener {
        public void setLatlngArray(ArrayList<LatLng> latlngArray);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Activity activity= (Activity) context;
//        try {
//            mOnTransferLatlngArrayListener= (OnTransferLatlngArrayListener) activity;
//        }catch (ClassCastException e){
//            throw  new ClassCastException(activity.toString()+"Must override methods");
//        }
//    }

    private View view;
    private GoogleMap map;
    private ArrayList<Phongghep> arrPhongGhepFRM_THO;
    private ArrayList<LatLngPhongGhep> arrLatLngFRM_THO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bando_phongghep_phongthuong, container, false);
        recieveDataLatLngArrayPhongGhep();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapPG);
        mapFragment.getMapAsync(this);
    }

    //Nhận dữ liệu mảng Latlng phòng ghép từ PhongGhepActivity
    private void recieveDataLatLngArrayPhongGhep() {
        arrLatLngFRM_THO = new ArrayList<>();
        Bundle bundle1 = getArguments();
        if (bundle1 != null) {
            arrLatLngFRM_THO = (ArrayList<LatLngPhongGhep>) bundle1.getSerializable(PhongGhepActivity.KEY_MANG_THO_LATLNG);
        }
        Toast.makeText(getContext(), arrLatLngFRM_THO.size() + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                for (int i = 0; i < arrLatLngFRM_THO.size(); i++) {

                    LatLng vt = new LatLng(10.360517, 107.085335);
                    LatLng ha = new LatLng(15.878330, 108.336408);
                    LatLng dn = new LatLng(16.050676, 108.207483);
                    LatLng hn = new LatLng(21.025688, 105.837115);
                    LatLng sg = new LatLng(10.804583, 106.647094);
                    LatLng point = new LatLng(Float.parseFloat(arrLatLngFRM_THO.get(i).getLat() + ""), Float.parseFloat(arrLatLngFRM_THO.get(i).getLng() + ""));
                    switch (arrLatLngFRM_THO.get(i).getTentp()) {
                        case "Vũng Tàu":
                            map.moveCamera(CameraUpdateFactory.newLatLng(vt));
                            break;
                        case "Đà Nẵng":
                            map.moveCamera(CameraUpdateFactory.newLatLng(dn));
                            break;
                        case "Hà Nội":
                            map.moveCamera(CameraUpdateFactory.newLatLng(hn));
                            break;
                        case "Hội An":
                            map.moveCamera(CameraUpdateFactory.newLatLng(ha));
                            break;
                        case "Sài Gòn":
                            map.moveCamera(CameraUpdateFactory.newLatLng(sg));
                            break;
                        default:
                            map.moveCamera(CameraUpdateFactory.newLatLng(vt));

                    }

                    map.animateCamera(CameraUpdateFactory.zoomTo(12f));
                    Marker nt = map.addMarker(new MarkerOptions().position(point).title(arrLatLngFRM_THO.get(i).getIdnt()).icon(BitmapDescriptorFactory.fromBitmap(createMarker(getActivity(), arrLatLngFRM_THO.get(i).getTennt()))));
                }

            }
        });

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
