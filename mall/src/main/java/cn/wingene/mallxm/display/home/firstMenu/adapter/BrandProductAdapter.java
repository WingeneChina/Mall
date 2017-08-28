package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/11.
 * 品牌大厂适配器
 */

public class BrandProductAdapter extends RecyclerView.Adapter {
    private List<RecommendModel.DataBean.BrandBean.ProductListBean> mProductListBeen;

    public BrandProductAdapter(List<RecommendModel.DataBean.BrandBean.ProductListBean> productListBeen) {
        this.mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_brand_item_layout, parent,
                false);

        return new BrandHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final RecommendModel.DataBean.BrandBean.ProductListBean productListBean = mProductListBeen.get(position);
        final BrandHolder brandHolder = (BrandHolder) holder;
        brandHolder.brandProductNameV.setText(productListBean.getProductName());
        brandHolder.brandProductPriceV.setText(String.valueOf(productListBean.getProductPrice()));
        brandHolder.brandProductImgV.setImageURI(productListBean.getProductImage());
        if (!TextUtils.isEmpty(productListBean.getTag())) {
            String tags = productListBean.getTag().replace(",", "/");
            brandHolder.brandProductMarkV.setBackgroundResource(R.drawable.red_shape);
            brandHolder.brandProductMarkV.setText(tags);
        }

        brandHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(brandHolder.brandProductImgV.getContext(), productListBean
                        .getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


    class BrandHolder extends RecyclerView.ViewHolder {
        private TextView brandProductNameV;
        private TextView brandProductPriceV;
        private TextView brandProductMarkV;
        private TextView brandProductMarkTwoV;
        private SimpleDraweeView brandProductImgV;

        public BrandHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View root) {
            brandProductNameV = (TextView) root.findViewById(R.id.brandProductNameV);
            brandProductPriceV = (TextView) root.findViewById(R.id.brandProductPriceV);
            brandProductMarkV = (TextView) root.findViewById(R.id.brandProductMarkV);
            brandProductMarkTwoV = (TextView) root.findViewById(R.id.brandProductMarkTwoV);
            brandProductImgV = (SimpleDraweeView) root.findViewById(R.id.brandProductImgV);
        }
    }
}
