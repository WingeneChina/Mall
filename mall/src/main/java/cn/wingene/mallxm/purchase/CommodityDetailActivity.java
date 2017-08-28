package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sennie.skulib.Sku;
import com.sennie.skulib.model.BaseSkuModel;

import junze.java.able.ICallBack;
import junze.java.util.CheckUtil;

import junze.widget.Tile;
import junze.widget.ViewPager;

import junze.android.ui.ViewHolder;
import junze.androidxf.core.Agent;
import junze.androidxf.tool.HtmlLoader;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.adapter.CommodityImagePagerAdapter;
import cn.wingene.mallxm.purchase.adapter.SkuAdapter;
import cn.wingene.mallxm.purchase.ask.AskBuyNow;
import cn.wingene.mallxm.purchase.ask.AskCartAdd;
import cn.wingene.mallxm.purchase.ask.AskProductDetail;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.ProductDetail;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.ProductImageList;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.ProductSpecList;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.Response;
import cn.wingene.mallxm.purchase.bean.ProductModel;
import cn.wingene.mallxm.purchase.bean.ProductModel.AttributesEntity;
import cn.wingene.mallxm.purchase.bean.ProductModel.AttributesEntity.AttributeMembersEntity;
import cn.wingene.mallxm.purchase.bean.UiData;
import cn.wingene.mallxm.purchase.listener.ItemClickListener;

/**
 * Created by Wingene on 2017/8/6.
 */

public class CommodityDetailActivity extends MyBaseActivity {
    public static Major major = new Major(CommodityDetailActivity.class);
    private CommodityImagePagerAdapter mImagePagerAdapter;
    int mProductId;
    int mPromotionId;
    int mBuyNumber;
    ProductDetail mProduct;
    List<ProductSpecList> mSpecList;
    private List<String> urlList = new ArrayList<>();
    private ProductModel mModel;

    UiData mUiData;

    private Tile tlBack;
    private ViewPager vpImage;
    private Tile tl1;
    private Tile tl2;
    private Tile tl3;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvPrice;
    private WebView wvDetail;
    private ImageView ivCart;
    private TextView tvBuy;
    private TextView tvAddCart;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        vpImage = (ViewPager) super.findViewById(R.id.vp_image);
        tl1 = (Tile) super.findViewById(R.id.tl_1);
        tl2 = (Tile) super.findViewById(R.id.tl_2);
        tl3 = (Tile) super.findViewById(R.id.tl_3);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
        tvPrice = (TextView) super.findViewById(R.id.tv_price);
        wvDetail = (WebView) super.findViewById(R.id.wv_detail);
        ivCart = (ImageView) super.findViewById(R.id.iv_cart);
        tvBuy = (TextView) super.findViewById(R.id.tv_buy);
        tvAddCart = (TextView) super.findViewById(R.id.tv_add_cart);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commdity_detail);
        initComponent();
        Params params = major.parseParams(this);
        if (params != null) {
            mProductId = params.productId;
            mPromotionId = params.promotionId;
        }else{
            finish();
            return;
        }
        // TODO: 2017/8/26
        mBuyNumber = 1;

        mUiData = new UiData();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ivCart.setOnClickListener(onCartClick());
        tvAddCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(buildProductModel());
            }
        });
        tvBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(buildProductModel());
            }
        });
        askProductDetail();

    }

    private void askProductDetail() {
        ask(new AskProductDetail.Request(mProductId, mPromotionId) {
            @Override
            public void updateUI(Response rsp) {
                mProduct = rsp.getProduct();
                mSpecList = rsp.getProductSpecList();
                List<ProductImageList> imageList = rsp.getProductImageList();
                initRollPager(imageList);
                Tile[] tiles = new Tile[]{tl1, tl2, tl3};
                for (int i = 0; i < imageList.size(); i++) {
                    if (i < 3) {
                        ImageHelper.displayImage(imageList.get(i).getThumbSrc(), tiles[i].getIvImage());
                    }
                }
                tvTitle.setText(mProduct.getName());
                tvSubTitle.setText(mProduct.getSellingPoint());
                tvPrice.setText(String.format("￥%.2f", mProduct.getPrice()));
                loadWebData(rsp.getProduct().getDetail());


            }

        });
    }

    @NonNull
    private OnClickListener onCartClick() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startShoppingCartActivity(getActivity());
            }
        };
    }

    @NonNull
    private OnClickListener onOperaClick(final boolean buyNow) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                Integer[] ids = new Integer[2];
                for (AttributeMembersEntity key : mUiData.getSelectedEntities()) {
                    sb.append(String.format("name : %s%ngroupid : %s%nmemberId : %s%nstatus:%s%n ", key.getName(), 
                            key.getAttributeGroupId(), key.getAttributeMemberId(), key.getStatus()));
                    ids[key.getAttributeGroupId() - 1] = key.getAttributeMemberId();
                }
                if (ids[0] == null || ids[1] == null) {
                    showToast("请继续选择");
                    return;
                }
                for (ProductSpecList item : mSpecList) {
                    if (CheckUtil.isEquals(ids[0], item.getSpec1ValueId()) && CheckUtil.isEquals(ids[1], item
                            .getSpec2ValueId())) {
                        if (buyNow) {
                            ask(new AskBuyNow.Request(mProductId, item.getId(), mPromotionId, mBuyNumber) {
                                @Override
                                public void updateUI(AskBuyNow.Response rsp) {
                                    OrderAddActivity.major.startActivity(getActivity(),rsp.data);
                                    mUiData.getBottomSheetDialog().hide();
                                }
                            });
                        } else {
                            ask(new AskCartAdd.Request(mProductId, mBuyNumber, item.getId()) {
                                @Override
                                public void updateUI(AskCartAdd.Response rsp) {
                                    showToast(rsp.msg);
                                    mUiData.getBottomSheetDialog().hide();
                                }
                            });
                        }
                    }
                }
            }
        };
    }


    private void loadWebData(String htmlCode) {
        if (htmlCode != null) {
            HtmlLoader.loadWebViewByHtmlCode(this, wvDetail, htmlCode, true);
        }
    }

    private void initRollPager(List<ProductImageList> list) {
        for (ProductImageList item : list) {
            urlList.add(item.getThumbSrc());
        }
        mImagePagerAdapter = new CommodityImagePagerAdapter(urlList);
        vpImage.setAdapter(mImagePagerAdapter);

    }

    public ProductModel buildProductModel() {
        if (mModel == null) {
            // 设置模拟数据
            mModel = new ProductModel();
            Map<Integer, String> colorMap = new HashMap<>();
            Map<Integer, String> sizeMap = new HashMap<>();
            for (ProductSpecList item : mSpecList) {
                Integer spec1ValueId = item.getSpec1ValueId();
                Integer spec2ValueId = item.getSpec2ValueId();
                mModel.getProductStocks().put(buildKey(spec1ValueId, spec2ValueId), new BaseSkuModel(item.getPrice
                        (), item.getStock()));
                colorMap.put(spec1ValueId, item.getSpec1Value());
                sizeMap.put(spec2ValueId, item.getSpec2Value());
            }

            addSpecGroup(mModel, 1, mProduct.getSpecDesp1(), colorMap);
            addSpecGroup(mModel, 2, mProduct.getSpecDesp2(), sizeMap);
        }
        return mModel;
    }


    public static class BottomSheetHolder extends ViewHolder {
        private RelativeLayout rlBottom;
        private LinearLayout llList;
        private TextView tvTitle;
        private LinearLayout llytNumber;
        private TextView tvReduce;
        private TextView tvNumber;
        private TextView tvIncrease;
        private LinearLayout llOperation;
        private Button cart;
        private Button buy;

        @Override
        protected void initComponent() {
            rlBottom = (RelativeLayout) super.findViewById(R.id.rl_bottom);
            llList = (LinearLayout) super.findViewById(R.id.ll_list);
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            llytNumber = (LinearLayout) super.findViewById(R.id.llyt_number);
            tvReduce = (TextView) super.findViewById(R.id.tv_reduce);
            tvNumber = (TextView) super.findViewById(R.id.tv_number);
            tvIncrease = (TextView) super.findViewById(R.id.tv_increase);
            llOperation = (LinearLayout) super.findViewById(R.id.ll_operation);
            cart = (Button) super.findViewById(R.id.cart);
            buy = (Button) super.findViewById(R.id.buy);
        }


        public BottomSheetHolder(Context context) {
            super(context, R.layout.bottom_sheet);
        }

        public void dispaly(OnClickListener onCartClick, OnClickListener onBuyClick, ProductModel mModel, UiData
                mUiData, int number, final ICallBack<Integer> numberCallback) {
            cart.setOnClickListener(onCartClick);
            buy.setOnClickListener(onBuyClick);
            tvNumber.setText("" + number);
            tvReduce.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer num = Integer.parseInt(tvNumber.getText().toString());
                    int result = --num >= 1 ? num : 1;
                    tvNumber.setText("" + num);
                    numberCallback.callBack(num);
                }
            });
            tvIncrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer num = Integer.parseInt(tvNumber.getText().toString());
                    int result = ++num;
                    tvNumber.setText("" + num);
                    numberCallback.callBack(num);
                }
            });
            //添加list组
            for (int i = 0; i < mModel.getAttributes().size(); i++) {
                View viewList = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_group, null);
                TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
                RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
                AttributesEntity attributesEntity = mModel.getAttributes().get(i);
                SkuAdapter skuAdapter = new SkuAdapter(attributesEntity.getAttributeMembers());
                tvTitle.setText(attributesEntity.getName());
                mUiData.getAdapters().add(skuAdapter);
                int item = 4;//设置列数
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), item);
                recyclerViewBottom.setLayoutManager(gridLayoutManager);
                recyclerViewBottom.setAdapter(skuAdapter);
                llList.addView(viewList);
            }
        }


    }


    private void showBottomSheetDialog(ProductModel productModel) {
        if (mUiData.getBottomSheetDialog() == null) {
            mUiData.getSelectedEntities().clear();
            mUiData.getAdapters().clear();
            BottomSheetHolder bottomSheetHolder = new BottomSheetHolder(this);
            bottomSheetHolder.dispaly(onOperaClick(true), onOperaClick(false), mModel, mUiData, mBuyNumber, new
                    ICallBack<Integer>() {
                @Override
                public void callBack(Integer num) {
                    mBuyNumber = num;
                }
            });
            View view = bottomSheetHolder.getView();
            //            View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
            //            LinearLayout llList = (LinearLayout) view.findViewById(R.id.ll_list);//列表
            //            Button btnCart = (Button) view.findViewById(R.id.cart);//列表
            //            Button btnBuy = (Button) view.findViewById(R.id.buy);//列表
            //            btnBuy.setOnClickListener(onOperaClick(true));
            //            btnCart.setOnClickListener(onOperaClick(false));
            //            //添加list组
            //            for (int i = 0; i < mModel.getAttributes().size(); i++) {
            //                View viewList = getLayoutInflater().inflate(R.layout.bottom_sheet_group, null);
            //                TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
            //                RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id
            // .recycler_bottom);
            //                AttributesEntity attributesEntity = mModel.getAttributes().get(i);
            //                SkuAdapter skuAdapter = new SkuAdapter(attributesEntity.getAttributeMembers());
            //                tvTitle.setText(attributesEntity.getName());
            //                mUiData.getAdapters().add(skuAdapter);
            //                int item = 4;//设置列数
            //                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, item);
            //                recyclerViewBottom.setLayoutManager(gridLayoutManager);
            //                recyclerViewBottom.setAdapter(skuAdapter);
            //                llList.addView(viewList);
            //            }
            // SKU 计算
            mUiData.setResult(Sku.skuCollection(mModel.getProductStocks()));
            for (String key : mUiData.getResult().keySet()) {
                Log.d("SKU Result", "key = " + key + " value = " + mUiData.getResult().get(key));
            }
            //设置点击监听器
            for (SkuAdapter adapter : mUiData.getAdapters()) {
                ItemClickListener listener = new ItemClickListener(mUiData, adapter);
                adapter.setOnClickListener(listener);
            }
            // 初始化按钮
            for (int i = 0; i < mUiData.getAdapters().size(); i++) {
                for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i)
                        .getAttributeMembersEntities()) {
                    if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null || mUiData.getResult()
                            .get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                        entity.setStatus(2);
                    }
                }
            }
            //设置价格
            mUiData.setBottomSheetDialog(new BottomSheetDialog(this));
            mUiData.getBottomSheetDialog().setContentView(view);
            View parent = (View) view.getParent();//获取ParentView
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            view.measure(0, 0);
            behavior.setPeekHeight(view.getMeasuredHeight());
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
            params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            parent.setLayoutParams(params);
            mUiData.getBottomSheetDialog().show();
        } else {
            mUiData.getBottomSheetDialog().show();
        }
    }

    private static void addSpecGroup(ProductModel model, Integer groupId, String desp, Map<Integer, String> specMap) {
        // 设置对应的品种和规格
        ProductModel.AttributesEntity group01 = new ProductModel.AttributesEntity();
        group01.setName(desp);
        for (Entry<Integer, String> entry : specMap.entrySet()) {
            group01.getAttributeMembers().add(new ProductModel.AttributesEntity.AttributeMembersEntity(groupId, 
                    entry.getKey(), entry.getValue()));
        }
        model.getAttributes().add(group01);//第一组
    }

    private static String buildKey(Integer spec1ValueId, Integer spec2ValueId) {
        return String.format("%s;%s", spec1ValueId, spec2ValueId);
    }

    public static class Major extends Agent.Major {

        public Major(Class<? extends CommodityDetailActivity> clazz) {
            super(clazz);
        }

        public void startActivity(Context src, int productId, int promotionId) {
            buildParams(src, new Params(productId, promotionId)).startActivity();
        }

        public Params parseParams(Activity target) {
            return parseParam(target, Params.class);
        }
    }

    private static class Params {
        int productId;
        int promotionId;

        public Params(int productId, int promotionId) {
            this.productId = productId;
            this.promotionId = promotionId;
        }
    }


}
