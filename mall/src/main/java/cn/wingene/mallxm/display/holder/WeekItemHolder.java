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

public class WeekItemHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView perWeekProductImgV;
    private TextView perWeekItemMarkOneV;
    private TextView perWeekItemMarkTwoV;
    private TextView perWeekProductNameV;
    private TextView perWeekProductDesV;
    private TextView perWeekProductPriceV;
    private TextView perWeekanDeductibleV;
    private LinearLayout perWeekMarkGroupV;

    public WeekItemHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public static WeekItemHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_perweek_item_layout,
                parent, false);
        return new WeekItemHolder(view);
    }


    private void initViews(View root) {
        perWeekProductImgV = (SimpleDraweeView) root.findViewById(R.id.perWeekProductImgV);
        perWeekItemMarkOneV = (TextView) root.findViewById(R.id.perWeekItemMarkOneV);
        perWeekItemMarkTwoV = (TextView) root.findViewById(R.id.perWeekItemMarkTwoV);
        perWeekProductNameV = (TextView) root.findViewById(R.id.perWeekProductNameV);
        perWeekProductDesV = (TextView) root.findViewById(R.id.perWeekProductDesV);
        perWeekProductPriceV = (TextView) root.findViewById(R.id.perWeekProductPriceV);
        perWeekMarkGroupV = (LinearLayout) root.findViewById(R.id.perWeekMarkGroupV);
        perWeekanDeductibleV = (TextView) root.findViewById(R.id.perWeekanDeductible);
    }

    public void onBindViewHolder(final IProductItem item, int position) {
        BindTool.bindProducItemView(item, itemView, perWeekProductImgV, perWeekProductNameV, perWeekProductDesV,
                perWeekMarkGroupV, perWeekItemMarkOneV, perWeekItemMarkTwoV, perWeekProductPriceV,
                perWeekanDeductibleV);
    }


}
