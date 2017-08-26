package cn.wingene.mallxm.purchase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.adapter.YouLikeProduceAdapter;
import cn.wingene.mallxm.purchase.adapter.CartItemAdapter;
import cn.wingene.mallxm.purchase.ask.AskCartList;
import cn.wingene.mallxm.purchase.ask.AskCartList.CartItem;
import cn.wingene.mallxm.purchase.ask.AskCartList.Response;

import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;

/**
 * Created by Wingene on 2017/8/13.
 */

public class ShoppingCartFragment extends MyBaseFragment {
    private ItemHolder mItemHolder;

    private Tile tlBack;
    private ListView lvCartItem;
    private RecyclerView rvOtherBuy;
    private TextView tvOrder;

    protected void initComponent(View v){
        tlBack = (Tile) v.findViewById(R.id.tl_back);
        lvCartItem = (ListView) v.findViewById(R.id.lv_cart_item);
        rvOtherBuy = (RecyclerView) v.findViewById(R.id.rv_other_buy);
        tvOrder = (TextView) v.findViewById(R.id.tv_order);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_cart, container,false);
        initComponent(v);
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mItemHolder = new ItemHolder(getContext(), lvCartItem);
        initOtherBuys();
        tvOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startOrderAddActivity(getContext());
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        agent().ask(new AskCartList.Request() {
            @Override
            public void updateUI(Response rsp) {
                mItemHolder.clear();
                mItemHolder.addAll(rsp.data);
                mItemHolder.notifyDataSetChanged();
            }
        });
    }

    private void initOtherBuys() {
        rvOtherBuy.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvOtherBuy.setLayoutManager(gridLayoutManager);

//        YouLikeProduceAdapter youLikeProduceAdapter = new YouLikeProduceAdapter();
//        rvOtherBuy.setAdapter(youLikeProduceAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        rvOtherBuy.addItemDecoration(spaceItemDecoration);
    }

    private static class ItemHolder extends ItemViewHolder<CartItem> {
        private TextView tvTitle;
        private TextView tvSubTitle;

        @Override
        protected void initComponent() {
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
        }

        public ItemHolder(Context mContext, ListView lv) {
            super(mContext, lv, R.layout.listitem_shopping_cart_item);
        }

        @Override
        public ItemViewHolder<CartItem> buildNewSelf(Context context) {
            return new ItemHolder(context, null);
        }

        @Override
        public void display(int i, CartItem cartItem) {
            tvTitle.setText(cartItem.getProductName());
            tvSubTitle.setText(cartItem.getProductSpec());
        }

    }


}
