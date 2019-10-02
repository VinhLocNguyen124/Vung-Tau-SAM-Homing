package com.locslender.detaiandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.model.Phongghep;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhongghepListViewAdapter extends ArrayAdapter<Phongghep> {
    private Context context;
    private int resource;
    private ArrayList<Phongghep> arrPhongghep;

    public PhongghepListViewAdapter(Context context, int resource,  ArrayList<Phongghep> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrPhongghep = objects;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_phongghep_listviewitem,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvtennt=convertView.findViewById(R.id.tvTennhatroLVPG);
            viewHolder.tvdientichnt=convertView.findViewById(R.id.tvDientichLVPG);
            viewHolder.tvgiant=convertView.findViewById(R.id.tvGiaLVPG);
            viewHolder.tvdiachint=convertView.findViewById(R.id.tvDiachiLVPG);
            viewHolder.imgHinhnt=convertView.findViewById(R.id.imgHinhLVPG);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        Phongghep phongghep=arrPhongghep.get(position);
        viewHolder.tvtennt.setText(phongghep.getTenkhutro());
        viewHolder.tvdientichnt.setText("Diện tích: "+phongghep.getDientich()+"m");
        viewHolder.tvgiant.setText("Giá phòng: "+formatter.format(Integer.parseInt(phongghep.getGiaphong()))+"vnđ");
        viewHolder.tvdiachint.setText("Địa chỉ: "+phongghep.getDiachi());
        Picasso.with(context).load(phongghep.getImgpt()).into(viewHolder.imgHinhnt);

        return convertView;
    }

    public class ViewHolder{
        private TextView tvtennt;
        private TextView tvdientichnt;
        private TextView tvgiant;
        private TextView tvdiachint;
        private ImageView imgHinhnt;
    }
}
