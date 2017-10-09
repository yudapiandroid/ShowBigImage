package com.suse.dapi.showbigimage_lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bm.library.PhotoView;

import java.util.List;


/**
 *
 * 查看大图的时候得activity
 *
 * 调用的方法　是使用　ShowPhotoActivity 的静态　方法　　startActivity
 *
 *
 * Created by admin on 2016/6/3.
 *
 */
public class ShowPhotoActivity extends Activity {

    private TextView nowTextView;
    private TextView totalTextView;
    private ViewPager imagesViewPager;
    private View numberView;

    private ShowImageAdapter adapter;
    private String imagePath;
    private String[] images;
    private int[] resImages;

    private int position;

    private static final String EXTRA_DATA_IMAGES = "images";
    private static final String EXTRA_DATA_PATH = "path";
    private static final String EXTRA_DATA_POSITION = "position";
    private static final String EXTRA_DATA_RES_IMAGES = "res_images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_showphoto);
        initView();
        imagePath = getIntent().getStringExtra(EXTRA_DATA_PATH);
        images = getIntent().getStringArrayExtra(EXTRA_DATA_IMAGES);
        resImages = getIntent().getIntArrayExtra(EXTRA_DATA_RES_IMAGES);
        if(images == null && imagePath == null && resImages == null){
            finish();
            return;
        }
        if(imagePath != null && (images == null || images.length == 0)){
            images = new String[]{imagePath};
        }
        position = getIntent().getIntExtra(EXTRA_DATA_POSITION,0);
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        if(images != null && images.length == 1){
            numberView.setVisibility(View.GONE);
        }

        nowTextView.setText(String.valueOf(position+1));
        if(resImages != null){
            adapter = new ShowImageAdapter(resImages);
            totalTextView.setText(String.valueOf(resImages.length));
        }
        if(images != null){
            adapter = new ShowImageAdapter(images);
            totalTextView.setText(String.valueOf(images.length));
        }
        imagesViewPager.setAdapter(adapter);
        imagesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                nowTextView.setText(String.valueOf(position + 1));
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        if(position != 0){
            imagesViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initView() {
        nowTextView = (TextView) findViewById(R.id.activity_showphoto_tv_now);
        totalTextView = (TextView) findViewById(R.id.activity_showphoto_tv_total);
        imagesViewPager = (ViewPager) findViewById(R.id.activity_showphoto_vp_images);
        numberView = findViewById(R.id.activity_showphoto_ll_number);
    }

    /**
     * @param context
     * @param path  file://  http://
     */
    public static void startActivity(Context context, String path){
        Intent intent = new Intent();
        intent.setClass(context, ShowPhotoActivity.class);
        intent.putExtra(EXTRA_DATA_PATH, path);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param images
     */
    public static void startActivity(Context context, List<String> images){
        if(images != null && images.size() > 0){
            String[] tempStr = new String[images.size()];
            for(int i=0;i<images.size();i++){
                tempStr[i] = images.get(i);
            }
            startActivity(context, tempStr, 0);
        }
    }

    /**
     *  多张图片的时候 position 是当前显示的图片
     *
     * @param context
     * @param images
     * @param position
     */
    public static void startActivity(Context context, List<String> images, int position){
        if(images != null && images.size() > 0){
            String[] tempStr = new String[images.size()];
            for(int i=0;i<images.size();i++){
                tempStr[i] = images.get(i);
            }
            startActivity(context,tempStr,position);
        }
    }

    public static void startActivity(Context context, String[] images, int position){
        Intent intent = new Intent();
        if(images != null && images.length > 0){
            intent.setClass(context,ShowPhotoActivity.class);
            intent.putExtra(EXTRA_DATA_IMAGES, images);
            intent.putExtra(EXTRA_DATA_POSITION,position);
            context.startActivity(intent);
        }
    }


    public static void startActivity(Context context, int[] images, int position){
        Intent intent = new Intent();
        if(images != null && images.length > 0){
            intent.setClass(context,ShowPhotoActivity.class);
            intent.putExtra(EXTRA_DATA_RES_IMAGES,images);
            intent.putExtra(EXTRA_DATA_POSITION,position);
            context.startActivity(intent);
        }
    }


    class ShowImageAdapter extends PagerAdapter {
        private String[] images;
        private int[] resImages;

        public ShowImageAdapter(String[] images) {
            this.images = images;
        }

        public ShowImageAdapter(int[] resImages){
            this.resImages = resImages;
        }


        @Override
        public int getCount() {
            if(images != null){
                return images.length;
            }
            if(resImages != null){
                return resImages.length;
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View root = getLayoutInflater().inflate(R.layout.item_show_photo,null);
            PhotoView photoView = (PhotoView) root.findViewById(R.id.item_show_pv_content);
            photoView.enable();
            photoView.setMaxScale(3f);
            if(images != null){
                if(SBIUtils.loader != null){
                    SBIUtils.loader.loadImage(container.getContext(),images[position],photoView);
                }
            }
            if(resImages != null){
                photoView.setImageResource(resImages[position]);
            }
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            container.addView(root);
            return root;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
