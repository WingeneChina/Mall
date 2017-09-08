package cn.wingene.mallxm.display.home.firstMenu.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import junze.android.util.TextViewUtil;

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 每周新品
 */

public class PerWeekProductAdapter extends RecyclerView.Adapter {
    private List<RecommendModel.DataBean.NewBean.ProductListBean> mProductListBeen;

    public PerWeekProductAdapter(List<RecommendModel.DataBean.NewBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_perweek_item_layout, parent,
                false);

        return new PerWeekHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PerWeekHolder perWeekHolder = (PerWeekHolder) holder;
        final RecommendModel.DataBean.NewBean.ProductListBean productListBean = mProductListBeen.get(position);
        perWeekHolder.perWeekProductNameV.setText(productListBean.getProductName());
        perWeekHolder.perWeekProductImgV.setImageURI(productListBean.getProductImage());
        perWeekHolder.perWeekProductNameV.setText(productListBean.getProductName());


            TextViewUtil.showOrGone(perWeekHolder.perWeekProductDesV,productListBean.getSellingPoint());

        if (!TextUtils.isEmpty(productListBean.getTag())) {
            perWeekHolder.perWeekItemMarkOneV.setVisibility(View.GONE);
            perWeekHolder.perWeekItemMarkTwoV.setVisibility(View.GONE);
//            perWeekHolder.perWeekItemMarkTwoV.setText(productListBean.getTag().replace(",", "/"));
            int index = 0;
            for (String string : productListBean.getTag().split(",")) {
                TextView textView = (TextView) LayoutInflater.from(perWeekHolder.perWeekMarkGroupV.getContext())
                        .inflate(index++ == 0 ? R.layout.productmark_layout_first : R.layout.productmark_layout,
                                perWeekHolder.perWeekMarkGroupV, false);
                textView.setText(string);
                perWeekHolder.perWeekMarkGroupV.addView(textView);
            }
        } else {
            perWeekHolder.perWeekItemMarkOneV.setVisibility(View.GONE);
            perWeekHolder.perWeekItemMarkTwoV.setVisibility(View.GONE);
        }
        perWeekHolder.perWeekProductPriceV.setText("¥" + String.valueOf(productListBean.getProductPrice()));
        if (!TextUtils.isEmpty(productListBean.getAcceptIntegral())) {
            perWeekHolder.perWeekanDeductibleV.setVisibility(View.VISIBLE);
            perWeekHolder.perWeekanDeductibleV.setText("可抵应币¥" + String.valueOf(productListBean.getAcceptIntegral
                    ()));
        } else {
            perWeekHolder.perWeekanDeductibleV.setVisibility(View.GONE);
        }

        perWeekHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(perWeekHolder.perWeekProductPriceV.getContext()
                        , productListBean
                                .getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


    class PerWeekHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView perWeekProductImgV;
        private TextView perWeekItemMarkOneV;
        private TextView perWeekItemMarkTwoV;
        private TextView perWeekProductNameV;
        private TextView perWeekProductDesV;
        private TextView perWeekProductPriceV;
        private TextView perWeekanDeductibleV;
        private RelativeLayout perWeekMarkGroupV;

        public PerWeekHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View root) {
            perWeekProductImgV = (SimpleDraweeView) root.findViewById(R.id.perWeekProductImgV);
            perWeekItemMarkOneV = (TextView) root.findViewById(R.id.perWeekItemMarkOneV);
            perWeekItemMarkTwoV = (TextView) root.findViewById(R.id.perWeekItemMarkTwoV);
            perWeekProductNameV = (TextView) root.findViewById(R.id.perWeekProductNameV);
            perWeekProductDesV = (TextView) root.findViewById(R.id.perWeekProductDesV);
            perWeekProductPriceV = (TextView) root.findViewById(R.id.perWeekProductPriceV);
            perWeekMarkGroupV = (RelativeLayout) root.findViewById(R.id.perWeekMarkGroupV);
            perWeekanDeductibleV = (TextView) root.findViewById(R.id.perWeekanDeductible);
        }
    }
}
