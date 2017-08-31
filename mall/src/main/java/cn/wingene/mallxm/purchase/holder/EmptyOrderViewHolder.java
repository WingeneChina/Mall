package cn.wingene.mallxm.purchase.holder;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import junze.android.ui.ViewHolder;

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;

/**
 * Created by Wingene on 2017/8/30.
 */

public class EmptyOrderViewHolder extends ViewHolder {

    private TextView tvGoHome;

    @Override
    protected void initComponent() {
        tvGoHome = (TextView) super.findViewById(R.id.tv_go_home);
    }

    public EmptyOrderViewHolder(final Context context) {
        super(context, R.layout.holder_empty_order);
        tvGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startMainActivity(context);
            }
        });
    }
}
