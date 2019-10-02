package com.locslender.detaiandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.model.Diadiem;
import com.locslender.detaiandroid.screen.PhongThuongActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiadiemAdapter extends RecyclerView.Adapter<DiadiemAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Diadiem> arrayList;
    //(*)
    OnPlaceClickListener mOnPlaceClickListener;

    //(*)
    public DiadiemAdapter(Context context, ArrayList<Diadiem> arrDiadiem, OnPlaceClickListener onPlaceClickListener) {
        this.mContext = context;
        this.arrayList = arrDiadiem;
        //(*)
        this.mOnPlaceClickListener = onPlaceClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_diadiem_item, viewGroup, false);
        //(*)
        ViewHolder viewHolder = new ViewHolder(view,mOnPlaceClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        TextView tvtentp = holder.tvTentp;
        TextView tvmotatp = holder.tvMotatp;
        ImageView imghinhtp = holder.imgHinhtp;
        ImageView imgicontp = holder.imgIcontp;

        Diadiem diadiem = arrayList.get(i);
        tvtentp.setText(diadiem.getTentp());
        tvmotatp.setText(diadiem.getMotatp());
        Picasso.with(mContext).load(diadiem.getHinhtp()).into(imghinhtp);
        Picasso.with(mContext).load(diadiem.getIcontp()).into(imgicontp);

        //Cách bắt sự kiện thứ 2 cho item recyclerView
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //(*)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTentp, tvMotatp;
        ImageView imgHinhtp, imgIcontp;
        //(*)
        OnPlaceClickListener onPlaceClickListener;

        //(*)
        public ViewHolder(View itemView, OnPlaceClickListener onPlaceClickListener) {
            super(itemView);
            tvTentp = itemView.findViewById(R.id.tvTenThanhPho);
            tvMotatp = itemView.findViewById(R.id.tvMotaThanhPho);
            imgHinhtp = itemView.findViewById(R.id.imgHinhTP);
            imgIcontp = itemView.findViewById(R.id.imgiconTP);
            // (*)
            this.onPlaceClickListener = onPlaceClickListener;

            //(*)
            itemView.setOnClickListener(this);
        }

        // (*)
        @Override
        public void onClick(View v) {
            onPlaceClickListener.onPlaceClick(getAdapterPosition());
        }
    }

    //Bắt sự kiện click cho item Recycler View trong TrangChuActivity bằng interface (*) có liên quan cần chỉnh sửa
    public interface OnPlaceClickListener {
        void onPlaceClick(int position);
    }
}
