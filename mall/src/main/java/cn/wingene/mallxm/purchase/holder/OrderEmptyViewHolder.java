package cn.wingene.mallxm.purchase.holder;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import junze.android.ui.ViewHolder;

import cn.wingene.mall.R;
import cn.wingene.mallxm.WgMainActivity;

/**
 * Created by Wingene on 2017/8/30.
 */

public class OrderEmptyViewHolder extends ViewHolder {

    private TextView tvInfo;
    private TextView tvGoHome;

    @Override
    protected void initComponent() {
        tvInfo = (TextView) super.findViewById(R.id.tv_info);
        tvGoHome = (TextView) super.findViewById(R.id.tv_go_home);
    }


    public OrderEmptyViewHolder(final Context context) {
        super(context, R.layout.holder_order_empty);
        tvGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WgMainActivity.major.startForPosition(context,0);
            }
        });
    }
    public OrderEmptyViewHolder(final Context context,String info) {
        super(context, R.layout.holder_order_empty);
        tvInfo.setText(info);
        tvGoHome.setVisibility(View.INVISIBLE);
        tvGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WgMainActivity.major.startForPosition(context,0);
            }
        });
    }

    public void setTextAndHideBtn(String info){
        tvInfo.setText(info);
        tvGoHome.setVisibility(View.INVISIBLE);
        tvGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WgMainActivity.major.startForPosition(mContext,0);
            }
        });
    }


}
