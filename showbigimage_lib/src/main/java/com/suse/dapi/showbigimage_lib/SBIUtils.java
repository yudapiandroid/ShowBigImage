package com.suse.dapi.showbigimage_lib;

import android.content.Context;

/**
 * Created by Administrator on 2017/10/9.
 */
public class SBIUtils {

    public static ImageLoader loader;

    public synchronized static void initImageLoader(Context context,ImageLoader imageLoader){
        loader = imageLoader;
    }

}
