package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;

/**
 * Created by wangcq on 2017/8/11.
 * 天天特价
 */

public class DaySpecialPriceAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_person_recommend_item_layout,
                parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return new DaySpecialPrice(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class DaySpecialPrice extends RecyclerView.ViewHolder {

        public DaySpecialPrice(View itemView) {
            super(itemView);
        }
    }
}
