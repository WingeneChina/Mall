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

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 天天特价
 */

public class DaySpecialPriceAdapter extends RecyclerView.Adapter {

    private List<RecommendModel.DataBean.SpecialsBean.ProductListBean> mProductListBeen;

    public DaySpecialPriceAdapter(List<RecommendModel.DataBean.SpecialsBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_person_recommend_item_layout,
                parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return new DaySpecialPrice(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DaySpecialPrice daySpecialPrice = (DaySpecialPrice) holder;
        final RecommendModel.DataBean.SpecialsBean.ProductListBean productListBean = mProductListBeen.get(position);
        daySpecialPrice.personRecommendItemImgV.setImageURI(productListBean.getProductImage());
        daySpecialPrice.personRecommendProductNameV.setText(productListBean.getProductName());
        daySpecialPrice.personRecommendProductPriceV.setText("¥" + productListBean.getProductPrice());
        if (!TextUtils.isEmpty(productListBean.getTag())) {
            int index = 0;
            for (String string : productListBean.getTag().split(",")) {
                TextView textView = (TextView) LayoutInflater.from(daySpecialPrice.personRecommendMarkGroupV
                        .getContext()).inflate(index++ == 0 ? R.layout.productmark_layout_first : R.layout
                        .productmark_layout, daySpecialPrice.personRecommendMarkGroupV, false);
                textView.setText(string);
                daySpecialPrice.personRecommendMarkGroupV.addView(textView);
                ;
            }
        } else {
            daySpecialPrice.personRecommendMarkGroupV.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(productListBean.getAcceptIntegral())) {
            daySpecialPrice.personRecommendCanDeductible.setVisibility(View.VISIBLE);
            daySpecialPrice.personRecommendCanDeductible.setText("可抵应币¥" + String.valueOf(productListBean.getAcceptIntegral
                    ()));
        } else {
            daySpecialPrice.personRecommendCanDeductible.setVisibility(View.INVISIBLE);
        }

//        daySpecialPrice.personRecommendItemMarkTwoV.setText(productListBean.getTag().replace(",", "/"));
        daySpecialPrice.personRecommendItemMarkTwoV.setVisibility(View.GONE);
        daySpecialPrice.personRecommendItemMarkOneV.setVisibility(View.GONE);
        daySpecialPrice.personRecommendProductDesV.setVisibility(View.GONE);

        daySpecialPrice.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(daySpecialPrice.personRecommendItemImgV.getContext(),
                        productListBean
                                .getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


    class DaySpecialPrice extends RecyclerView.ViewHolder {

        private SimpleDraweeView personRecommendItemImgV;
        private TextView personRecommendItemMarkOneV;
        private TextView personRecommendItemMarkTwoV;
        private TextView personRecommendProductNameV;
        private TextView personRecommendProductDesV;
        private TextView personRecommendProductPriceV;
        private RelativeLayout personRecommendMarkGroupV;
        private TextView personRecommendCanDeductible;

        public DaySpecialPrice(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View root) {
            personRecommendItemImgV = (SimpleDraweeView) root.findViewById(R.id.personRecommendItemImgV);
            personRecommendItemMarkOneV = (TextView) root.findViewById(R.id.personRecommendItemMarkOneV);
            personRecommendItemMarkTwoV = (TextView) root.findViewById(R.id.personRecommendItemMarkTwoV);
            personRecommendProductNameV = (TextView) root.findViewById(R.id.personRecommendProductNameV);
            personRecommendProductDesV = (TextView) root.findViewById(R.id.personRecommendProductDesV);
            personRecommendProductPriceV = (TextView) root.findViewById(R.id.personRecommendProductPriceV);
            personRecommendMarkGroupV = (RelativeLayout) root.findViewById(R.id.personRecommendMarkGroupV);
            personRecommendCanDeductible = (TextView) root.findViewById(R.id.personRecommendCanDeductible);
        }
    }
}
