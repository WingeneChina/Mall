package cn.wingene.mallxm.purchase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.YouLikeProduceAdapter;
import junze.widget.Tile;

/**
 * Created by Wingene on 2017/8/13.
 */

public class ShoppingCartFragment extends MyBaseFragment {
    private Tile tlBack;
    private RecyclerView rvCartItem;
    private RecyclerView rvOtherBuy;

    protected void initComponent(View v){
        tlBack = (Tile) v.findViewById(R.id.tl_back);
        rvCartItem = (RecyclerView) v.findViewById(R.id.rv_cart_item);
        rvOtherBuy = (RecyclerView) v.findViewById(R.id.rv_other_buy);
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
        initCartItems();
        initOtherBuys();
        return v;
    }

    private void initCartItems() {
        rvCartItem.setNestedScrollingEnabled(false);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCartItem.setLayoutManager(lm);

        CartItemAdapter cia = new CartItemAdapter();
        rvCartItem.setAdapter(cia);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        rvCartItem.addItemDecoration(spaceItemDecoration);
    }

    private void initOtherBuys() {
        rvOtherBuy.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvOtherBuy.setLayoutManager(gridLayoutManager);

        YouLikeProduceAdapter youLikeProduceAdapter = new YouLikeProduceAdapter();
        rvOtherBuy.setAdapter(youLikeProduceAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        rvOtherBuy.addItemDecoration(spaceItemDecoration);
    }


}
