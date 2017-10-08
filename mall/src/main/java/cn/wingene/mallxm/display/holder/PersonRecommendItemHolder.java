package cn.wingene.mallxm.display.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limecn.ghmall.R;

import cn.wingene.mallxm.display.home.firstMenu.data.IProductItem;
import cn.wingene.mallxm.display.tool.BindTool;

/**
 * 人气推荐
 * Created by Wingene on 2017/9/9.
 */

public class PersonRecommendItemHolder extends ViewHolder {
    private SimpleDraweeView personRecommendItemImgV;
    private TextView personRecommendItemMarkOneV;
    private TextView personRecommendItemMarkTwoV;
    private TextView personRecommendProductNameV;
    private TextView personRecommendProductDesV;
    private TextView personRecommendProductPriceV;
    private LinearLayout personRecommendMarkGroupV;
    private TextView personRecommendCanDeductible;

    private void initViews(View root) {
        personRecommendItemImgV = (SimpleDraweeView) root.findViewById(R.id.personRecommendItemImgV);
        personRecommendItemMarkOneV = (TextView) root.findViewById(R.id.personRecommendItemMarkOneV);
        personRecommendItemMarkTwoV = (TextView) root.findViewById(R.id.personRecommendItemMarkTwoV);
        personRecommendProductNameV = (TextView) root.findViewById(R.id.personRecommendProductNameV);
        personRecommendProductDesV = (TextView) root.findViewById(R.id.personRecommendProductDesV);
        personRecommendProductPriceV = (TextView) root.findViewById(R.id.personRecommendProductPriceV);
        personRecommendMarkGroupV = (LinearLayout) root.findViewById(R.id.personRecommendMarkGroupV);
        personRecommendCanDeductible = (TextView) root.findViewById(R.id.personRecommendCanDeductible);

    }

    private PersonRecommendItemHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    public static PersonRecommendItemHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recommend_person_recommend_item_layout, parent, false);
        return new PersonRecommendItemHolder(view);
    }

    public void onBindViewHolder(final IProductItem item, int position) {
        onBindViewHolder(item, position,false);
    }
    
    public void onBindViewHolder(final IProductItem item, int position,boolean defaultisJiaPei) {
        try {
            _onBindViewHolder(item,defaultisJiaPei);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "填充数据异常");
        }
    }

    private void _onBindViewHolder(final IProductItem item, boolean defaultisJiaPei) {
        BindTool.bindProducItemView(item, itemView, personRecommendItemImgV, personRecommendProductNameV, 
                personRecommendProductDesV, personRecommendMarkGroupV, personRecommendItemMarkOneV, 
                personRecommendItemMarkTwoV, personRecommendProductPriceV, personRecommendCanDeductible, 
                defaultisJiaPei);
    }


}
