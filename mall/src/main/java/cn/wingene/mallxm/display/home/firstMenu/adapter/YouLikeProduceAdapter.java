package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.YouLikeItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 猜你喜欢
 */

public class YouLikeProduceAdapter extends RecyclerView.Adapter {
    private List<RecommendModel.DataBean.LikeBean.ProductListBean> mProductListBeen;

    public YouLikeProduceAdapter(List<RecommendModel.DataBean.LikeBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return YouLikeItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YouLikeItemHolder itemHolder = (YouLikeItemHolder) holder;
        final RecommendModel.DataBean.LikeBean.ProductListBean item = mProductListBeen.get(position);
        itemHolder.onBindViewHolder(position, item);
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }
}
