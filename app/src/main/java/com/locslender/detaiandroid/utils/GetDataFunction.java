package com.locslender.detaiandroid.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.locslender.detaiandroid.model.Diadiem;
import com.locslender.detaiandroid.screen.TrangChuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDataFunction {
    private Context context;
    private String url;
    private Object object;
    private ArrayList<Object> arrObhect;
    private Adapter adapter;

    public GetDataFunction(Context context,String url, Object object, ArrayList<Object> arrObhect,Adapter adapter) {
        this.context=context;
        this.url = url;
        this.object = object;
        this.arrObhect = arrObhect;
        this.adapter=adapter;
    }

    public void getDataFromHosting(final Context context, String url, Object object, final ArrayList<Object> arrObhect){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonobject = response.getJSONObject(i);
                                arrObhect.add(new Diadiem(jsonobject.getString("Tentp"),
                                        jsonobject.getString("Motatp"),
                                        jsonobject.getString("Hinhtp"),
                                        jsonobject.getString("Icontp")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
