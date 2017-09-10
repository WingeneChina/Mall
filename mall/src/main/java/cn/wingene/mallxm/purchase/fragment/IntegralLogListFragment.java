package cn.wingene.mallxm.purchase.fragment;

import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import junze.java.net.IHttpCacheElement.ICaheReqCallBack;
import junze.java.net.IHttpElement.IReqCallBack;

import junze.android.ui.ItemViewHolder;

import cn.wingene.mall.R;
import cn.wingene.mallx.frame.fragment.BasePullListFragment;
import cn.wingene.mallxm.purchase.ask.AskIntegralLogList;
import cn.wingene.mallxm.purchase.ask.AskIntegralLogList.IntegralLog;
import cn.wingene.mallxm.purchase.ask.AskIntegralLogList.Response;
import cn.wingene.mallxm.purchase.holder.OrderEmptyViewHolder;


/**
 * Created by wangcq on 2017/8/13.
 * 商品列表页面
 */

public class IntegralLogListFragment extends BasePullListFragment {

    private RechargeScheme mOrderScheme = new RechargeScheme(this);
    boolean hadCreted;

    public static IntegralLogListFragment newInstance(int state) {
        IntegralLogListFragment productListFragment = new IntegralLogListFragment();
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
        getListViewHolder().setAdapter(getScheme().getAdapter());
        getListViewHolder().loadFirstPage();
        getListViewHolder().setOnItemClickListener(this);
        getListViewHolder().setOnItemLongClickListener(this);
    }

    @Override
    protected RechargeScheme getScheme() {
        return mOrderScheme;
    }

    @Override
    protected void askItem(int pageIndex) {
        askInBack(new AskIntegralLogList.Request(pageIndex).setCallBack(getScheme().getCallback(pageIndex)),false);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        OrderDetailActivity.major.startForOrderNo(getActivity(),getScheme().getItem(position).getNo());
    }

    private IReqCallBack<Response> getCallback(int pageIndex) {
        return getScheme().getCallback(pageIndex);
    }

    public static class RechargeScheme extends Scheme<Integer, IntegralLog> {

        public RechargeScheme(BasePullListFragment fragmetn) {
            super(fragmetn);
        }


        @Override
        public void onInit(BaseSchemeOption option) {
            peformatInit(null, new OrderItemHolder(getActivity()));
        }

        @Override
        public OrderItemHolder getItemViewHolder() {
            return (OrderItemHolder) super.getItemViewHolder();
        }

        public ICaheReqCallBack<Response> getCallback(final int pageIndex) {
            return new ICaheReqCallBack<Response>() {

                @Override
                public void updateUIWhenException(Exception e) {
                    schemeUpdateException(pageIndex,e);
                }

                @Override
                public void updateUI(Response rsp) {
                    schemeUpdateUI(pageIndex, rsp.getList());
                    if(pageIndex ==1 && getItemViewHolder().isEmpty()){
                        OrderEmptyViewHolder holder= getFragment().switchLayoutOther(OrderEmptyViewHolder.class);
                        holder.setTextAndHideBtn("暂无明细");
                    }
                }

                @Override
                public void updateUIByCache(Response rsp, Date saveTime) {
                    schemeUpdateUIByCache(pageIndex, rsp.getList(), saveTime);
                }

                @Override
                public void updateFinally() {
                }

            };
        }
    }

    private static class OrderItemHolder extends ItemViewHolder<IntegralLog> {

        private TextView tvDesc;
        private TextView tvTime;
        private TextView tvNumber;

        protected void initComponent() {
            tvDesc = (TextView) super.findViewById(R.id.tv_desc);
            tvTime = (TextView) super.findViewById(R.id.tv_time);
            tvNumber = (TextView) super.findViewById(R.id.tv_number);
        }


        public OrderItemHolder(Context mContext) {
            super(mContext, R.layout.listitem_recharge_item);
        }

        @Override
        public ItemViewHolder<IntegralLog> buildNewSelf(Context context) {
            return new OrderItemHolder(context);
        }

        @Override
        public void display(final int position, IntegralLog item) {
            tvDesc.setText(item.getChargeTypeDesp());
            tvTime.setText(item.getLogTime());

            if (item.getOperateKey() >= 0) {
                tvNumber.setText(String.format("+%.2f", item.getChargeAmount()));
                tvNumber.setTextColor(mContext.getResources().getColor(R.color.fontYellow));
            } else {
                tvNumber.setText(String.format("-%.2f", item.getChargeAmount()));
                tvNumber.setTextColor(mContext.getResources().getColor(R.color.gray));
            }
        }

    }


}
