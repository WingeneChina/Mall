package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.wingene.mallxm.display.holder.YouLikeItemHolder;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/9/4.
 * 居家/服饰..适配器
 */

public class ProductGroupAdapter extends RecyclerView.Adapter {

    private List<ProductListModel.DataBean.ListBean> mProductListBeen;

    public ProductGroupAdapter(List<ProductListModel.DataBean.ListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return YouLikeItemHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YouLikeItemHolder itemHolder = (YouLikeItemHolder) holder;
        final ProductListModel.DataBean.ListBean item = mProductListBeen.get(position);
        itemHolder.onBindViewHolder(position, item);
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }

}
