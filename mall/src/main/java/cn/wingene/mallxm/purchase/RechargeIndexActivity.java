package cn.wingene.mallxm.purchase;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.limecn.ghmall.R;

import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.androidxf.core.Agent;

import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.ask.AskAmountIndex;
import cn.wingene.mallxm.purchase.ask.AskIntegralIndex;
import cn.wingene.mallxm.purchase.ask.AskIntegralIndex.Response;

/**
 * Created by Wingene on 2017/9/5.
 */

public class RechargeIndexActivity extends MyBaseActivity {
    public static Major major = new Major(RechargeIndexActivity.class);
    //    private BigDecimal mBigDecimal;
    private Integer mIntegral;
    private Double mAmount;
    private boolean mIsDeposit;

    private Tile tlBack;
    private TextView tvTitle;
    private Tile tlList;
    private TextView tvRecharge;
    private TextView tvLabel;
    private TextView tvCount;
    private TextView tvCash;
    private TextView tvIntegral;
    private TextView tvAmount;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        tlList = (Tile) super.findViewById(R.id.tl_list);
        tvRecharge = (TextView) super.findViewById(R.id.tv_recharge);
        tvLabel = (TextView) super.findViewById(R.id.tv_label);
        tvCount = (TextView) super.findViewById(R.id.tv_count);
        tvCash = (TextView) super.findViewById(R.id.tv_cash);
        tvIntegral = (TextView) super.findViewById(R.id.tv_integral);
        tvAmount = (TextView) super.findViewById(R.id.tv_amount);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        askInit();
    }

    private void initListener() {
        tlList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeLogListActivity.major.startForMajor(getActivity(), getMajor());
            }
        });
        tvRecharge.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeSubmitActivity.major.startForMajor(getActivity(), getMajor());
            }
        });
        tvCash.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCashActivity(getActivity());
            }
        });
        tvAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvAmount.isSelected()) {
                    askAmount();
                }
            }
        });
        tvIntegral.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvIntegral.isSelected()) {
                    askIntegral();
                }
            }
        });
    }


    public void askInit() {
        switch (getMajor()) {
        case Major.MAJOR_INTEGRAL:
            askIntegral();
            break;
        case Major.MAJOR_AMOUNT:
        default:
            askAmount();
            break;
        }
    }

    private void askIntegral() {
        ask(new AskIntegralIndex.Request() {
            @Override
            public void updateUI(Response rsp) {
                mIntegral = rsp.getIntegral();
                mIsDeposit = false;
                setMajor(Major.MAJOR_INTEGRAL);
                RechargeIndexActivity.this.updateUI();
            }
        });
    }

    private void askAmount() {
        ask(new AskAmountIndex.Request() {
            @Override
            public void updateUI(AskAmountIndex.Response rsp) {
                mAmount = rsp.getAmount();
                mIsDeposit = rsp.isDeposit();
                setMajor(Major.MAJOR_AMOUNT);
                RechargeIndexActivity.this.updateUI();
            }
        });
    }

    public void updateUI() {
        switch (getMajor()) {
        case Major.MAJOR_INTEGRAL:
            tvTitle.setText("金币余额");
            tvLabel.setText("金币余额(元)");
            tvCount.setText(String.format("%s", mIntegral != null ? mIntegral : 0));
            //            tvRecharge // 充值
            tvCash.setVisibility(mIsDeposit ? View.VISIBLE : View.INVISIBLE);
            tvIntegral.setSelected(true);
            tvAmount.setSelected(false);
            break;
        case Major.MAJOR_AMOUNT:
        default:
            tvTitle.setText("元宝余额");
            tvLabel.setText("元宝余额(元)");
            tvCount.setText(String.format("%.2f", mAmount != null ? mAmount : 0f));
            //            tvRecharge // 充值
            tvCash.setVisibility(mIsDeposit ? View.VISIBLE : View.INVISIBLE);
            tvAmount.setSelected(true);
            tvIntegral.setSelected(false);
            break;
        }
    }


    public static class Major extends Agent.Major {
        public static final int MAJOR_AMOUNT = 0;
        public static final int MAJOR_INTEGRAL = 1;

        public Major(Class<? extends RechargeIndexActivity> clazz) {
            super(clazz);
        }

        public void startForIntegral(Context src) {
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(src);
                return;
            }
            builder(src).setMajor(MAJOR_INTEGRAL).startActivity();
        }

        public void startForAmount(Context src) {
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(src);
                return;
            }
            builder(src).setMajor(MAJOR_AMOUNT).startActivity();
        }
    }
}
