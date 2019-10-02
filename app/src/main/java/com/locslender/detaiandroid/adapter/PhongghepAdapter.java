package com.locslender.detaiandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.model.Phongghep;
import com.locslender.detaiandroid.screen.PhongGhepActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class PhongghepAdapter extends PagerAdapter {

    Context context;
    ArrayList<Phongghep> arrPhongghep;

    public PhongghepAdapter(Context context, ArrayList<Phongghep> arrPhongghep) {
        this.context = context;
        this.arrPhongghep = arrPhongghep;
    }

    @Override
    public int getCount() {
        return arrPhongghep.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.layout_test, null);
        TextView tvTenpt, tvGiapt, tvDientichpt, tvDiachipt;
        ImageView imgDaidien, imgBackground;
        //Ánh xạ qua các view bên layout_phongghep_item
        tvTenpt = view.findViewById(R.id.tvTenphongtro);
        tvDientichpt = view.findViewById(R.id.tvDientichphongtro);
        tvDiachipt = view.findViewById(R.id.tvDiachiphongtro);
        imgDaidien = view.findViewById(R.id.imgAnhdaidien);
        imgBackground = view.findViewById(R.id.imgBackground);


        //Gắn dữ liệu lên phần layout
        Phongghep phongghep = arrPhongghep.get(position);
        tvTenpt.setText(phongghep.getTenkhutro());
        tvDientichpt.setText(phongghep.getDientich()+"m");
        tvDiachipt.setText(phongghep.getDiachi());
        Picasso.with(context).load(phongghep.getImgpt()).into(imgDaidien);
        Picasso.with(context).load(phongghep.getImg()).into(imgBackground);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do trong Class không phải Activity nên phải dùng view.getContext() để lấy ra context hiện tại
                view.getContext().startActivity(new Intent(v.getContext(), PhongGhepActivity.class));
            }
        });

        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
