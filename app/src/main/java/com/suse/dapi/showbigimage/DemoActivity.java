package com.suse.dapi.showbigimage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.suse.dapi.showbigimage_lib.ShowPhotoActivity;

/**
 * Created by Administrator on 2017/10/9.
 */
public class DemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViewById(R.id.btn_show_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImages();
            }
        });
    }

    /**
     *
     * 显示图片
     *
     */
    private void showImages() {
        int[] images = new int[]{
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher
        };
        ShowPhotoActivity.startActivity(this,images,0);
    }

}
