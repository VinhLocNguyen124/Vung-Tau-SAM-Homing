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
import com.locslender.detaiandroid.model.Phong;
import com.locslender.detaiandroid.screen.ChiTietPhongActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KhuTroAdapter extends RecyclerView.Adapter<KhuTroAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Phong> arrPhongtro;
    private OnItemPhongClickListener onItemPhongClickListener;

    public KhuTroAdapter(Context context, ArrayList<Phong> arrPhongtro, OnItemPhongClickListener onItemPhongClickListener) {
        this.context = context;
        this.arrPhongtro = arrPhongtro;
        this.onItemPhongClickListener=onItemPhongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_phongghep_recyclerview_item, viewGroup, false);
        //(*)
        ViewHolder viewHolder = new ViewHolder(view,onItemPhongClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        ImageView imgHinhphongLVP = holder.imgHinhphongLVP;
        TextView tvDientichLVP = holder.tvDientichLVP;
        TextView tvGiaLVP = holder.tvGiaLVP;
        TextView tvTrangthaighepLVP = holder.tvTrangthaighepLVP;
        TextView tvPhongtrongLVP = holder.tvPhongtrongLVP;
        TextView tvSonguoihientaiLVP = holder.tvSonguoihientaiLVP;

        Phong phong = arrPhongtro.get(i);
        Picasso.with(context).load(phong.getImgpt()).into(imgHinhphongLVP);
        tvDientichLVP.setText("Diện tích phòng: "+phong.getDientich()+"m");
        if (phong.getTrangthai().equals("0")){
            tvPhongtrongLVP.setText("phòng còn trống");
            tvSonguoihientaiLVP.setText("Hiện tại chưa có người ở");
        }else {
            tvPhongtrongLVP.setText("phòng đã có người thuê");
            tvSonguoihientaiLVP.setText("Số người ở hiện tại "+phong.getTrangthai()+" người");
        }
        if (phong.getGhep().equals("0")){
            tvTrangthaighepLVP.setText("phòng chưa cần ghép");
        }else {
            tvTrangthaighepLVP.setText("phòng đang cần ghép");
        }
        tvGiaLVP.setText("VNĐ "+formatter.format(Integer.parseInt(phong.getGiaphong())));


    }

    @Override
    public int getItemCount() {
        return arrPhongtro.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgHinhphongLVP;
        TextView tvDientichLVP, tvGiaLVP, tvTrangthaighepLVP, tvPhongtrongLVP,tvSonguoihientaiLVP;
        OnItemPhongClickListener onItemPhongClickListener;

        public ViewHolder(@NonNull View itemView, OnItemPhongClickListener onItemPhongClickListener) {
            super(itemView);
            imgHinhphongLVP = itemView.findViewById(R.id.imgHinhLVP);
            tvDientichLVP = itemView.findViewById(R.id.tvDientichLVP);
            tvPhongtrongLVP = itemView.findViewById(R.id.tvPhongtrongLVP);
            tvSonguoihientaiLVP=itemView.findViewById(R.id.tvSonguoihientaiLVP);
            tvTrangthaighepLVP = itemView.findViewById(R.id.tvTrangthaighepLVP);
            tvGiaLVP = itemView.findViewById(R.id.tvGiaLVP);
            this.onItemPhongClickListener=onItemPhongClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemPhongClickListener.onItemPhongClick(getAdapterPosition());
        }
    }

    public interface OnItemPhongClickListener {
        void onItemPhongClick(int position);
    }

}
