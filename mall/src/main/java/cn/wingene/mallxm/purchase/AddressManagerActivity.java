package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;
import junze.android.ui.ItemViewHolder.OnItemViewClickListener;
import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.ask.AskAddressDefault;
import cn.wingene.mallxm.purchase.ask.AskAddressList;
import cn.wingene.mallxm.purchase.ask.AskAddressList.AddressItem;
import cn.wingene.mallxm.purchase.ask.AskAddressList.Response;
import cn.wingene.mallxm.purchase.ask.AskAddressRemove;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AddressManagerActivity extends MyBaseActivity {
    public static Major major = new Major(AddressManagerActivity.class);
    public static final int RC_ADDRESS_ADD = 1000;
    ItemHolder mItemHolder;

    private Tile tlBack;
    private LinearLayout llytAddress;
    private ListView lvAddressItem;
    private RelativeLayout rlytAddressEmpty;
    private TextView tvAddAddress;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        llytAddress = (LinearLayout) super.findViewById(R.id.llyt_address);
        lvAddressItem = (ListView) super.findViewById(R.id.lv_address_item);
        rlytAddressEmpty = (RelativeLayout) super.findViewById(R.id.rlyt_address_empty);
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
                    //                    AddressAddActivity.major.startForResult(getActivity(),mItemHolder.getItem
                    // (position),RC_ADDRESS_ADD);
                }
            }
        });
        refreshUI();
        OnItemViewClickListener listener = new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, String s, ItemViewHolder<?> itemViewHolder, int i) {
                final AddressItem item = mItemHolder.getItem(i);
                if ("edit".equals(s)) {
                    AddressAddActivity.major.startForResult(getActivity(), item, RC_ADDRESS_ADD);
                } else if ("remove".equals(s)) {
                    showConfirmDialog("提示", String.format("确认删除%s", item.getConsignee()), new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAgent().ask(new AskAddressRemove.Request(item.getId()) {
                                @Override
                                public void updateUI(AskAddressRemove.Response rsp) {
                                    mItemHolder.getList().remove(item);
                                    refreshUI();
                                }
                            });
                        }
                    });
                } else if ("default".equals(s)) {
                    if (!item.getIsDefault()) {
                        getAgent().ask(new AskAddressDefault.Request(item.getId()) {
                            @Override
                            public void updateUI(AskAddressDefault.Response rsp) {
                                for (AddressItem ai : mItemHolder.getList()) {
                                    ai.setDefault(false);
                                }
                                item.setDefault(true);
                                refreshUI();
                            }
                        });
                    } else {
                        showToast("已经是默认地址了");
                    }
                }
            }
        };
        mItemHolder.setOnItemViewClick("edit", listener);
        mItemHolder.setOnItemViewClick("remove", listener);
        mItemHolder.setOnItemViewClick("default", listener);
    }

    public void askAddressList(){
        ask(new AskAddressList.Request(){
            @Override
            public void updateUI(Response rsp) {
                mItemHolder.clear();
                mItemHolder.addAll(rsp.getList());
                refreshUI();
            }
        });
    }

    public void refreshUI() {
        mItemHolder.notifyDataSetChanged();
        boolean empty = mItemHolder.isEmpty();
        llytAddress.setVisibility(empty ? View.GONE : View.VISIBLE);
        rlytAddressEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
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
        private TextView tvAddress;
        private TextView tvPhone;
        private TextView tvIsDefault;
        private TextView tvEdit;
        private TextView tvRemove;

        @Override
        protected void initComponent() {
            tvName = (TextView) super.findViewById(R.id.tv_name);
            tvAddress = (TextView) super.findViewById(R.id.tv_address);
            tvPhone = (TextView) super.findViewById(R.id.tv_phone);
            tvIsDefault = (TextView) super.findViewById(R.id.tv_is_default);
            tvEdit = (TextView) super.findViewById(R.id.tv_edit);
            tvRemove = (TextView) super.findViewById(R.id.tv_remove);
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
            tvIsDefault.setText(item.getIsDefault() ? "   默  认   " : "设为默认");
            tvIsDefault.setBackgroundResource(item.getIsDefault() ? R.drawable.shape_address_solid_color_primary : R
                    .drawable.shape_address_stroke_darkgray);
            tvPhone.setText(item.getMobile());
            tvAddress.setText(item.getRegion() + item.getAddress());
            tvEdit.setOnClickListener(buildClickForItem("edit", i));
            tvRemove.setOnClickListener(buildClickForItem("remove", i));
            tvIsDefault.setOnClickListener(buildClickForItem("default", i));
        }
    }

    public static class Major extends Agent.Major {
        public static final int MAJOR_CHOISE = createIntMajor(Major.class, 1);

        public Major(Class<? extends AddressManagerActivity> clazz) {
            super(clazz);
        }

        public void startForChoise(Activity activity, int rc) {
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(activity);
                return;
            }
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
