package cn.wingene.mallxm.display.home.secondMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;

/**
 * Created by wangcq on 2017/8/14.
 * 精选适配器
 */

public class SelectItemAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_select_item_layout, parent,
                false);
        return new SelectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SelectHolder selectHolder = (SelectHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class SelectHolder extends RecyclerView.ViewHolder {

        public SelectHolder(View itemView) {
            super(itemView);
        }
    }
}
