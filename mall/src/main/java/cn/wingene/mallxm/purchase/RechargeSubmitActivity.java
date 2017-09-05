package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import junze.java.able.ICallBack;
import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.android.util.EditTextUtil;
import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.ask.AskAmountRecharge;
import cn.wingene.mallxm.purchase.ask.AskIntegralRecharge;

/**
 * Created by Wingene on 2017/9/5.
 */

public class RechargeSubmitActivity extends MyBaseActivity {
    public static Major major = new Major(RechargeSubmitActivity.class);

    private Tile tlBack;
    private TextView tvTitle;
    private Tile tlService;
    private EditText etNumber;
    private EditText etPassword;
    private TextView tvSubmit;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        tlService = (Tile) super.findViewById(R.id.tl_service);
        etNumber = (EditText) super.findViewById(R.id.et_number);
        etPassword = (EditText) super.findViewById(R.id.et_password);
        tvSubmit = (TextView) super.findViewById(R.id.tv_submit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_submit);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tlService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeLogListActivity.major.startForMajor(getActivity(),getMajor());
            }
        });
        switch (getMajor()) {
        case Major.MAJOR_INTEGRAL:
            tvTitle.setText("应币充值");
            etNumber.setHint("请输入8位数卡号");
            etNumber.setMaxWidth(8);
            break;
        case Major.MAJOR_AMOUNT:
            tvTitle.setText("游币充值");
            etNumber.setHint("请输入7位数卡号");
            etNumber.setMaxWidth(7);
        default:
            break;
        }
        List<EditText> list = new ArrayList<>();
        list.add(etNumber);
        list.add(etPassword);
        EditTextUtil.setImgOptionNext(list,new ICallBack<EditText>(){
            @Override
            public void callBack(EditText editText) {
                tvSubmit.performClick();
            }
        });

        tvSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String carno = etNumber.getText().toString();
                String pwd = etPassword.getText().toString();
                if (StringUtil.isEmpty(carno)) {
                    showToast("请输入卡号");
                    return;
                }
                if (StringUtil.isEmpty(pwd)) {
                    showToast("请输入密码");
                    return;
                }
                if (getMajor() == Major.MAJOR_AMOUNT) {
                    ask(new AskAmountRecharge.Request(carno, pwd) {
                        @Override
                        public void updateUI(AskAmountRecharge.Response rsp) {
                            finish();
                            RechargeResultActivity.major.startForParams(getActivity(), getMajor(), rsp.getCardNo(),
                                    rsp.getCardPrice());
                        }
                    });
                } else if (getMajor() == Major.MAJOR_INTEGRAL) {
                    ask(new AskIntegralRecharge.Request(carno, pwd) {
                        @Override
                        public void updateUI(AskIntegralRecharge.Response rsp) {
                            finish();
                            RechargeResultActivity.major.startForParams(getActivity(), getMajor(), rsp.getCardNo(),
                                    rsp.getCardPrice());
                        }
                    });
                }
            }
        });
    }

    public static class Major extends Agent.Major {
        public static final int MAJOR_AMOUNT = 0;
        public static final int MAJOR_INTEGRAL = 1;

        public Major(Class<? extends RechargeSubmitActivity> clazz) {
            super(clazz);
        }

        public void startForMajor(Context src,int major) {
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(src);
                return;
            }
            builder(src).setMajor(major).startActivity();
        }

    }
}
