package cn.wingene.mallxm.purchase1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;

import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.purchase1.data.CashCommitResultModel;

/**
 * 提现申请成功
 */
public class CashSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backIcon;
    private TextView titleV;
    private ImageView cashStateImgV;
    private TextView cashStateDesV;
    private TextView cardInfoV;
    private TextView cashV;
    private TextView cashCommitV;
    private LinearLayout cashSuccessGroupV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_success);
        initViews();
        initEvent();
        showCashResult();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        cashStateImgV = (ImageView) findViewById(R.id.cashStateImgV);
        cashStateDesV = (TextView) findViewById(R.id.cashStateDesV);
        cardInfoV = (TextView) findViewById(R.id.cardInfoV);
        cashV = (TextView) findViewById(R.id.cashV);
        cashCommitV = (TextView) findViewById(R.id.cashCompletedV);
        cashSuccessGroupV = (LinearLayout) findViewById(R.id.cashSuccessGroupV);
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        cashCommitV.setOnClickListener(this);
    }

    private void showCashResult() {
        try {
            GsonUtil<BaseResponse> baseResponseGsonUtil = new GsonUtil<>(BaseResponse.class);
            BaseResponse baseResponse = baseResponseGsonUtil.fromJson(getIntent().getStringExtra("cashResult"));
            if (baseResponse.err == 0) {
                GsonUtil<CashCommitResultModel> gsonUtil1 = new GsonUtil<>(CashCommitResultModel.class);
                CashCommitResultModel cashCommitResultModel = gsonUtil1.fromJson(getIntent().getStringExtra
                        ("cashResult"));

                if (cashCommitResultModel.getErr() == 0 && cashCommitResultModel.getData() != null) {
                    cashSuccessGroupV.setVisibility(View.VISIBLE);
                    cashStateImgV.setImageResource(R.drawable.cash_success);
                    cashStateDesV.setText(cashCommitResultModel.getMsg());
                    String lastNumber = cashCommitResultModel.getData().getBankBack().getBankCardNo().substring
                            (cashCommitResultModel.getData().getBankBack().getBankCardNo().length() - 3,
                                    cashCommitResultModel.getData().getBankBack().getBankCardNo().length());

                    cardInfoV.setText(cashCommitResultModel.getData().getBankBack().getBankTypeDesp() + "    尾号  " +
                            lastNumber);
                    cashV.setText(String.valueOf(cashCommitResultModel.getData().getAmountPrice()));

                } else {
                    cashStateImgV.setImageResource(R.drawable.cash_fail);
                    cashStateDesV.setText(cashCommitResultModel.getMsg());

                }
            } else {
                cashSuccessGroupV.setVisibility(View.GONE);
                cashStateImgV.setImageResource(R.drawable.cash_fail);
                cashStateDesV.setText(baseResponse.msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.cashCompletedV:
                finish();
                break;
        }
    }
}
