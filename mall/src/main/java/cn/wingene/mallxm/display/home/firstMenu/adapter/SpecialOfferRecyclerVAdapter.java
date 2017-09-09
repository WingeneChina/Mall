package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.PersonRecommendItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/8/27.
 * 天天特价适配器
 */

public class SpecialOfferRecyclerVAdapter extends RecyclerView.Adapter {

    private List<ProductListModel.DataBean.ListBean> mDataBeanList;

    public SpecialOfferRecyclerVAdapter(List<ProductListModel.DataBean.ListBean> dataBeanList) {
        mDataBeanList = dataBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PersonRecommendItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonRecommendItemHolder itemHolder = (PersonRecommendItemHolder) holder;
        final ProductListModel.DataBean.ListBean item = mDataBeanList.get(position);
        itemHolder.onBindViewHolder(item, position);
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }


}
