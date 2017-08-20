package cn.wingene.mallxm.purchase;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.adapter.OrderItemAdapter;
import junze.widget.Tile;

/**
 * Created by Wingene on 2017/8/14.
 */

public class OrderAddActivity extends MyBaseActivity {
    private Tile tlBack;
    private LinearLayout llytAddress;
    private RecyclerView rvOrderItem;
    private TextView tvOrder;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        llytAddress = (LinearLayout) super.findViewById(R.id.llyt_address);
        rvOrderItem = (RecyclerView) super.findViewById(R.id.rv_order_item);
        tvOrder = (TextView) super.findViewById(R.id.tv_order);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_add);
        initComponent();
        initOrderItem();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        llytAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startAddressManagerActivity(getActivity());
            }
        });
    }

    private void initOrderItem() {
        rvOrderItem.setNestedScrollingEnabled(false);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        OrderItemAdapter adapter = new OrderItemAdapter();
        SpaceItemDecoration sd = new SpaceItemDecoration(10,10,10,10);
        rvOrderItem.setLayoutManager(llm);
        rvOrderItem.setAdapter(adapter);
        rvOrderItem.addItemDecoration(sd);

    }
}
