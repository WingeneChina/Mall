package cn.wingene.mallxm.display.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.home.firstMenu.data.IProductItem;
import cn.wingene.mallxm.display.tool.BindTool;

/**
 * Created by Wingene on 2017/9/9.
 */

public class YouLikeItemHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView youLikeItemImgV;
    private TextView youLikeProductDesV;
    private TextView youLikeProductNameV;
    private TextView youLikeMarkOneV;
    private TextView youLikeMarkTwoV;
    private TextView youLikeProductPriceV;
    private TextView youLikeProductCanDeductible;
    private RelativeLayout youLikeMarkGroupV;

    private void initViews(View root) {
        youLikeItemImgV = (SimpleDraweeView) root.findViewById(R.id.youLikeItemImgV);
        youLikeProductDesV = (TextView) root.findViewById(R.id.youLikeProductDesV);
        youLikeProductNameV = (TextView) root.findViewById(R.id.youLikeProductNameV);
        youLikeMarkGroupV = (RelativeLayout) root.findViewById(R.id.youLikeMarkGroupV);
        youLikeMarkOneV = (TextView) root.findViewById(R.id.youLikeMarkOneV);
        youLikeMarkTwoV = (TextView) root.findViewById(R.id.youLikeMarkTwoV);
        youLikeProductPriceV = (TextView) root.findViewById(R.id.youLikeProductPriceV);
        youLikeProductCanDeductible = (TextView) root.findViewById(R.id.youLikeProductCanDeductible);
    }

    private YouLikeItemHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public static YouLikeItemHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_youlike_item_layout,
                parent, false);
        return new YouLikeItemHolder(view);
    }

    public void onBindViewHolder(int position, final IProductItem item) {
        BindTool.bindProducItemView(item, itemView, youLikeItemImgV, youLikeProductNameV, youLikeProductDesV,
                youLikeMarkGroupV, youLikeMarkOneV, youLikeMarkTwoV, youLikeProductPriceV,
                youLikeProductCanDeductible);
    }

}
