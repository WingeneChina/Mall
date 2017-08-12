package cn.wingene.mall.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import junze.java.util.CheckUtil;
import junze.java.util.FilterUtil;

import junze.android.util.BitmapUtil;

/**
 * Created by Wingene on 2017/8/12.
 */

public class TextViewHtmlLoader {
    private final Activity mActivity;
    private final Handler mHandler = new Handler();
    private boolean needLoadNextBitmap = true;
    private int bitmapToatal = 0;
    private int bitmapOnce = 0;
    private boolean hasDestory;

    public TextViewHtmlLoader(Activity activity) {
        this.mActivity = activity;
        this.hasDestory = false;
    }

    public void loadTextViewByHtmlCode(final TextView tvContent, final String content, final Runnable runable) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int windowWidth = dm.widthPixels;
        new Thread(new Runnable() {

            @Override
            public void run() {
                final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).build();
                ImageGetter imgGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        if (!needLoadNextBitmap) {
                            return null;
                        }
                        Drawable drawable = null;
                        Bitmap bitmap = null;
                        if(CheckUtil.isUrl(source)){
                            bitmap = ImageLoader.getInstance().loadImageSync(source, options);
                            bitmap = BitmapUtil.createBitmapFitX(bitmap, windowWidth);
                            bitmapOnce++;
                            if (bitmapOnce > bitmapToatal) {
                                bitmapToatal++;
                                needLoadNextBitmap = false;
                            }
                            if (bitmap != null) {
                                drawable = new BitmapDrawable(mActivity.getResources(), bitmap);
                            }
                        }else if(CheckUtil.isNumber(source)){
                            int rId = Integer.parseInt(source);
                            drawable = mActivity.getResources().getDrawable(rId);
                        }else{
                            drawable = Drawable.createFromPath(source);
                        }
                        // Important
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
//                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        }
                        return drawable;
                    }
                };
                while (!hasDestory) {
                    bitmapOnce = 0;
                    needLoadNextBitmap = true;
                    final Spanned span = Html.fromHtml(FilterUtil.filterNull(content), imgGetter, null);
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            tvContent.setText(span);
                            if (runable != null) {
                                runable.run();
                            }
                        }
                    });
                    if (needLoadNextBitmap == true) {
                        break;
                    }
                }
            }
        }).start();
    }

    public void onDestory(){
        hasDestory = true;
        System.gc();
    }
}
