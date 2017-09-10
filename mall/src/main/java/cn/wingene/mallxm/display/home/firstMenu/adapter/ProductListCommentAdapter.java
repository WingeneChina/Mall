package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.holder.PersonRecommendItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/8/27.
 * 商品分类统一适配器
 */

public class ProductListCommentAdapter extends RecyclerView.Adapter {
    private List<ProductListModel.DataBean.ListBean> mDataBeanList;
    private static final int NOT_DATA = 2;

    public ProductListCommentAdapter(List<ProductListModel.DataBean.ListBean> dataBeanList) {
        mDataBeanList = dataBeanList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (NOT_DATA == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_data_layout, parent,
                    false);

            return new NotDataHolder(view);
        }
        return PersonRecommendItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            final PersonRecommendItemHolder itemHolder = (PersonRecommendItemHolder) holder;
            final ProductListModel.DataBean.ListBean item = mDataBeanList.get(position);
            itemHolder.onBindViewHolder(item, position);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "填充数据异常");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataBeanList.size() == 0) {
            return NOT_DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    class NotDataHolder extends RecyclerView.ViewHolder {

        public NotDataHolder(View itemView) {
            super(itemView);
        }
    }
}
