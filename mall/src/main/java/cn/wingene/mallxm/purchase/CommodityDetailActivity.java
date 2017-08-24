package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sennie.skulib.Sku;
import com.sennie.skulib.model.BaseSkuModel;

import junze.widget.Tile;
import junze.widget.ViewPager;

import junze.android.util.FileUtil;
import junze.androidxf.tool.HtmlLoader;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.adapter.CommodityImagePagerAdapter;
import cn.wingene.mallxm.purchase.adapter.SkuAdapter;
import cn.wingene.mallxm.purchase.ask.AskProductDetail;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.Data;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.Product;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.ProductImageList;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.ProductSpecList;
import cn.wingene.mallxm.purchase.ask.AskProductDetail.Response;
import cn.wingene.mallxm.purchase.bean.ProductModel;
import cn.wingene.mallxm.purchase.bean.ProductModel.AttributesEntity;
import cn.wingene.mallxm.purchase.bean.UiData;
import cn.wingene.mallxm.purchase.listener.ItemClickListener;

/**
 * Created by Wingene on 2017/8/6.
 */

public class CommodityDetailActivity extends MyBaseActivity {
    private CommodityImagePagerAdapter mImagePagerAdapter;
    Product mProduct;
    List<ProductSpecList> mSpecList;
    private List<String> urlList = new ArrayList<>();
    private ProductModel testData;

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
        mUiData = new UiData();

        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ivCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startShoppingCartActivity(getActivity());
            }
        });
        tvAddCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(buildProductModel());
            }
        });
        ask(new AskProductDetail.Request(1, 0) {
            @Override
            public void updateUI(Response rsp) {
                super.updateUI(rsp);
                Data data = rsp.data;
                mProduct = data.getProduct();
                mSpecList = data.getProductSpecList();
                List<ProductImageList> imageList = data.getProductImageList();
                initRollPager(imageList);
                Tile[] tiles = new Tile[]{tl1, tl2, tl3};
                for (int i = 0; i < imageList.size(); i++) {
                    if (i < 3) {
                        ImageLoader.getInstance().displayImage(imageList.get(i).getThumbSrc(), tiles[i].getIvImage());
                    }
                }
                tvTitle.setText(mProduct.getName());
                tvSubTitle.setText(mProduct.getSellingPoint());
                tvPrice.setText(String.format("￥%.2f", mProduct.getPrice()));
                loadWebData(rsp.data.getProduct().getDetail());


            }

        });

    }

    private void loadWebData() {
        String htmlCode = FileUtil.readTextFileFromAssets(getActivity(), "temp/commodity_detail.txt");
        loadWebData(htmlCode);
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
        if (testData == null) {
            // 设置模拟数据
            testData = new ProductModel();
            Map<Integer, String> colorMap = new HashMap<>();
            Map<Integer, String> sizeMap = new HashMap<>();
            for (ProductSpecList item : mSpecList) {
                testData.getProductStocks().put(String.format("%s;%s", item.getSpec1ValueId(), item.getSpec2ValueId
                        ()), new BaseSkuModel(item.getPrice(), item.getStock()));
                colorMap.put(item.getSpec1ValueId(), item.getSpec1Value());
                sizeMap.put(item.getSpec2ValueId(), item.getSpec2Value());
            }

            addSpecGroup(1, mProduct.getSpecDesp1(), colorMap);
            addSpecGroup(2, mProduct.getSpecDesp2(), sizeMap);
        }
        return testData;
    }

    private void addSpecGroup(Integer groupId, String desp, Map<Integer, String> specMap) {
        // 设置对应的品种和规格
        ProductModel.AttributesEntity group01 = new ProductModel.AttributesEntity();
        group01.setName(desp);
        for (Entry<Integer, String> entry : specMap.entrySet()) {
            group01.getAttributeMembers().add(new ProductModel.AttributesEntity.AttributeMembersEntity(groupId,
                    entry.getKey(), entry.getValue()));
        }
        testData.getAttributes().add(group01);//第一组
    }

    private void showBottomSheetDialog(ProductModel productModel) {
        if (mUiData.getBottomSheetDialog() == null) {
            mUiData.getSelectedEntities().clear();
            mUiData.getAdapters().clear();
            View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
            LinearLayout llList = (LinearLayout) view.findViewById(R.id.ll_list);//列表
            //添加list组
            for (int i = 0; i < testData.getAttributes().size(); i++) {
                View viewList = getLayoutInflater().inflate(R.layout.bottom_sheet_group, null);
                TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
                RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
                AttributesEntity attributesEntity = testData.getAttributes().get(i);
                SkuAdapter skuAdapter = new SkuAdapter(attributesEntity.getAttributeMembers());
                tvTitle.setText(attributesEntity.getName());
                mUiData.getAdapters().add(skuAdapter);
                int item = 4;//设置列数
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, item);
                recyclerViewBottom.setLayoutManager(gridLayoutManager);
                recyclerViewBottom.setAdapter(skuAdapter);
                llList.addView(viewList);
            }
            // SKU 计算
            mUiData.setResult(Sku.skuCollection(testData.getProductStocks()));
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


}
