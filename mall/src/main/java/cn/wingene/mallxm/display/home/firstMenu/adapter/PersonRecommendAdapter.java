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
 * 人气推荐
 */

public class PersonRecommendAdapter extends RecyclerView.Adapter {

    private List<RecommendModel.DataBean.RecommendBean.ProductListBean> mProductListBeen;

    public PersonRecommendAdapter(List<RecommendModel.DataBean.RecommendBean.ProductListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_person_recommend_item_layout,
                parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        return new PersonRecommendHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonRecommendHolder personRecommendHolder = (PersonRecommendHolder) holder;
        final RecommendModel.DataBean.RecommendBean.ProductListBean productListBean = mProductListBeen.get(position);
        personRecommendHolder.personRecommendItemImgV.setImageURI(productListBean.getProductImage());
        personRecommendHolder.personRecommendProductNameV.setText(productListBean.getProductName());
        personRecommendHolder.personRecommendProductDesV.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(productListBean.getTag())) {
            for (String string : productListBean.getTag().split(",")) {
                TextView textView = (TextView) LayoutInflater.from(personRecommendHolder.personRecommendMarkGroupV
                        .getContext()).inflate(R.layout.productmark_layout, personRecommendHolder
                        .personRecommendMarkGroupV, false);
                textView.setText(string);
                personRecommendHolder.personRecommendMarkGroupV.addView(textView);
            }
        } else {
            personRecommendHolder.personRecommendMarkGroupV.setVisibility(View.INVISIBLE);
        }

        personRecommendHolder.personRecommendItemMarkTwoV.setVisibility(View.GONE);
        personRecommendHolder.personRecommendItemMarkOneV.setVisibility(View.GONE);
        personRecommendHolder.personRecommendProductPriceV.setText("¥" + productListBean.getProductPrice());
        if (!TextUtils.isEmpty(productListBean.getAcceptIntegral())) {
            personRecommendHolder.personRecommendCanDeductible.setVisibility(View.VISIBLE);
            personRecommendHolder.personRecommendCanDeductible.setText("可抵应币¥" + String.valueOf(productListBean.getAcceptIntegral
                    ()));
        } else {
            personRecommendHolder.personRecommendCanDeductible.setVisibility(View.GONE);
        }

        personRecommendHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(personRecommendHolder.personRecommendProductDesV.getContext()
                        , productListBean
                                .getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


    class PersonRecommendHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView personRecommendItemImgV;
        private TextView personRecommendItemMarkOneV;
        private TextView personRecommendItemMarkTwoV;
        private TextView personRecommendProductNameV;
        private TextView personRecommendProductDesV;
        private TextView personRecommendProductPriceV;
        private RelativeLayout personRecommendMarkGroupV;
        private TextView personRecommendCanDeductible;

        public PersonRecommendHolder(View itemView) {
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
