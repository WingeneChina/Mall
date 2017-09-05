package cn.wingene.mallxm.purchase.fragment;

import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import junze.java.net.IHttpCacheElement.ICaheReqCallBack;
import junze.java.net.IHttpElement.IReqCallBack;

import junze.widget.HightMatchListView;

import junze.android.ui.ItemViewHolder;
import junze.android.ui.ItemViewHolder.OnItemViewClickListener;

import cn.wingene.mall.R;
import cn.wingene.mallx.frame.fragment.BasePullListFragment;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.LogisticsActivity;
import cn.wingene.mallxm.purchase.OrderAddActivity.ProductItemHolder;
import cn.wingene.mallxm.purchase.OrderDetailActivity;
import cn.wingene.mallxm.purchase.ask.AskLogisticsDetail;
import cn.wingene.mallxm.purchase.ask.AskOrderCancel;
import cn.wingene.mallxm.purchase.ask.AskOrderConfirm;
import cn.wingene.mallxm.purchase.ask.AskOrderList;
import cn.wingene.mallxm.purchase.ask.AskOrderList.OrderItem;
import cn.wingene.mallxm.purchase.ask.AskOrderList.Response;
import cn.wingene.mallxm.purchase.ask.AskOrderPayNow;
import cn.wingene.mallxm.purchase.holder.EmptyOrderViewHolder;
import cn.wingene.mallxm.purchase.tool.PayHelper;
import cn.wingene.mallxm.purchase.tool.PayHelper.OnOrderBuild;

/**
 * Created by wangcq on 2017/8/13.
 * 商品列表页面
 */

public class OrderListFragment extends BasePullListFragment {

    private OrderScheme mOrderScheme = new OrderScheme(this);
    boolean hadCreted;
    PayHelper mPayHelper;

    public static OrderListFragment newInstance(int state) {
        OrderListFragment productListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hadCreted = true;
        mPayHelper = new PayHelper(agent());
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order_pull_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseSchemeOption option = new BaseSchemeOption();
        option.bundle = getArguments();
        getScheme().onInit(option);
        EmptyOrderViewHolder eh = new EmptyOrderViewHolder(getActivity());
        eh.getView().setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        eh.getView().setVisibility(View.GONE);
        ((ViewGroup)getListView().getParent()).addView(eh.getView());
        getListView().setEmptyView(eh.getView());
        getListViewHolder().setAdapter(getScheme().getAdapter());
        getListViewHolder().loadFirstPage();
        getListViewHolder().setOnItemClickListener(this);
        getListViewHolder().setOnItemLongClickListener(this);
        ItemViewHolder.OnItemViewClickListener listener = new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, String s, ItemViewHolder<?> itemViewHolder, int i) {
                final OrderItem item = getScheme().getItem(i);
                if (OrderItemHolder.CANCEL.equals(s)) {
                    agent().ask((new AskOrderCancel.Request(item.getNo()) {
                        @Override
                        public void updateUI(MyBaseResponse rsp) {
                            item.setState(6);
                            getScheme().getItemViewHolder().notifyDataSetChanged();
                        }
                    }));
                } else if (OrderItemHolder.PAY_NOW.equals(s)) {
                    pay(item);
                } else if (OrderItemHolder.SEARCH.equals(s)) {
                    agent().ask(new AskLogisticsDetail.Request(item.getNo()){
                        @Override
                        public void updateUI(AskLogisticsDetail.Response rsp) {
                            LogisticsActivity.major.startForBean(getActivity(),rsp.data);
                        }
                    });
                } else if (OrderItemHolder.OK.equals(s)) {
                    agent().ask((new AskOrderConfirm.Request(item.getNo()) {
                        @Override
                        public void updateUI(MyBaseResponse rsp) {
                            item.setState(4);
                            getScheme().getItemViewHolder().notifyDataSetChanged();
                        }
                    }));
                }
            }
        };
        getScheme().getItemViewHolder().setOnItemViewClick(OrderItemHolder.CANCEL, listener);
        getScheme().getItemViewHolder().setOnItemViewClick(OrderItemHolder.PAY_NOW, listener);
        getScheme().getItemViewHolder().setOnItemViewClick(OrderItemHolder.SEARCH, listener);
        getScheme().getItemViewHolder().setOnItemViewClick(OrderItemHolder.OK, listener);
    }



    public void pay(final OrderItem item) {
        mPayHelper.setOnOrderBuild(new OnOrderBuild() {
            @Override
            public void onOrderBuild(final PayHelper helper, final boolean isAlipay, final double amount, final int integral) {
                agent().ask(new AskOrderPayNow.Request(item.getNo()) {
                    @Override
                    public void updateUI(AskOrderPayNow.Response rsp) {
                       helper.askPay(rsp.data,isAlipay,amount,integral);
                    }
                });
            }
        });
        mPayHelper.showBottomDialog(item.getPayPrice(), 0, 0);
    }


    @Override
    protected OrderScheme getScheme() {
        return mOrderScheme;
    }

    @Override
    protected void askItem(int pageIndex) {
        askInBack(new AskOrderList.Request(getScheme().getType(), pageIndex).setCallBack(getScheme().getCallback
                (pageIndex)));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderDetailActivity.major.startForOrderNo(getActivity(),getScheme().getItem(position).getNo());
    }

    private IReqCallBack<Response> getCallback(int pageIndex) {
        return getScheme().getCallback(pageIndex);
    }

    public static class OrderScheme extends Scheme<Integer, OrderItem> {
        Integer mState;


        public OrderScheme(BasePullListFragment fragmetn) {
            super(fragmetn);
        }


        @Override
        public void onInit(BaseSchemeOption option) {
            mState = option.bundle.getInt("state");
            peformatInit(mState, new OrderItemHolder(getActivity()));
        }

        @Override
        public OrderItemHolder getItemViewHolder() {
            return (OrderItemHolder) super.getItemViewHolder();
        }

        public ICaheReqCallBack<Response> getCallback(final int pageIndex) {
            return new ICaheReqCallBack<AskOrderList.Response>() {

                @Override
                public void updateUIWhenException(Exception e) {

                }

                @Override
                public void updateUI(Response rsp) {
                    schemeUpdateUI(pageIndex, rsp.data.getList());
                }

                @Override
                public void updateUIByCache(Response rsp, Date saveTime) {
                    schemeUpdateUIByCache(pageIndex, rsp.data.getList(), saveTime);
                }

                @Override
                public void updateFinally() {
                }

            };
        }
    }

    private static class OrderItemHolder extends ItemViewHolder<OrderItem> {
        ProductItemHolder mItemHolder;

        private HightMatchListView hmlvProduct;
        private TextView tvState;
        private TextView tvNumber;
        private TextView tvTotal;
        private TextView tvNo;
        private TextView tvLeft;
        private TextView tvRight;

        @Override
        protected void initComponent() {
            hmlvProduct = (HightMatchListView) super.findViewById(R.id.hmlv_product);
            tvState = (TextView) super.findViewById(R.id.tv_state);
            tvNumber = (TextView) super.findViewById(R.id.et_number);
            tvTotal = (TextView) super.findViewById(R.id.tv_total);
            tvNo = (TextView) super.findViewById(R.id.tv_no);
            tvLeft = (TextView) super.findViewById(R.id.tv_left);
            tvRight = (TextView) super.findViewById(R.id.tv_right);
        }

        public OrderItemHolder(Context mContext) {
            super(mContext, R.layout.listitem_order_item);
        }

        @Override
        public ItemViewHolder<OrderItem> buildNewSelf(Context context) {
            return new OrderItemHolder(context);
        }

        public static final String PAY_NOW = "PAY_NOW";
        public static final String CANCEL = "CANCEL";
        public static final String SEARCH = "SEARCH";
        public static final String OK = "OK";

        @Override
        public void display(final int position, OrderItem orderItem) {
            String str1 = null;
            String str2 = null;
            String key1 = null;
            String key2 = null;

            switch (orderItem.getState()) {
            case 0:
                tvState.setText("待付款");
                str1 = "取消订单";
                str2 = "立即付款";
                key1 = CANCEL;
                key2 = PAY_NOW;
                break;
            case 1:
                tvState.setText("待发货");
                break;
            case 2:
                tvState.setText("已发货");
                str1 = "查询包裹";
                str2 = "确认收货";
                key1 = SEARCH;
                key2 = OK;
                break;
            case 3:
                tvState.setText("待确认");
                str1 = "查询包裹";
                str2 = "确认收货";
                key1 = SEARCH;
                key2 = OK;
                break;
            case 4:
                tvState.setText("交易完成");
                break;
            case 5:
                tvState.setText("交易完成已评价");
                break;
            case 6:
                tvState.setText("买家关闭交易");
                break;
            case 7:
                tvState.setText("平台关闭交易");
                break;
            case 8:
                tvState.setText("退款成功关闭");
                break;
            default:
                break;
            }
            tvLeft.setVisibility(str1 != null ? View.VISIBLE : View.INVISIBLE);
            tvLeft.setText(str1);
            tvLeft.setOnClickListener(buildClickForItem(key1, position));
            tvRight.setVisibility(str2 != null ? View.VISIBLE : View.INVISIBLE);
            tvRight.setText(str2);
            tvRight.setOnClickListener(buildClickForItem(key2, position));
            tvNo.setText(String.format("订单编号:%s", orderItem.getNo()));
            if (mItemHolder == null) {
                mItemHolder = ProductItemHolder.createForOrderList(getContext(), hmlvProduct);
            }
            hmlvProduct.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                    OrderDetailActivity.major.startForOrderNo(getActivity(),getItem(position).getNo());
                }
            });
            mItemHolder.clear();
            mItemHolder.addAll(orderItem.getOrderProductList());
            mItemHolder.notifyDataSetChanged();
            tvNumber.setText(String.format("共%s件",orderItem.getSumNumber()));
            tvTotal.setText(String.format("￥%.2f",orderItem.getSumPrice()));
        }

    }


}
