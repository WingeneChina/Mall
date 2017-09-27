package cn.wingene.mallxf.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limecn.ghmall.R;

import java.util.List;


/**
 * Created by wangcq on 2017/8/9.
 * 轮播图适配器
 */

public class ImagePagerAdapter extends PagerAdapter {
    private List<String> urlList;
    private BinnerClickListener mBinnerClickListener;

    public ImagePagerAdapter(List<String> urlList) {
        this.urlList = urlList;
    }

    public void setBinnerClickListener(BinnerClickListener listener) {
        this.mBinnerClickListener = listener;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View mView = LayoutInflater.from(container.getContext()).inflate(R.layout.rollpager_item_layout, container,
                false);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) mView.findViewById(R.id.rollPagerItemV);
        Uri uri = Uri.parse(urlList.get(position));
        simpleDraweeView.setImageURI(uri);

        container.addView(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinnerClickListener != null) {
                    mBinnerClickListener.binnerItemClick(position);
                }
            }
        });

        return mView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    public interface BinnerClickListener {
        void binnerItemClick(int position);
    }
}
