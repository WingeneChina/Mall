package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

import junze.widget.HightMatchListView;
import junze.widget.Tile;

import junze.androidxf.core.Agent;

import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.D;
import cn.wingene.mallxm.purchase.OrderAddActivity.ProductItemHolder;
import cn.wingene.mallxm.purchase.ask.AskLogisticsDetail;
import cn.wingene.mallxm.purchase.ask.AskOrderCancel;
import cn.wingene.mallxm.purchase.ask.AskOrderConfirm;
import cn.wingene.mallxm.purchase.ask.AskOrderDetail;
import cn.wingene.mallxm.purchase.ask.AskOrderDetail.OrderDetail;
import cn.wingene.mallxm.purchase.ask.AskOrderDetail.OrderPay;
import cn.wingene.mallxm.purchase.ask.AskOrderDetail.Response;
import cn.wingene.mallxm.purchase.ask.AskOrderPayNow;
import cn.wingene.mallxm.purchase.bean.Address4;
import cn.wingene.mallxm.purchase.tool.PayHelper;
import cn.wingene.mallxm.purchase.tool.ShowTool;

/**
 * Created by Wingene on 2017/9/3.
 */

public class OrderDetailActivity extends MyBaseActivity {
    public static Major major = new Major(OrderDetailActivity.class);
    public static final String PAY_NOW = "PAY_NOW";
    public static final String CANCEL = "CANCEL";
    public static final String SEARCH = "SEARCH";
    public static final String OK = "OK";
    PayHelper mPayHelper;
    ProductItemHolder mItemHolder;

    private Tile tlBack;
    private Tile tlService;
    private RelativeLayout rlytBanner;
    private TextView tvState;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPhone;
    private HightMatchListView hmlvProduct;
    private TextView tvNo;
    private TextView tvNumber;
    private TextView tvSumPrice;
    private TextView tvDeliveryFee;
    private TextView tvPayPrice;
    private TextView tvAmount;
    private TextView tvIntegral;
    private TextView tvRealTotal;
    private TextView tvLeft;
    private TextView tvRight;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tlService = (Tile) super.findViewById(R.id.tl_service);
        rlytBanner = (RelativeLayout) super.findViewById(R.id.rlyt_banner);
        tvState = (TextView) super.findViewById(R.id.tv_state);
        tvName = (TextView) super.findViewById(R.id.tv_name);
        tvAddress = (TextView) super.findViewById(R.id.tv_address);
        tvPhone = (TextView) super.findViewById(R.id.tv_phone);
        hmlvProduct = (HightMatchListView) super.findViewById(R.id.hmlv_product);
        tvNo = (TextView) super.findViewById(R.id.tv_no);
        tvNumber = (TextView) super.findViewById(R.id.tv_number);
        tvSumPrice = (TextView) super.findViewById(R.id.tv_sum_price);
        tvDeliveryFee = (TextView) super.findViewById(R.id.tv_delivery_fee);
        tvPayPrice = (TextView) super.findViewById(R.id.tv_pay_price);
        tvAmount = (TextView) super.findViewById(R.id.tv_amount);
        tvIntegral = (TextView) super.findViewById(R.id.tv_integral);
        tvRealTotal = (TextView) super.findViewById(R.id.tv_real_total);
        tvLeft = (TextView) super.findViewById(R.id.tv_left);
        tvRight = (TextView) super.findViewById(R.id.tv_right);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initComponent();
        mPayHelper = new PayHelper(getAgent());
        mItemHolder = ProductItemHolder.createForOrderList(getContext(), hmlvProduct);
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tlService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getAgent().tryCallPhone("客服", D.CUSTOMER_PHONE);
            }
        });
        String orderNo = major.parseParams(this);
        askOrderDetail(orderNo);

    }

    private void askOrderDetail(final String orderNo) {
        getAgent().ask("请求中...", false, new AskOrderDetail.Request(orderNo) {
            @Override
            public void updateUI(Response rsp) {
                updateUIForActivity(rsp.data);
            }

            @Override
            protected void updateUIWhenException(Exception e) {
                super.updateUIWhenException(e);
                showConfirmDialog("提示", "请求失败", "重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askOrderDetail(orderNo);
                    }
                }, "离开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    private void updateUIForActivity(OrderDetail bean) {
        int ridBanner = R.drawable.bg_wait_deliver;
        String str1 = null;
        String str2 = null;
        String key1 = null;
        String key2 = null;

        switch (bean.getState()) {
        case 0:
            tvState.setText("待付款");
            ridBanner = R.drawable.bg_wait_pay;
            str1 = "取消订单";
            str2 = "立即付款";
            key1 = CANCEL;
            key2 = PAY_NOW;
            break;
        case 1:
            ridBanner = R.drawable.bg_wait_deliver;
            tvState.setText("待发货");
            break;
        case 2:
            ridBanner = R.drawable.bg_had_deliver;
            tvState.setText("已发货");
            str1 = "查询包裹";
            str2 = "确认收货";
            key1 = SEARCH;
            key2 = OK;
            break;
        case 3:
            ridBanner = R.drawable.bg_had_deliver;
            tvState.setText("待确认");
            str1 = "查询包裹";
            str2 = "确认收货";
            key1 = SEARCH;
            key2 = OK;
            break;
        case 4:
            ridBanner = R.drawable.bg_done;
            tvState.setText("交易完成");
            break;
        case 5:
            ridBanner = R.drawable.bg_done;
            tvState.setText("交易完成已评价");
            break;
        case 6:
            ridBanner = R.drawable.bg_close;
            tvState.setText("买家关闭交易");
            break;
        case 7:
            ridBanner = R.drawable.bg_close;
            tvState.setText("平台关闭交易");
            break;
        case 8:
            ridBanner = R.drawable.bg_close;
            tvState.setText("退款成功关闭");
            break;
        default:
            break;
        }

        rlytBanner.setBackgroundResource(ridBanner);

        setAddress(bean.getAddress());

        mItemHolder.set__isJiaPei(bean.isJiaPei());
        mItemHolder.clear();
        mItemHolder.addAll(bean.getOrderProductList());
        mItemHolder.notifyDataSetChanged();

        tvNo.setText(String.format("订单编号:%s", bean.getNo()));
        tvNumber.setText(String.format("共%s件", bean.getSumNumber().intValue()));
        ShowTool.showPrice(tvSumPrice,"合计:%s",bean.getSumPrice(),bean.isJiaPei());
        ShowTool.showPrice(tvPayPrice,"%s",bean.getPayPrice(),bean.isJiaPei());
        ShowTool.showDeliveryFee(tvDeliveryFee,bean.getDeliveryFee());

        setOrderPay(bean.getOrderPay());



        tvLeft.setVisibility(str1 != null ? View.VISIBLE : View.INVISIBLE);
        tvLeft.setText(str1);
        tvLeft.setOnClickListener(makeClickListener(key1, bean));
        tvRight.setVisibility(str2 != null ? View.VISIBLE : View.INVISIBLE);
        tvRight.setOnClickListener(makeClickListener(key2, bean));
        tvRight.setText(str2);
    }

    public void setAddress(Address4 a) {
        tvName.setText(a != null ? a.getRegion() : null);
        tvAddress.setText(a != null ? a.getAddress() : null);
        tvPhone.setText(a != null ? a.getMobile() : null);
    }

    public void setOrderPay(OrderPay orderPay) {
        tvAmount.setText(String.format("￥%.2f", orderPay != null ? orderPay.getAmount() : 0.0f));
        tvIntegral.setText(String.format("￥%s", orderPay != null ? orderPay.getIntegral() : 0));
        tvRealTotal.setText(String.format("￥%.2f", orderPay != null ? orderPay.getOrderPay() : 0.0f));
    }

    public OnClickListener makeClickListener(final String s, final OrderDetail item) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CANCEL.equals(s)) {
                    getAgent().ask((new AskOrderCancel.Request(item.getNo()) {
                        @Override
                        public void updateUI(MyBaseResponse rsp) {
                            item.setState(6);
                            updateUIForActivity(item);
                        }
                    }));
                } else if (PAY_NOW.equals(s)) {
                    pay(item);
                } else if (SEARCH.equals(s)) {
                    getAgent().ask(new AskLogisticsDetail.Request(item.getNo()) {
                        @Override
                        public void updateUI(AskLogisticsDetail.Response rsp) {
                            LogisticsActivity.major.startForBean(getActivity(), rsp.data);
                        }
                    });
                } else if (OK.equals(s)) {
                    getAgent().ask((new AskOrderConfirm.Request(item.getNo()) {
                        @Override
                        public void updateUI(MyBaseResponse rsp) {
                            item.setState(4);
                            updateUIForActivity(item);
                        }
                    }));
                }
            }
        };
    }

    public void pay(final OrderDetail item) {
        getAgent().ask(new AskOrderPayNow.Request(item.getNo()) {
            @Override
            public void updateUI(AskOrderPayNow.Response rsp) {
                OrderAddActivity.major.startActivity(getActivity(), rsp.data);
                finish();
            }
        });
    }


    public static class Major extends Agent.Major {
        public Major(Class<? extends OrderDetailActivity> clazz) {
            super(clazz);
        }

        public void startForOrderNo(Context src, String orderNo) {
            buildParams(src, orderNo).startActivity();
        }

        public String parseParams(Activity src) {
            return parseParam(src, String.class);
        }
    }
}
