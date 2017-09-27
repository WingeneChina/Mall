package cn.wingene.mallxm.purchase.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;

import junze.java.able.ICallBack;

import junze.android.ui.ViewHolder;
import junze.androidxf.core.Agent;
import junze.androidxf.kit.AKit;

/**
 * Created by Wingene on 2017/8/27.
 */

public class BottomPayChoiseDialog extends BottomSheetDialog {
    BottomPayChoiseHolder mHolder;

    public BottomPayChoiseDialog(@NonNull Context context) {
        super(context);
//        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED |
//                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void show(Agent agent, double sum, View.OnClickListener onClose, final ICallBack<Boolean> callBack) {
        if (mHolder == null) {
            mHolder = new BottomPayChoiseHolder(getContext());
            setContentView(mHolder.getView());
        }
        mHolder.dispaly(sum, onClose, callBack);
//        adjustUnspecified|stateHidden
        agent.showDialog(this);
    }

    public class BottomPayChoiseHolder extends ViewHolder {
        private ImageView ivClose;
        private TextView tvSum;
        private LinearLayout llytPay;
        private ImageView ivPaySelect;
        private LinearLayout llytWx;
        private ImageView ivWxSelect;
        private TextView tvOk;

        @Override
        protected void initComponent() {
            ivClose = (ImageView) super.findViewById(R.id.iv_close);
            tvSum = (TextView) super.findViewById(R.id.tv_sum);
            llytPay = (LinearLayout) super.findViewById(R.id.llyt_pay);
            ivPaySelect = (ImageView) super.findViewById(R.id.iv_pay_select);
            llytWx = (LinearLayout) super.findViewById(R.id.llyt_wx);
            ivWxSelect = (ImageView) super.findViewById(R.id.iv_wx_select);
            tvOk = (TextView) super.findViewById(R.id.tv_ok);
        }


        public BottomPayChoiseHolder(Context context) {
            super(context, R.layout.bottom_choise_pay);
        }


        public void dispaly(double sum, View.OnClickListener onClose, final ICallBack<Boolean> callBack) {
            tvSum.setText(String.format("￥%.2f", sum));
            ivClose.setOnClickListener(onClose);
            ivPaySelect.setSelected(false);
            ivWxSelect.setSelected(false);
            ImageView[] ivAll = {ivPaySelect, ivWxSelect};
            bindSwitch(llytPay, ivAll, ivPaySelect);
            bindSwitch(llytWx, ivAll, ivWxSelect);
            ////
            llytWx.setVisibility(View.GONE); // // TODO: 2017/9/5 微信支付未实现。
            ivPaySelect.setSelected(true);
            ////
            tvOk.setOnClickListener(new View.OnClickListener() {
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


    }

    private static void bindSwitch(LinearLayout llyt, final ImageView[] ivAll, final ImageView ivSelect) {
        llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView iv : ivAll) {
                    iv.setSelected(false);
                }
                ivSelect.setSelected(true);
            }
        });
    }
}
