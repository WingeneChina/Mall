package cn.wingene.mallxf.holder;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.limecn.ghmall.R;

import junze.android.ui.ViewHolder;

import junze.androidxf.kit.AKit;

/**
 * Created by Wingene on 2017/9/10.
 */

public class ExceptionViewHolder extends ViewHolder {
    private TextView tvInfo;
    private TextView tvBtn;

    @Override
    protected void initComponent() {
        tvInfo = (TextView) super.findViewById(R.id.tv_info);
        tvBtn = (TextView) super.findViewById(R.id.tv_btn);
    }

    public ExceptionViewHolder(Context context) {
        super(context, R.layout.holder_no_network);
    }


    public void setText(Context context,Exception e) {
        tvInfo.setText(AKit.getFriendExceptionMessage(context,e));
    }
//    public void setText(CharSequence text) {
//        tvInfo.setText(text);
//    }

    public void setOnClickListener(OnClickListener l) {
        tvBtn.setOnClickListener(l);
    }
}
