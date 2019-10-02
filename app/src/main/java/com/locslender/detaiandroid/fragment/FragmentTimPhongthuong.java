package com.locslender.detaiandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.model.Phongghep;
import com.locslender.detaiandroid.screen.PhongThuongActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentTimPhongthuong extends Fragment implements View.OnClickListener {
    public OnTransferArrayListener mOnTransferArrayListener;

    public interface OnTransferArrayListener {
        public void setArrayFromFragmenttoPhongThuongActivity(ArrayList<Phongghep> arrayList);
    }

    private static final String TAG = "FragmentTimPhongghep";

    //Key
    public static final String KEY_MANG = "mangPhong";
    public static final String ALL_GIA = "Tất cả các mức giá";
    public static final String ALL_PHUONG = "Tất cả các phường";
    public static final String ALL_THANH_PHO = "Tất cả các thành phố";
    public static final String GIA_DUOI_1CU = "Dưới 1.000.000vnđ";
    public static final String GIA_1CU_TOI_2CU = "Từ 1.000.000vnđ đến 2.000.000vnđ";
    public static final String GIA_TREN_2CU = "Trên 2.000.000vnđ";
    public static final String PHUONG_THANG_NHAT = "Phường Thắng Nhất";
    public static final String PHUONG_THANG_NHI = "Phường Thắng Nhì";
    public static final String PHUONG_THANG_TAM = "Phường Thắng Tam";
    public static final String PHUONG_BON = "Phường Bốn";
    public static final String PHUONG_NAM = "Phường Năm";
    public static final String PHUONG_SAU = "Phường Sáu";
    public static final String PHUONG_BAY = "Phường Bảy";
    public static final String PHUONG_TAM = "Phường Tám";
    public static final String PHUONG_CHIN = "Phường Chín";
    public static final String PHUONG_MUOI = "Phường Mười";
    public static final String PHUONG_RACH_DUA = "Phường Rạch Dừa";
    public static final String PHUONG_MUOI_MOT = "Phường Mười Một";
    public static final String PHUONG_MUOI_HAI = "Phường Mười Hai";
    public static final String VUNG_TAU = "Vũng Tàu";
    public static final String DA_NANG = "Đà Nẵng";
    public static final String HOI_AN = "Hội An";
    public static final String HA_NOI = "Hà Nội";
    public static final String SAI_GON = "Sài Gòn";

    //widgets
    private View view;
    private Button btnSearch;
    private Spinner spnTimtheogia, spnTimtheophuong, spnTimtheothanhpho;
    private RadioButton rdbCogac, rdbKhonggac;

    //vars
    private ArrayList<Phongghep> arrPhongghepFRM_THO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_phongghep, container, false);
        mapped();
        recieveDataArrayPhongGhep();
        addItemforSpinner();
        catchEventClickforBtnSearch();
        return view;
    }

    //Nhận dữ liệu từ mảng arrPhongghep bên PhongghepActivity đổ qua
    private void recieveDataArrayPhongGhep() {
        arrPhongghepFRM_THO = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            arrPhongghepFRM_THO = (ArrayList<Phongghep>) bundle.getSerializable(PhongThuongActivity.KEY_MANG_THO);
        }
        Log.d(TAG, "recieveDataArrayPhongGhep: kích cỡ mãng sau khi nhận dữ liệu " + arrPhongghepFRM_THO.size());
    }

    public void catchEventClickforBtnSearch() {
        btnSearch.setOnClickListener(this);
    }

    private void addItemforSpinner() {
        //Spinner Tìm theo giá
        List<String> listMucgia = new ArrayList<>();
        listMucgia.add("Tất cả các mức giá");
        listMucgia.add("Dưới 1.000.000vnđ");
        listMucgia.add("Từ 1.000.000vnđ đến 2.000.000vnđ");
        listMucgia.add("Trên 2.000.000vnđ");
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listMucgia);
        spnTimtheogia.setAdapter(adapter);

        //spinner Tìm theo phường
        List<String> listPhuong = new ArrayList<>();
        listPhuong.add("Tất cả các phường");
        listPhuong.add("Phường Thắng Nhất");
        listPhuong.add("Phường Thắng Nhì");
        listPhuong.add("Phường Thắng Tam");
        listPhuong.add("Phường Bốn");
        listPhuong.add("Phường Năm");
        listPhuong.add("Phường Sáu");
        listPhuong.add("Phường Bảy");
        listPhuong.add("Phường Tám");
        listPhuong.add("Phường Chín");
        listPhuong.add("Phường Mười");
        listPhuong.add("Phường Rạch Dừa");
        listPhuong.add("Phường Mười Một");
        listPhuong.add("Phường Mười Hai");
        ArrayAdapter<String> adapterphuong = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listPhuong);
        spnTimtheophuong.setAdapter(adapterphuong);

        //Spinner Tìm theo Thành phố
        List<String> listThanhpho = new ArrayList<>();
        listThanhpho.add("Tất cả các thành phố");
        listThanhpho.add("Vũng Tàu");
        listThanhpho.add("Đà Nẵng");
        listThanhpho.add("Hội An");
        listThanhpho.add("Sài Gòn");
        listThanhpho.add("Hà Nội");

        ArrayAdapter<String> adapterThanhpho = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listThanhpho);
        spnTimtheothanhpho.setAdapter(adapterThanhpho);
    }

    private void mapped() {
        btnSearch = view.findViewById(R.id.btnSearch);
        spnTimtheogia = view.findViewById(R.id.spnTimtheogia);
        spnTimtheophuong = view.findViewById(R.id.spnTimtheophuong);
        spnTimtheothanhpho = view.findViewById(R.id.spnTimtheothanhpho);
        rdbCogac = view.findViewById(R.id.rdbCogacPG);
        rdbKhonggac = view.findViewById(R.id.rdbKhonggacPG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mOnTransferArrayListener = (OnTransferArrayListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must override methode");
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: " + arrPhongghepFRM_THO.size());

        ArrayList<Phongghep> arrPhongghepFRM_XULY = new ArrayList<>();
        ArrayList<Phongghep> arrPhongghepFRM_XULY_GIA = new ArrayList<>();
        ArrayList<Phongghep> arrPhongghepFRM_XULY_PHUONG = new ArrayList<>();
        ArrayList<Phongghep> arrPhongghepFRM_XULY_THANHPHO = new ArrayList<>();
        ArrayList<Phongghep> arrPhongghepFRM_XULY_GAC = new ArrayList<>();

        String mGia = spnTimtheogia.getSelectedItem().toString().trim();
        String mPhuong = spnTimtheophuong.getSelectedItem().toString().trim();
        String mThanhpho = spnTimtheothanhpho.getSelectedItem().toString().trim();
        Boolean mCogac = rdbCogac.isChecked();
        Boolean mKhonggac = rdbKhonggac.isChecked();

        //Xử lý giá
        if (mGia == GIA_DUOI_1CU) {
            for (int i = 0; i < arrPhongghepFRM_THO.size(); i++) {
                if (Float.parseFloat(arrPhongghepFRM_THO.get(i).getGiaphong()) < 1000000) {
                    arrPhongghepFRM_XULY_GIA.add(arrPhongghepFRM_THO.get(i));
                }
            }
        } else {
            if (mGia == GIA_1CU_TOI_2CU) {
                for (int i = 0; i < arrPhongghepFRM_THO.size(); i++) {
                    if (Float.parseFloat(arrPhongghepFRM_THO.get(i).getGiaphong()) > 1000000 && Float.parseFloat(arrPhongghepFRM_THO.get(i).getGiaphong()) < 2000000) {
                        arrPhongghepFRM_XULY_GIA.add(arrPhongghepFRM_THO.get(i));
                    }
                }
            } else {
                if (mGia == GIA_TREN_2CU) {
                    for (int i = 0; i < arrPhongghepFRM_THO.size(); i++) {
                        if (Float.parseFloat(arrPhongghepFRM_THO.get(i).getGiaphong()) > 2000000) {
                            arrPhongghepFRM_XULY_GIA.add(arrPhongghepFRM_THO.get(i));
                        }
                    }
                } else {
                    if (mGia == ALL_GIA) {
                        for (int i = 0; i < arrPhongghepFRM_THO.size(); i++) {
                            arrPhongghepFRM_XULY_GIA.add(arrPhongghepFRM_THO.get(i));
                        }
                    }
                }
            }
        }

        Log.d(TAG, "onClick: xử lý giá" + arrPhongghepFRM_XULY_GIA.size());

        //Xử lý phường
        switch (mPhuong) {
            case PHUONG_THANG_NHAT:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_THANG_NHAT)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_THANG_NHI:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_THANG_NHI)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_THANG_TAM:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_THANG_TAM)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_BON:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_BON)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_NAM:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_NAM)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_SAU:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_SAU)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_BAY:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_BAY)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_TAM:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_TAM)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_CHIN:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_CHIN)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_MUOI:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_MUOI)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_MUOI_MOT:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_MUOI_MOT)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_MUOI_HAI:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_MUOI_HAI)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case PHUONG_RACH_DUA:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    if (arrPhongghepFRM_XULY_GIA.get(i).getPhuong().equals(PHUONG_RACH_DUA)) {
                        arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                    }
                }
                break;
            case ALL_PHUONG:
                for (int i = 0; i < arrPhongghepFRM_XULY_GIA.size(); i++) {
                    arrPhongghepFRM_XULY_PHUONG.add(arrPhongghepFRM_XULY_GIA.get(i));
                }
                break;
        }
        Log.d(TAG, "onClick: xử lý phường" + arrPhongghepFRM_XULY_PHUONG.size());

        //Xử lý thành phố
        switch (mThanhpho) {
            case VUNG_TAU:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    if (arrPhongghepFRM_XULY_PHUONG.get(i).getTentp().equals(VUNG_TAU)) {
                        arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                    }
                }
                break;
            case DA_NANG:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    if (arrPhongghepFRM_XULY_PHUONG.get(i).getTentp().equals(DA_NANG)) {
                        arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                    }
                }
                break;
            case HOI_AN:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    if (arrPhongghepFRM_XULY_PHUONG.get(i).getTentp().equals(HOI_AN)) {
                        arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                    }
                }
                break;
            case HA_NOI:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    if (arrPhongghepFRM_XULY_PHUONG.get(i).getTentp().equals(HA_NOI)) {
                        arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                    }
                }
                break;
            case SAI_GON:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    if (arrPhongghepFRM_XULY_PHUONG.get(i).getTentp().equals(SAI_GON)) {
                        arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                    }
                }
                break;
            case ALL_THANH_PHO:
                for (int i = 0; i < arrPhongghepFRM_XULY_PHUONG.size(); i++) {
                    arrPhongghepFRM_XULY_THANHPHO.add(arrPhongghepFRM_XULY_PHUONG.get(i));
                }
                break;
        }
        Log.d(TAG, "onClick: xử lý thành phố" + arrPhongghepFRM_XULY_THANHPHO.size());
        //Xử lý gác
        if (mCogac) {
            for (int i = 0; i < arrPhongghepFRM_XULY_THANHPHO.size(); i++) {
                if (arrPhongghepFRM_XULY_THANHPHO.get(i).getTrangthaigac().equals("1")) {
                    arrPhongghepFRM_XULY_GAC.add(arrPhongghepFRM_XULY_THANHPHO.get(i));
                }
            }
        } else {
            if (mKhonggac) {
                for (int i = 0; i < arrPhongghepFRM_XULY_THANHPHO.size(); i++) {
                    if (arrPhongghepFRM_XULY_THANHPHO.get(i).getTrangthaigac().equals("0")) {
                        arrPhongghepFRM_XULY_GAC.add(arrPhongghepFRM_XULY_THANHPHO.get(i));
                    }
                }
            }
        }
        Log.d(TAG, "recieveDataArrayPhongGhep: xử lý gác " + arrPhongghepFRM_XULY_GAC.size());


        try {
            mOnTransferArrayListener.setArrayFromFragmenttoPhongThuongActivity(arrPhongghepFRM_XULY_GAC);
            FragmentTimPhongthuong fragmentTimPhongghep = (FragmentTimPhongthuong) getFragmentManager().findFragmentById(R.id.fragment_container_phongthuong);
            getFragmentManager().beginTransaction().remove(fragmentTimPhongghep).commit();
        } catch (Exception e) {
            Log.d(TAG, "onClick: " + e.toString());
        }


    }

}

