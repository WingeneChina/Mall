package cn.wingene.mallxm.purchase.adapter;

import java.util.List;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;

/**
 * Created by wangcq on 2017/8/9.
 * 轮播图适配器
 */

public class CommodityImagePagerAdapter extends PagerAdapter {
    private List<String> urlList;

    public CommodityImagePagerAdapter(List<String> urlList) {
        this.urlList = urlList;
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
//        if(position != urlList.size()-2) {
//
//        }else{
//            simpleDraweeView.setImageURI("res://cn.wingene.mall/" +
//                    R.drawable.choujiang);
//        }

        container.addView(mView);

//        mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position == urlList.size() - 2) {
//                    Intent intent = new Intent(container.getContext(), LuckyActivity.class);
//                    container.getContext().startActivity(intent);
//                }
//            }
//        });

        return mView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
