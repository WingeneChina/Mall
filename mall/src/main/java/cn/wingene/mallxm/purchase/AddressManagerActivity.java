package cn.wingene.mallxm.purchase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AddressManagerActivity extends MyBaseActivity {
    private Tile tlBack;
    private ListView lvAddressItem;
    private TextView tvAddAddress;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        lvAddressItem = (ListView) super.findViewById(R.id.lv_address_item);
        tvAddAddress = (TextView) super.findViewById(R.id.tv_add_address);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initLvAddressItem();

        tvAddAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startAddressAddActivity(getActivity());
            }
        });
    }

    private void initLvAddressItem() {
        ItemHolder holder = new ItemHolder(this,lvAddressItem);
        holder.add(new Object());
        holder.add(new Object());
        holder.notifyDataSetChanged();
    }


    private static class ItemHolder extends ItemViewHolder {

        @Override
        protected void initComponent() {
        }


        public ItemHolder(Context mContext,ListView lv) {
            super(mContext,lv, R.layout.listitem_address_item);
        }

        @Override
        public ItemViewHolder buildNewSelf(Context context) {
            return new ItemHolder(context,null);
        }

        @Override
        public void display(int i, Object o) {

        }

    }
}
