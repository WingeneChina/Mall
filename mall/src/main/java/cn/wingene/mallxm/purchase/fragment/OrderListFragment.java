package cn.wingene.mallxm.purchase.fragment;

import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import junze.java.net.IHttpCacheElement.ICaheReqCallBack;
import junze.java.net.IHttpElement.IReqCallBack;

import junze.android.ui.ItemViewHolder;

import cn.wingene.mall.R;
import cn.wingene.mallx.frame.fragment.BasePullListFragment;
import cn.wingene.mallxm.purchase.ask.AskOrderList;
import cn.wingene.mallxm.purchase.ask.AskOrderList.OrderItem;
import cn.wingene.mallxm.purchase.ask.AskOrderList.Response;
import cn.wingene.mallxm.purchase.holder.EmptyOrderViewHolder;

/**
 * Created by wangcq on 2017/8/13.
 * 商品列表页面
 */

public class OrderListFragment extends BasePullListFragment {

    private OrderScheme mOrderScheme = new OrderScheme(this);
    boolean hadCreted;

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
        private TextView tvNo;
        private TextView tvAmount;
        private TextView tvIntegral;
        private TextView tvNumber;
        private TextView tvTotal;

        @Override
        protected void initComponent() {
            tvNo = (TextView) super.findViewById(R.id.tv_no);
            tvAmount = (TextView) super.findViewById(R.id.tv_amount);
            tvIntegral = (TextView) super.findViewById(R.id.tv_integral);
            tvNumber = (TextView) super.findViewById(R.id.tv_number);
            tvTotal = (TextView) super.findViewById(R.id.tv_total);
        }


        public OrderItemHolder(Context mContext) {
            super(mContext, R.layout.listitem_order_item);
        }

        @Override
        public ItemViewHolder<OrderItem> buildNewSelf(Context context) {
            return new OrderItemHolder(context);
        }

        @Override
        public void display(int i, OrderItem orderItem) {
            tvNo.setText(String.format("订单编号:%s", orderItem.getNo()));
//            tvIntegral.setText(String.format("应币:%s",orderItem.get));
            tvNumber.setText(String.format("共%s件",orderItem.getSumNumber()));
            tvTotal.setText(String.format("￥%.2f",orderItem.getSumPrice()));
        }

    }


}
