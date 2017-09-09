package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.BrandItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 品牌大厂适配器
 */

public class BrandProductAdapter extends RecyclerView.Adapter {
    private List<RecommendModel.DataBean.BrandBean.ProductListBean> mProductListBeen;

    public BrandProductAdapter(List<RecommendModel.DataBean.BrandBean.ProductListBean> productListBeen) {
        this.mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return BrandItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final RecommendModel.DataBean.BrandBean.ProductListBean item = mProductListBeen.get(position);
        final BrandItemHolder itemHolder = (BrandItemHolder) holder;
        itemHolder.onBindViewHolder(item, position);
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }

}
