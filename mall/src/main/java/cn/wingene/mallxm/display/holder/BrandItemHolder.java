package cn.wingene.mallxm.display.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.home.firstMenu.data.IProductItem;
import cn.wingene.mallxm.display.tool.BindTool;

/**
 * Created by Wingene on 2017/9/9.
 */

public class BrandItemHolder extends RecyclerView.ViewHolder {
    private TextView brandProductNameV;
    private TextView brandProductPriceV;
    private TextView brandProductCanDeductible;
    private LinearLayout brandProductMarkGroupV;
    private TextView brandProductMarkV;
    private TextView brandProductMarkTwoV;
    private SimpleDraweeView brandProductImgV;

    private void initViews(View root) {
        brandProductNameV = (TextView) root.findViewById(R.id.brandProductNameV);
        brandProductPriceV = (TextView) root.findViewById(R.id.brandProductPriceV);
        brandProductCanDeductible = (TextView) root.findViewById(R.id.brandProductCanDeductible);
        brandProductMarkGroupV = (LinearLayout) root.findViewById(R.id.brandProductMarkGroupV);
        brandProductMarkV = (TextView) root.findViewById(R.id.brandProductMarkV);
        brandProductMarkTwoV = (TextView) root.findViewById(R.id.brandProductMarkTwoV);
        brandProductImgV = (SimpleDraweeView) root.findViewById(R.id.brandProductImgV);
    }

    public BrandItemHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public static BrandItemHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_brand_item_layout, parent,
                false);
        return new BrandItemHolder(view);
    }

    public void onBindViewHolder(final IProductItem item, int position) {
        BindTool.bindProducItemView(item, itemView, brandProductImgV, brandProductNameV, null,
                brandProductMarkGroupV, brandProductMarkV, brandProductMarkTwoV, brandProductPriceV,
                brandProductCanDeductible);
    }


}
