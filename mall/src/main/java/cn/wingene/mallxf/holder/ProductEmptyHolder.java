package cn.wingene.mallxf.holder;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import junze.android.ui.ViewHolder;

import cn.wingene.mall.R;

/**
 * Created by Wingene on 2017/9/10.
 */

public class ProductEmptyHolder extends ViewHolder {

    private RelativeLayout rlytInvalid;
    private TextView tvInvalidMsg;

    public ProductEmptyHolder(Context context) {
        super(context, R.layout.holder_product_empty);
    }

    @Override
    protected void initComponent() {
        rlytInvalid = (RelativeLayout) super.findViewById(R.id.rlyt_invalid);
        tvInvalidMsg = (TextView) super.findViewById(R.id.tv_invalid_msg);
    }

    public void setOnClickListener(OnClickListener l) {
        rlytInvalid.setOnClickListener(l);
    }

    public void setMsg(CharSequence text) {
        tvInvalidMsg.setText(text);
    }
}
