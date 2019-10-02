package com.locslender.detaiandroid.ImageGallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.locslender.detaiandroid.R;
import com.locslender.detaiandroid.adapter.FullSizeAdapter;

public class FullScreenImageActivity extends AppCompatActivity {
    String[] imgs = new String[10];
    int pos;
    ViewPager viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_gallery_fullscreen);
        viewPager = findViewById(R.id.viewPager);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            imgs = intent.getStringArrayExtra("IMAGES");
            pos = (int) intent.getIntExtra("POSITION", 0);
        }

        FullSizeAdapter fullSizeAdapter=new FullSizeAdapter(this,imgs);
        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(pos,true);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if (currentItem >= viewPager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);
            }
        };
        handler.postDelayed(runnable, 4500);
    }
}
