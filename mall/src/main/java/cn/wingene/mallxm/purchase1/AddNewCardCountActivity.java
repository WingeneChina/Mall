package cn.wingene.mallxm.purchase1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.purchase1.data.BankListModel;


public class AddNewCardCountActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private final int REUEST_ADD_CARD_WHAT = 1;
    private final int COMMIT_ADD_CARD_WHAT = 2;

    private ImageView backIcon;
    private TextView titleV;
    private Spinner bankSpinnerV;
    private EditText bankBranchV;
    private EditText bankCardNumberV;
    private EditText bankCardUserNameV;
    private TextView saveCardInfo;

    private BankArrayAdapter mBankArrayAdapter;
    private List<BankListModel.DataBean.BankTypeListBean> mBankTypeListBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card_count);
        initViews();
        initEvent();
        requestData();

    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        bankSpinnerV = (Spinner) findViewById(R.id.bankSpinnerV);
        bankBranchV = (EditText) findViewById(R.id.bankBranchV);
        bankCardNumberV = (EditText) findViewById(R.id.bankCardNumberV);
        bankCardUserNameV = (EditText) findViewById(R.id.bankCardUserNameV);
        saveCardInfo = (TextView) findViewById(R.id.saveCardInfo);

        initBankSpinnerList();
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        saveCardInfo.setOnClickListener(this);
    }

    private void initBankSpinnerList() {
        mBankArrayAdapter = new BankArrayAdapter(this, mBankTypeListBeen);
        bankSpinnerV.setAdapter(mBankArrayAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();

                break;
            case R.id.saveCardInfo://保存卡信息
                String branchBankName = bankBranchV.getText().toString();
                if (TextUtils.isEmpty(branchBankName)) {
                    bankBranchV.setError("请输入开户支行");
                    return;
                }

                String bankCardNumber = bankCardNumberV.getText().toString();
                if (TextUtils.isEmpty(bankCardNumber)) {
                    bankCardNumberV.setError("请输入银行卡号");
                    return;
                }

                String bankUserName = bankCardUserNameV.getText().toString();
                if (TextUtils.isEmpty(bankUserName)) {
                    bankCardUserNameV.setError("请输入用户名");
                    return;
                }

                commitAddCardInfo();
                break;
        }
    }

    /**
     * 请求银行卡参数
     */
    private void requestData() {
        NoHttpRequest<BankListModel> noHttpRequest = new NoHttpRequest<>(BankListModel.class);
        noHttpRequest.request(this, HttpConstant.ADD_CARD, null, REUEST_ADD_CARD_WHAT, this, false, null, true, false);
    }

    /**
     * 提交银行卡号信息
     */
    private void commitAddCardInfo() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Id", getIntent().getIntExtra("id", 0));
        hashMap.put("IsDefault", 0);
        hashMap.put("BankType", mBankTypeListBeen.get(bankSpinnerV.getSelectedItemPosition()));
        hashMap.put("BankAccount", bankCardUserNameV.getText().toString());
        hashMap.put("BankCardNo", bankCardNumberV.getText().toString());
        hashMap.put("OpenBank", bankBranchV.getText().toString());
        noHttpRequest.request(this, HttpConstant.COMMIT_ADD_CARD, hashMap, COMMIT_ADD_CARD_WHAT, this, false, null,
                true, false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case REUEST_ADD_CARD_WHAT:
                    GsonUtil<BankListModel> gsonUtil = new GsonUtil<>(BankListModel.class);
                    BankListModel bankListModel = gsonUtil.fromJson(response.get());
                    bankBranchV.setText(bankListModel.getData().getBankBack().getOpenBank());
                    bankCardNumberV.setText(bankListModel.getData().getBankBack().getBankCardNo());
                    bankCardUserNameV.setText(bankListModel.getData().getBankBack().getBankAccount());

                    mBankTypeListBeen.clear();
                    mBankTypeListBeen.addAll(bankListModel.getData().getBankTypeList());
                    mBankArrayAdapter.notifyDataSetChanged();

                    for (int i = 0; i < bankListModel.getData()
                            .getBankTypeList().size(); i++) {
                        if (bankListModel.getData().getBankBack().getId().equals(bankListModel.getData()
                                .getBankTypeList
                                        ().get(i).getId())) {

                            bankSpinnerV.setSelection(i);
                            break;
                        }
                    }
                    break;
                case COMMIT_ADD_CARD_WHAT:
                    GsonUtil<BaseResponse> gsonUtil1 = new GsonUtil<>(BaseResponse.class);
                    BaseResponse baseResponse = gsonUtil1.fromJson(response.get());
                    if (baseResponse.err == 0) {
                        setResult(CashActivity.BANK_REQUEST_CODE, null);
                        finish();
                    } else {
                        ToastUtil.show(baseResponse.msg, this);
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

    class BankArrayAdapter extends ArrayAdapter<BankListModel.DataBean.BankTypeListBean> {

        private Context mContext;
        private List<BankListModel.DataBean.BankTypeListBean> mBankTypeListBeen;

        public BankArrayAdapter(Context context, List<BankListModel.DataBean.BankTypeListBean> bankTypeListBeen) {
            super(context, android.R.layout.simple_spinner_item, bankTypeListBeen);
            mContext = context;
            mBankTypeListBeen = bankTypeListBeen;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            //修改Spinner展开后的字体颜色
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }

            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(mBankTypeListBeen.get(position).getName());
//            tv.setTextSize(22f);
//            tv.setTextColor(Color.RED);

            return convertView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 修改Spinner选择后结果的字体颜色
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }

            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(mBankTypeListBeen.get(position).getName());
            tv.setTextSize(18f);
            tv.setTextColor(Color.BLUE);
            return convertView;
        }

    }

}
