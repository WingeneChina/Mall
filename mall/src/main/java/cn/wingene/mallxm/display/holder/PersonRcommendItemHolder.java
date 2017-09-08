package cn.wingene.mallxm.display.holder;

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
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel.DataBean.RecommendBean.ProductListBean;

/**
 * Created by Wingene on 2017/9/9.
 */

public class PersonRcommendItemHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView personRecommendItemImgV;
    private TextView personRecommendItemMarkOneV;
    private TextView personRecommendItemMarkTwoV;
    private TextView personRecommendProductNameV;
    private TextView personRecommendProductDesV;
    private TextView personRecommendProductPriceV;
    private RelativeLayout personRecommendMarkGroupV;
    private TextView personRecommendCanDeductible;

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

    private PersonRcommendItemHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public static PersonRcommendItemHolder build(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recommend_person_recommend_item_layout, parent, false);
        return new PersonRcommendItemHolder(view);
    }

    public void onBindViewHolder(List<ProductListBean> list, int position) {
        final RecommendModel.DataBean.RecommendBean.ProductListBean productListBean = list.get(position);
        personRecommendItemImgV.setImageURI(productListBean.getProductImage());
        personRecommendProductNameV.setText(productListBean.getProductName());
        personRecommendProductDesV.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(productListBean.getTag())) {
            int index = 0;
            for (String string : productListBean.getTag().split(",")) {
                TextView textView = (TextView) LayoutInflater.from(personRecommendMarkGroupV.getContext()).inflate
                        (index++ == 0 ? R.layout.productmark_layout_first : R.layout.productmark_layout,
                                personRecommendMarkGroupV, false);
                textView.setText(string);
                personRecommendMarkGroupV.addView(textView);
            }
        } else {
            personRecommendMarkGroupV.setVisibility(View.INVISIBLE);
        }

        personRecommendItemMarkTwoV.setVisibility(View.GONE);
        personRecommendItemMarkOneV.setVisibility(View.GONE);
        personRecommendProductPriceV.setText("¥" + productListBean.getProductPrice());
        if (!TextUtils.isEmpty(productListBean.getAcceptIntegral())) {
            personRecommendCanDeductible.setVisibility(View.VISIBLE);
            personRecommendCanDeductible.setText("可抵应币¥" + String.valueOf(productListBean.getAcceptIntegral()));
        } else {
            personRecommendCanDeductible.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(personRecommendProductDesV.getContext(), productListBean
                        .getProductId());
            }
        });
    }
}
