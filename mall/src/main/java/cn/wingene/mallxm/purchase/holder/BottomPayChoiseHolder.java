package cn.wingene.mallxm.purchase.holder;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;

import junze.java.able.ICallBack;

import junze.android.ui.ViewHolder;

import junze.androidxf.kit.AKit;

/**
 * Created by Wingene on 2017/8/27.
 */

public class BottomPayChoiseHolder extends ViewHolder {
    private ImageView ivClose;
    private LinearLayout llytPay;
    private ImageView ivPaySelect;
    private LinearLayout llytWx;
    private ImageView ivWxSelect;
    private TextView tvOk;

    @Override
    protected void initComponent() {
        ivClose = (ImageView) super.findViewById(R.id.iv_close);
        llytPay = (LinearLayout) super.findViewById(R.id.llyt_pay);
        ivPaySelect = (ImageView) super.findViewById(R.id.iv_pay_select);
        llytWx = (LinearLayout) super.findViewById(R.id.llyt_wx);
        ivWxSelect = (ImageView) super.findViewById(R.id.iv_wx_select);
        tvOk = (TextView) super.findViewById(R.id.tv_ok);
    }


    public BottomPayChoiseHolder(Context context) {
        super(context, R.layout.bottom_choise_pay);
    }


    public void dispaly(OnClickListener onClose, final ICallBack<Boolean> callBack) {
        ivClose.setOnClickListener(onClose);
        ivPaySelect.setSelected(false);
        ivWxSelect.setSelected(false);
        bindSwitch(llytPay, ivPaySelect);
        bindSwitch(llytWx, ivWxSelect);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ivPaySelect.isSelected() && !ivWxSelect.isSelected()) {
                    AKit.showToast(getContext(), "请选择支付方式");
                    return;
                }
                callBack.callBack(ivPaySelect.isSelected());
            }
        });
    }

    private static void bindSwitch(LinearLayout llyt, final ImageView ivSelect) {
        llyt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelect.setSelected(!ivSelect.isSelected());
            }
        });
    }


}
