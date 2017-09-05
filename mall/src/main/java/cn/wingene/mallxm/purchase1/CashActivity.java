package cn.wingene.mallxm.purchase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.purchase1.data.CashCommitResultModel;
import cn.wingene.mallxm.purchase1.data.CashEnterModel;

public class CashActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {
    private final int ENTER_CASH_WHAT = 1;
    private final int COMMIT_CASH_WHAT = 2;

    private final int BANK_REQUEST_CODE = 1;

    private ImageView backIcon;
    private TextView titleV;
    private TextView canCashShowV;
    private EditText cashEditV;
    private TextView addNewAccountV;
    private LinearLayout addNewAccountGroupV;
    private TextView bankNameV;
    private TextView bankCardNumberV;
    private LinearLayout accountMsgGroupV;
    private TextView cashCommitV;
    private TextView bankCardUserNameV;

    private int mBankId;
    private int minMoney = 100;
    private int maxMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        initViews();
        initEvent();
        requestUserCanCash();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        canCashShowV = (TextView) findViewById(R.id.canCashShowV);
        cashEditV = (EditText) findViewById(R.id.cashEditV);
        addNewAccountV = (TextView) findViewById(R.id.addNewAccountV);
        addNewAccountGroupV = (LinearLayout) findViewById(R.id.addNewAccountGroupV);
        bankNameV = (TextView) findViewById(R.id.bankNameV);
        bankCardNumberV = (TextView) findViewById(R.id.bankCardNumberV);
        accountMsgGroupV = (LinearLayout) findViewById(R.id.accountMsgGroupV);
        cashCommitV = (TextView) findViewById(R.id.cashCommitV);
        bankCardUserNameV = (TextView) findViewById(R.id.bankCardUserNameV);

        cashEditV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() > 0) {
                        double money = Double.parseDouble(s.toString());
                        if (money < minMoney) {
                            cashEditV.setError("提现金额不足100");
                            return;
                        }
                        if (money > maxMoney) {
                            cashEditV.setError("超过最大金额");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        addNewAccountGroupV.setOnClickListener(this);
        cashCommitV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.addNewAccountGroupV:
                Intent intent = new Intent(this, AddNewCardCountActivity.class);
                startActivityForResult(intent, BANK_REQUEST_CODE);
                break;
            case R.id.cashCommitV:
                if (TextUtils.isEmpty(cashEditV.getText().toString())) {
                    cashEditV.setError("请输入提现金额");
                    return;
                }
                if (mBankId == 0) {
                    ToastUtil.show("请选择账户", this);
                    return;
                }
                commitCashInfo();

                break;
        }
    }

    /**
     * 进入提现请求
     */
    private void requestUserCanCash() {
        NoHttpRequest<CashEnterModel> noHttpRequest = new NoHttpRequest<>(CashEnterModel.class);
        noHttpRequest.request(this, HttpConstant.ENTER_CASH, null, ENTER_CASH_WHAT, this, false, null, true,
                false);
    }

    /**
     * 提交用户提现信息
     */
    private void commitCashInfo() {
        NoHttpRequest<CashCommitResultModel> noHttpRequest = new NoHttpRequest<>(CashCommitResultModel.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("AmountPrice", cashEditV.getText().toString());
        hashMap.put("BankCardId", mBankId);
        noHttpRequest.request(this, HttpConstant.COMMIT_CASH, hashMap, COMMIT_CASH_WHAT, this, false, null,
                true,
                false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case ENTER_CASH_WHAT:
                    GsonUtil<CashEnterModel> gsonUtil = new GsonUtil<>(CashEnterModel.class);
                    CashEnterModel cashEnterModel = gsonUtil.fromJson(response.get());
                    if (cashEnterModel.getErr() == 0 && cashEnterModel.getData() != null) {
                        maxMoney = cashEnterModel.getData().getAmount();
                        canCashShowV.setText(String.valueOf(maxMoney));

                        if (cashEnterModel.getData().getBankBack() != null) {
                            accountMsgGroupV.setVisibility(View.GONE);
                            bankNameV.setText(cashEnterModel.getData().getBankBack().getOpenBank());
                            bankCardNumberV.setText(cashEnterModel.getData().getBankBack().getBankCardNo());
                            bankCardUserNameV.setText(cashEnterModel.getData().getBankBack().getBankAccount());

                            mBankId = cashEnterModel.getData().getBankBack().getId();
                        } else {
                            accountMsgGroupV.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtil.show(cashEnterModel.getMsg(), this);
                    }

                    break;
                case COMMIT_CASH_WHAT:
                    GsonUtil<CashCommitResultModel> gsonUtil1 = new GsonUtil<>(CashCommitResultModel.class);
                    CashCommitResultModel cashCommitResultModel = gsonUtil1.fromJson(response.get());
                    if (cashCommitResultModel.getErr() == 0 && cashCommitResultModel.getData() != null) {

                    } else {
                        ToastUtil.show(cashCommitResultModel.getMsg(), this);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}