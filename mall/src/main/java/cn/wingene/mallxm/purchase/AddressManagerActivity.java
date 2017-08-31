package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.purchase.ask.AskAddressList;
import cn.wingene.mallxm.purchase.ask.AskAddressList.AddressItem;
import cn.wingene.mallxm.purchase.ask.AskAddressList.Response;

import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;
import junze.androidxf.core.Agent;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AddressManagerActivity extends MyBaseActivity {
    public static Major major = new Major(AddressManagerActivity.class);
    public static final int RC_ADDRESS_ADD = 1000;
    ItemHolder mItemHolder;

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
                AddressAddActivity.major.startForResult(getActivity(), RC_ADDRESS_ADD);
            }
        });
        askAddressList();
    }

    private void initLvAddressItem() {
        mItemHolder = new ItemHolder(this, lvAddressItem);
        lvAddressItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getMajor() == Major.MAJOR_CHOISE) {
                    setResult(Activity.RESULT_OK, major.buildResult(mItemHolder.getItem(position)));
                    finish();
                } else {
                    AddressAddActivity.major.startForResult(getActivity(),mItemHolder.getItem(position),RC_ADDRESS_ADD);
                }
            }
        });
        mItemHolder.notifyDataSetChanged();
    }

    public void askAddressList(){
        ask(new AskAddressList.Request(){
            @Override
            public void updateUI(Response rsp) {
                mItemHolder.clear();
                mItemHolder.addAll(rsp.getList());
                mItemHolder.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == RC_ADDRESS_ADD) {
            if(getMajor()==Major.MAJOR_CHOISE){
                AddressItem address = AddressAddActivity.major.parseResult(data);
                setResult(Activity.RESULT_OK, major.buildResult(address));
                finish();
            }else{
                askAddressList();
            }

        }
    }


    private static class ItemHolder extends ItemViewHolder<AddressItem> {
        private TextView tvName;
        private TextView tvIsDefault;
        private TextView tvPhone;
        private TextView tvAddress;
        private ImageView ivEdit;

        @Override
        protected void initComponent() {
            tvName = (TextView) super.findViewById(R.id.tv_name);
            tvIsDefault = (TextView) super.findViewById(R.id.tv_is_default);
            tvPhone = (TextView) super.findViewById(R.id.tv_phone);
            tvAddress = (TextView) super.findViewById(R.id.tv_address);
            ivEdit = (ImageView) super.findViewById(R.id.iv_edit);
        }


        public ItemHolder(Context context, ListView lv) {
            super(context, lv, R.layout.listitem_address_item);
        }

        @Override
        public ItemViewHolder<AddressItem> buildNewSelf(Context context) {
            return new ItemHolder(context, null);
        }

        @Override
        public void display(int i, AddressItem item) {
            tvName.setText(item.getConsignee());
            tvIsDefault.setText(item.getIsDefault() ? "默认" : "");
            tvPhone.setText(item.getMobile());
            tvAddress.setText(item.getRegion() + item.getAddress());
        }
    }

    public static class Major extends Agent.Major {
        public static final int MAJOR_CHOISE = createIntMajor(Major.class, 1);

        public Major(Class<? extends AddressManagerActivity> clazz) {
            super(clazz);
        }

        public void startForChoise(Activity activity, int rc) {
            builder(activity).setMajor(MAJOR_CHOISE).startActivityForResult(rc);
        }

        public Intent buildResult(AddressItem bean) {
            return Agent.Major.buildResult(bean);
        }


        public AddressItem parseResult(Intent intent) {
            return parseResult(intent, AddressItem.class);
        }
    }
}
