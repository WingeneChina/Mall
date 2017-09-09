package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.PersonItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 人气推荐
 */

public class PersonRecommendAdapter extends RecyclerView.Adapter {

    private List<RecommendModel.DataBean.RecommendBean.ProductListBean> mProductListBeen;

    public PersonRecommendAdapter(List<RecommendModel.DataBean.RecommendBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PersonItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonItemHolder itemHolder = (PersonItemHolder) holder;
        final RecommendModel.DataBean.RecommendBean.ProductListBean item = mProductListBeen.get(position);
        itemHolder.onBindViewHolder(item,position);
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


}
