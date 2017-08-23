package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
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
        PerWeekHolder perWeekHolder = (PerWeekHolder) holder;
        RecommendModel.DataBean.NewBean.ProductListBean productListBean = mProductListBeen.get(position);
        perWeekHolder.perWeekProductNameV.setText(productListBean.getProductName());
        perWeekHolder.perWeekProductImgV.setImageURI(productListBean.getProductImage());
        perWeekHolder.perWeekProductNameV.setText(productListBean.getProductName());
        perWeekHolder.perWeekProductDesV.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(productListBean.getTag())) {
            perWeekHolder.perWeekItemMarkTwoV.setText(productListBean.getTag().replace(",", "/"));
        }
        perWeekHolder.perWeekProductPriceV.setText("¥"+String.valueOf(productListBean.getProductPrice()));
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
        }
    }
}
