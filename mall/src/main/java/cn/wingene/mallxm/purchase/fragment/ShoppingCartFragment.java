package cn.wingene.mallxm.purchase.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;
import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;
import junze.android.ui.ItemViewHolder.OnItemViewClickListener;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.D;
import cn.wingene.mallxm.purchase.OrderAddActivity;
import cn.wingene.mallxm.purchase.ask.AskBuyCart;
import cn.wingene.mallxm.purchase.ask.AskCartEdit;
import cn.wingene.mallxm.purchase.ask.AskCartList;
import cn.wingene.mallxm.purchase.ask.AskCartList.CartItem;
import cn.wingene.mallxm.purchase.ask.AskCartList.Response;
import cn.wingene.mallxm.purchase.ask.AskCartRemove;
import cn.wingene.mallxm.purchase.tool.NumberTool;

/**
 * Created by Wingene on 2017/8/13.
 */

public class ShoppingCartFragment extends MyBaseFragment {
    private ItemHolder mItemHolder;
    Map<Integer, CartItemLocal> mCheckItemStates;

    private Tile tlBack;
    private Tile tlService;
    private ListView lvCartItem;
    private Tile tlSelectAll;
    private TextView tvTotal;
    private TextView tvOrder;

    protected void initComponent(View v){
        tlBack = (Tile) v.findViewById(R.id.tl_back);
        tlService = (Tile) v.findViewById(R.id.tl_service);
        lvCartItem = (ListView) v.findViewById(R.id.lv_cart_item);
        tlSelectAll = (Tile) v.findViewById(R.id.tl_select_all);
        tvTotal = (TextView) v.findViewById(R.id.tv_total);
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
        tlService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                agent().tryCallPhone("客服", D.CUSTOMER_PHONE);
            }
        });
        mCheckItemStates = new HashMap<>();
        mItemHolder = new ItemHolder(getContext(), lvCartItem);
        mItemHolder.setCheckItemStates(mCheckItemStates);
        mItemHolder.setOnItemViewClick("iv", onIvCheckClick());
        //        initOtherBuys();
        tlSelectAll.setOnClickListener(onSelectAllClick());
        tvOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList();
                for (CartItem item : mItemHolder.getList()) {
                    CartItemLocal local = mCheckItemStates.get(item.getId());
                    if (local != null && local.isChecked) {
                        list.add(""+item.getId());
                    }
                }
                final String cartIds = StringUtil.spellBy(list);
                agent().ask(new AskBuyCart.Request(cartIds) {
                    @Override
                    public void updateUI(AskBuyCart.Response rsp) {
                        OrderAddActivity.major.startActivity(getActivity(), cartIds, rsp.data);
                    }
                });
            }
        });
        return v;
    }

    private OnItemViewClickListener onIvCheckClick() {
        return new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, String s, ItemViewHolder<?> itemViewHolder, int i) {
                CartItem item = mItemHolder.getItem(i);
                CartItemLocal local = mCheckItemStates.get(item.getId());
                if (local != null) {
                    local.isChecked = !local.isChecked;
                } else {
                    local = new CartItemLocal(true);
                    mCheckItemStates.put(item.getId(), local);
                }
                refreshUI();
            }
        };
    }

    private OnClickListener onSelectAllClick() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllSelect()) {
                    mCheckItemStates.clear();
                } else {
                    mCheckItemStates.clear();
                    for (CartItem item : mItemHolder.getList()) {
                        mCheckItemStates.put(item.getId(), new CartItemLocal(true));
                    }
                }
                refreshUI();
            }
        };
    }

    public boolean isAllSelect() {
        if (mItemHolder.getList().size() == 0) {
            return false;
        }
        if (mItemHolder.getList().size() == mCheckItemStates.size()) {
            for (CartItemLocal value : mCheckItemStates.values()) {
                if (!value.isChecked) {
                    return false;
                }
            }
            return true;
        }
        return false;

    }


    public void refreshUI() {
        double total = 0;
        for (CartItem item : mItemHolder.getList()) {
            CartItemLocal local = mCheckItemStates.get(item.getId());
            if (local != null && local.isChecked) {
                total += item.getProductNumber() * item.getProductPrice();
            }
        }
        mItemHolder.notifyDataSetChanged();
        tlSelectAll.setSelected(isAllSelect());
        tvTotal.setText(String.format("￥%.2f", total));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        agent().ask(new AskCartList.Request() {
            @Override
            public void updateUI(Response rsp) {
                mItemHolder.clear();
                mItemHolder.addAll(rsp.data);
                refreshUI();
            }
        });
    }

    class ItemHolder extends ItemViewHolder<CartItem> {
        Map<Integer, CartItemLocal> __checkItemStates;

        private ImageView ivCheck;
        private ImageView ivProduct;
        private TextView tvTitle;
        private ImageView ivDelete;
        private TextView tvSubTitle;
        private TextView tvReduce;
        private TextView tvNumber;
        private TextView tvIncrease;
        private TextView tvPrice;

        @Override
        protected void initComponent() {
            ivCheck = (ImageView) super.findViewById(R.id.iv_check);
            ivProduct = (ImageView) super.findViewById(R.id.iv_product);
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            ivDelete = (ImageView) super.findViewById(R.id.iv_delete);
            tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
            tvReduce = (TextView) super.findViewById(R.id.tv_reduce);
            tvNumber = (TextView) super.findViewById(R.id.tv_number);
            tvIncrease = (TextView) super.findViewById(R.id.tv_increase);
            tvPrice = (TextView) super.findViewById(R.id.tv_price);
        }

        public ItemHolder(Context mContext, ListView lv) {
            super(mContext, lv, R.layout.listitem_shopping_cart_item);
        }

        @Override
        public ItemViewHolder<CartItem> buildNewSelf(Context context) {
            return new ItemHolder(context, null);
        }

        @Override
        protected ItemHolder getHeader() {
            return (ItemHolder) super.getHeader();
        }

        public Map<Integer, CartItemLocal> getCheckItemStates() {
            return getHeader().__checkItemStates;
        }

        public void setCheckItemStates(Map<Integer, CartItemLocal> __checkItemStates) {
            getHeader().__checkItemStates = __checkItemStates;
        }

        @Override
        public void display(int i, final CartItem cartItem) {
            CartItemLocal local = getCheckItemStates().get(cartItem.getId());
            ivCheck.setSelected(local != null && local.isChecked);
            ivCheck.setOnClickListener(buildClickForItem("iv", i));
            ImageHelper.displayImage(cartItem.getProductImage(), ivProduct);
            tvTitle.setText(cartItem.getProductName());
            tvSubTitle.setText(cartItem.getProductSpec());
            tvPrice.setText(String.format("￥%.2f", cartItem.getProductPrice()));
            tvNumber.setText("" + cartItem.getProductNumber());

            NumberTool.bindInteger(agent(), "请选择商品数量", 1, new IBuilder<Integer>() {
                @Override
                public Integer build() {
                    return cartItem.getProductNumber();
                }
            }, new IBuilder<Integer>() {
                @Override
                public Integer build() {
                    return Integer.MAX_VALUE;
                }
            }, tvReduce, tvNumber, tvIncrease, new ICallBack<Integer>() {
                @Override
                public void callBack(final Integer number) {
                    agent().ask(new AskCartEdit.Request(cartItem.getId(), number) {
                        @Override
                        public void updateUI(AskCartEdit.Response rsp) {
                            cartItem.setProductNumber(number);
                            ShoppingCartFragment.this.refreshUI();
                        }
                    });
                }
            });
            ivDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    agent().showConfirmDialog("提示", String.format("确认删除'%s'", cartItem.getProductName()), new
                            DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            agent().ask(new AskCartRemove.Request(cartItem.getId()) {
                                            @Override
                                            public void updateUI(AskCartRemove.Response rsp) {
                                                mCheckItemStates.remove(cartItem.getId());
                                                mItemHolder.getList().remove(cartItem);
                                                ShoppingCartFragment.this.refreshUI();
                                            }
                                        }

                            );
                        }
                    });
                }
            });
        }

    }

    public static class CartItemLocal {
        boolean isChecked;

        public CartItemLocal(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }


}
