package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.WeekItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 每周新品
 */

public class PerWeekProductAdapter extends RecyclerView.Adapter {
    private List<RecommendModel.DataBean.NewBean.ProductListBean> mProductListBeen;

    public PerWeekProductAdapter(List<RecommendModel.DataBean.NewBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WeekItemHolder.create(parent);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WeekItemHolder itemHolder = (WeekItemHolder) holder;
        final RecommendModel.DataBean.NewBean.ProductListBean item = mProductListBeen.get(position);
        itemHolder.onBindViewHolder(item, position);
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }
}
