package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sennie.skulib.Sku;
import com.sennie.skulib.model.BaseSkuModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;
import junze.java.util.CheckUtil;
import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.android.ui.ViewHolder;
import junze.android.util.EditTextUtil;
import junze.android.util.TextViewUtil;
import junze.androidxf.core.Agent;
import junze.androidxf.tool.HtmlLoader;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.http.Ask.NeedLoginException;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.ui.banner.BannerImgLoader;
import cn.wingene.mallxm.D;
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
import cn.wingene.mallxm.purchase.tool.NumberTool;

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
    private TextView tvActionbarTitle;
    private LinearLayout llytValid;
    private Banner bannerImage;
    private ImageView ivSellImage;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvPrice;
    private LinearLayout llytSpec;
    private TextView tvSpec;
    private WebView wvDetail;
    private Tile tlService;
    private Tile tlCart;
    private Tile tlCollect;
    private TextView tvBuy;
    private TextView tvAddCart;
    private RelativeLayout rlytInvalid;
    private TextView tvInvalidMsg;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tvActionbarTitle = (TextView) super.findViewById(R.id.tv_actionbar_title);
        llytValid = (LinearLayout) super.findViewById(R.id.llyt_valid);
        bannerImage = (Banner) super.findViewById(R.id.banner_image);
        ivSellImage = (ImageView) super.findViewById(R.id.iv_sell_image);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
        tvPrice = (TextView) super.findViewById(R.id.tv_price);
        llytSpec = (LinearLayout) super.findViewById(R.id.llyt_spec);
        tvSpec = (TextView) super.findViewById(R.id.tv_spec);
        wvDetail = (WebView) super.findViewById(R.id.wv_detail);
        tlService = (Tile) super.findViewById(R.id.tl_service);
        tlCart = (Tile) super.findViewById(R.id.tl_cart);
        tlCollect = (Tile) super.findViewById(R.id.tl_collect);
        tvBuy = (TextView) super.findViewById(R.id.tv_buy);
        tvAddCart = (TextView) super.findViewById(R.id.tv_add_cart);
        rlytInvalid = (RelativeLayout) super.findViewById(R.id.rlyt_invalid);
        tvInvalidMsg = (TextView) super.findViewById(R.id.tv_invalid_msg);
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
        mBuyNumber = 1;

        mUiData = new UiData();

        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tlService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getAgent().tryCallPhone("客服", D.CUSTOMER_PHONE);
            }
        });
        tlCart.setOnClickListener(onCartClick());
        tvSpec.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(buildProductModel());
            }
        });
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
        rlytInvalid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                askProductDetail();
            }
        });


    }

    private void askProductDetail() {
        ask("数据加载中...",false,new AskProductDetail.Request(mProductId, mPromotionId) {
            @Override
            public void updateUI(Response rsp) {
                tvActionbarTitle.setText("商品详情");
                rlytInvalid.setVisibility(View.GONE);
                llytValid.setVisibility(View.VISIBLE);
                mProduct = rsp.getProduct();
                mSpecList = rsp.getProductSpecList();
                List<ProductImageList> imageList = rsp.getProductImageList();
                initRollPager(imageList);
                if (StringUtil.isValid(mProduct.getSellingImage())) {
                    ImageHelper.displayImage(mProduct.getSellingImage(), ivSellImage);
                    ivSellImage.setVisibility(View.VISIBLE);
                } else {
                    ivSellImage.setVisibility(View.GONE);
                }
                tvTitle.setText(mProduct.getName());
                TextViewUtil.showOrGone(tvSubTitle, mProduct.getSellingPoint());
                tvPrice.setText(String.format("￥%.2f", mProduct.getPrice()));
                loadWebData(rsp.getProduct().getDetail());
                refreshUI();
            }

            @Override
            protected void updateUIWhenException(Exception exception) {
                rlytInvalid.setVisibility(View.VISIBLE);
                llytValid.setVisibility(View.GONE);
                if (exception != null && exception instanceof NeedLoginException) {
                    if (CheckUtil.isInclude(getActivity().getClass(), ShoppingCartActivity.class,
                            OrderListActivity.class, AddressManagerActivity.class, RechargeIndexActivity
                                    .class)) {
                        getActivity().finish();
                    }
                    JumpHelper.startLoginActivity(getActivity());
                    return;
                }
                if (exception != null && exception instanceof NotOKException) {
                    NotOKException e = (NotOKException) exception;
                    if (e.responseCode == 400) {
                        showToast("网络不稳定!!!!");
                        return;
                    }
                }
                if(exception != null && exception.getMessage().contains("下架")){
                    tvActionbarTitle.setText("商品已下架");
                }else{
                    tvActionbarTitle.setText("商品详情");
                    showToast(exception);
                }
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
                if (StringUtil.isValid(mProduct.getSpecDesp1()) && ids[0] == null) {
                    showToast("请选择%s", mProduct.getSpecDesp1());
                    return;
                }
                if (StringUtil.isValid(mProduct.getSpecDesp2()) && ids[1] == null) {
                    showToast("请选择%s", mProduct.getSpecDesp2());
                    return;
                }
                if (mSpecList.size() == 0) {
                    askBuy(buyNow, 0);
                    return;
                }
                for (ProductSpecList item : mSpecList) {
                    if (CheckUtil.isEquals(ids[0], item.getSpec1ValueId()) && CheckUtil.isEquals(ids[1], item
                            .getSpec2ValueId())) {
                        final Integer specId = item.getId();
                        askBuy(buyNow, specId);
                    }
                }
            }
        };
    }

    private void askBuy(boolean buyNow, final Integer specId) {
        if (buyNow) {
            ask(new AskBuyNow.Request(mProductId, specId, mPromotionId, mBuyNumber) {
                @Override
                public void updateUI(AskBuyNow.Response rsp) {
                    OrderAddActivity.major.startActivity(getActivity(), rsp.data);
                    mUiData.getBottomSheetDialog().hide();
                }
            });
        } else {
            ask(new AskCartAdd.Request(mProductId, mBuyNumber, specId) {
                @Override
                public void updateUI(AskCartAdd.Response rsp) {
                    showToast(rsp.msg);
                    mUiData.getBottomSheetDialog().hide();
                }
            });
        }
    }

    private Integer getValidStock() {
        int max = 0;
        StringBuilder sb = new StringBuilder();
        Integer[] ids = new Integer[2];
        for (AttributeMembersEntity key : mUiData.getSelectedEntities()) {
            sb.append(String.format("name : %s%ngroupid : %s%nmemberId : %s%nstatus:%s%n ", key.getName(), key
                    .getAttributeGroupId(), key.getAttributeMemberId(), key.getStatus()));
            ids[key.getAttributeGroupId() - 1] = key.getAttributeMemberId();
        }
        if (StringUtil.isValid(mProduct.getSpecDesp1()) && ids[0] == null) {
            return max;
        }
        if (StringUtil.isValid(mProduct.getSpecDesp2()) && ids[1] == null) {
            return max;
        }
        for (ProductSpecList item : mSpecList) {
            if (CheckUtil.isEquals(ids[0], item.getSpec1ValueId()) && CheckUtil.isEquals(ids[1], item
                    .getSpec2ValueId())) {
                return item.getStock();
            }
        }
        return mProduct.getStock();
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
        bannerImage.setImages(urlList).isAutoPlay(false).setBannerStyle(BannerConfig.NUM_INDICATOR).setImageLoader(new
                BannerImgLoader()).start();

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

    public void refreshUI() {
        if (mSpecList == null || mSpecList.isEmpty()) {
            llytSpec.setVisibility(View.GONE);
        } else {
            llytSpec.setVisibility(View.VISIBLE);
            if (mModel == null) {
                tvSpec.setText("请选择商品规格");
            } else {
                StringBuilder sb = new StringBuilder();
                String[] names = new String[2];
                for (AttributeMembersEntity key : mUiData.getSelectedEntities()) {
                    names[key.getAttributeGroupId() - 1] = key.getName();
                }
                List<String> list = new ArrayList<>();
                if (StringUtil.isValid(mProduct.getSpecDesp1()) && StringUtil.isValid(names[0])) {
                    list.add(String.format("%s:%s",mProduct.getSpecDesp1(),names[0]));
                }
                if (StringUtil.isValid(mProduct.getSpecDesp2()) && StringUtil.isValid(names[1])) {
                    list.add(String.format("%s:%s",mProduct.getSpecDesp2(),names[1]));
                }
                String info = StringUtil.spellBy(list);
                tvSpec.setText(StringUtil.isValid(info) ? info : "请选择商品规格");
            }
        }
    }

    private void showBottomSheetDialog(ProductModel productModel) {
        if (mUiData.getBottomSheetDialog() == null) {
            mUiData.getSelectedEntities().clear();
            mUiData.getAdapters().clear();
            final BottomSheetHolder bottomSheetHolder = new BottomSheetHolder(this);
            bottomSheetHolder.dispaly(getAgent(), onOperaClick(true), onOperaClick(false), mModel, mUiData, new
                    IBuilder<Integer>() {
                @Override
                public Integer build() {
                    return mBuyNumber;
                }
            }, new IBuilder<Integer>() {
                @Override
                public Integer build() {
                    int max = Math.min(mProduct.getStock(), getValidStock());
                    return Math.max(max, 1);
                }
            }, new ICallBack<Integer>() {
                @Override
                public void callBack(Integer num) {
                    mBuyNumber = num;
                    bottomSheetHolder.updateNumber(mBuyNumber);
                }
            });
            View view = bottomSheetHolder.getView();
            // SKU 计算
            mUiData.setResult(Sku.skuCollection(mModel.getProductStocks()));
            for (String key : mUiData.getResult().keySet()) {
                Log.d("SKU Result", "key = " + key + " value = " + mUiData.getResult().get(key));
            }
            //设置点击监听器
            for (SkuAdapter adapter : mUiData.getAdapters()) {
                ItemClickListener listener = new ItemClickListener(mUiData, adapter);
                listener.setOnClickListener(new SkuAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        mBuyNumber = 1;
                        bottomSheetHolder.updateNumber(mBuyNumber);
                    }
                });
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
            mUiData.getBottomSheetDialog().setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    refreshUI();
                }
            });
        } else {
            mUiData.getBottomSheetDialog().show();
        }
    }

    private static void addSpecGroup(ProductModel model, Integer groupId, String desp, Map<Integer, String> specMap) {
        // 设置对应的品种和规格
        ProductModel.AttributesEntity group = new ProductModel.AttributesEntity();
        group.setName(desp);
        for (Entry<Integer, String> entry : specMap.entrySet()) {
            group.getAttributeMembers().add(new ProductModel.AttributesEntity.AttributeMembersEntity(groupId,
                    entry.getKey(), entry.getValue()));
        }
        model.getAttributes().add(group);//第一组
    }

    private static String buildKey(Integer spec1ValueId, Integer spec2ValueId) {
        return String.format("%s;%s", spec1ValueId, spec2ValueId);
    }

    public static class BottomSheetHolder extends ViewHolder {
        private RelativeLayout rlBottom;
        private LinearLayout llList;
        private LinearLayout llytNumber;
        private TextView tvTitle;
        private TextView tvReduce;
        private EditText etNumber;
        private TextView tvIncrease;
        private LinearLayout llOperation;
        private TextView tvBuy;
        private TextView tvAddCart;

        protected void initComponent() {
            rlBottom = (RelativeLayout) super.findViewById(R.id.rl_bottom);
            llList = (LinearLayout) super.findViewById(R.id.ll_list);
            llytNumber = (LinearLayout) super.findViewById(R.id.llyt_number);
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            tvReduce = (TextView) super.findViewById(R.id.tv_reduce);
            etNumber = (EditText) super.findViewById(R.id.et_number);
            tvIncrease = (TextView) super.findViewById(R.id.tv_increase);
            llOperation = (LinearLayout) super.findViewById(R.id.ll_operation);
            tvBuy = (TextView) super.findViewById(R.id.tv_buy);
            tvAddCart = (TextView) super.findViewById(R.id.tv_add_cart);
        }



        public BottomSheetHolder(Context context) {
            super(context, R.layout.bottom_sheet);
            etNumber.setSelectAllOnFocus(true);
        }

        public void dispaly(final Agent agent, final OnClickListener onBuyClick, final OnClickListener onCartClick,
                ProductModel
                mModel, UiData mUiData, final IBuilder<Integer> bCurrent, final IBuilder<Integer> bMax, final
        ICallBack<Integer> numberCallback) {
            tvBuy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!StringUtil.isValid(etNumber.getText().toString())){
                        agent.showToast("请输入数量");
                        return;
                    }
                    onBuyClick.onClick(v);


                }
            });
            tvAddCart.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!StringUtil.isValid(etNumber.getText().toString())){
                        agent.showToast("请输入数量");
                        return;
                    }
                    onCartClick.onClick(v);
                }
            });
            updateNumber(bCurrent.build());
            NumberTool.bindInteger(agent, "请输入数量", 1, bCurrent, bMax, tvReduce, etNumber, tvIncrease, numberCallback);
            //添加list组
            for (int i = 0; i < mModel.getAttributes().size(); i++) {
                View viewList = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_group, null);
                TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
                RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
                AttributesEntity attributesEntity = mModel.getAttributes().get(i);
                SkuAdapter skuAdapter = new SkuAdapter(attributesEntity.getAttributeMembers());
//                tvTitle.setText(attributesEntity.getName());
                TextViewUtil.showOrGone(tvTitle,attributesEntity.getName());
                mUiData.getAdapters().add(skuAdapter);
                int item = 4;//设置列数
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), item);
                recyclerViewBottom.setLayoutManager(gridLayoutManager);
                recyclerViewBottom.setAdapter(skuAdapter);
                llList.addView(viewList);
            }
        }

        public void updateNumber(Integer integer) {
            etNumber.setText(String.format("%s", integer));
            EditTextUtil.moveCourseToLast(etNumber);
        }


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
