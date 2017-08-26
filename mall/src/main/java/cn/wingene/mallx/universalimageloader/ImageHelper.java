package cn.wingene.mallx.universalimageloader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Wingene on 2017/8/26.
 */

public class ImageHelper {
    public static void displayImage(String uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
                .considerExifParams(true).displayer(new FadeInBitmapDisplayer(500)).build();
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }
}
