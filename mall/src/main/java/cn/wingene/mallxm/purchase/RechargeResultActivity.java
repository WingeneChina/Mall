package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import junze.widget.Tile;

import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;

/**
 * Created by Wingene on 2017/9/5.
 */

public class RechargeResultActivity extends MyBaseActivity {
    public static Major major = new Major(RechargeResultActivity.class);

    private Tile tlBack;
    private TextView tvTitle;
    private TextView tvState;
    private TextView tvNumber;
    private TextView tvSubmit;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        tvState = (TextView) super.findViewById(R.id.tv_state);
        tvNumber = (TextView) super.findViewById(R.id.tv_number);
        tvSubmit = (TextView) super.findViewById(R.id.tv_submit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_result);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Params p = major.parseParams(this);
        if (p == null) {
            finish();
            return;
        }
        tvState.setText(getMajor() == Major.MAJOR_AMOUNT ? "游币" : "应币");
        tvNumber.setText(String.format("%￥%s", p.getCardPrice()));
        tvSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static class Major extends Agent.Major {
        public static final int MAJOR_AMOUNT = 0;
        public static final int MAJOR_INTEGRAL = 1;

        public Major(Class<? extends RechargeResultActivity> clazz) {
            super(clazz);
        }

        public void startForParams(Context src, int major, String cardNo, String cardPrice) {
            buildParams(src, new Params(cardNo, cardPrice)).setMajor(major).startActivity();
        }

        public Params parseParams(Activity target) {
            return parseParam(target, Params.class);
        }
    }


    private static class Params {
        /**
         * 卡号	不可
         */
        @SerializedName("CardNo")
        private String cardNo;

        /**
         * 充值金额	不可
         */
        @SerializedName("CardPrice")
        private String cardPrice;

        public Params(String cardNo, String cardPrice) {
            this.cardNo = cardNo;
            this.cardPrice = cardPrice;
        }

        /**
         * 卡号	不可
         */
        public String getCardNo() {
            return cardNo;
        }

        /**
         * 充值金额	不可
         */
        public String getCardPrice() {
            return cardPrice;
        }


    }
}
