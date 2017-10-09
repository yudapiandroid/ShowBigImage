package com.suse.dapi.showbigimage_lib;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/10/9.
 */

public interface ImageLoader {
    void loadImage(Context context, String imageUrl, ImageView targetView);
}
