package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/8/27.
 * 商品分类统一适配器
 */

public class ProductListCommentAdapter extends RecyclerView.Adapter {
    private List<ProductListModel.DataBean.ListBean> mDataBeanList;
    private static final int NOT_DATA = 2;

    public ProductListCommentAdapter(List<ProductListModel.DataBean.ListBean> dataBeanList) {
        mDataBeanList = dataBeanList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(this.getClass().getName(), "搜索结果 view 类型= " + viewType);
        if (NOT_DATA == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_data_layout, parent,
                    false);

            return new NotDataHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_person_recommend_item_layout,
                parent,
                false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        return new ProductListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductListHolder productListHolder = (ProductListHolder) holder;
        final ProductListModel.DataBean.ListBean listBean = mDataBeanList.get(position);
        try {
            productListHolder.personRecommendItemImgV.setImageURI(listBean.getDefaultImage());
            productListHolder.personRecommendProductNameV.setText(listBean.getName());
            if (!TextUtils.isEmpty(listBean.getSellingPoint())) {
                productListHolder.personRecommendProductDesV.setVisibility(View.VISIBLE);
                productListHolder.personRecommendProductDesV.setText(listBean.getSellingPoint());
            } else {
                productListHolder.personRecommendProductDesV.setVisibility(View.GONE);
            }
            productListHolder.personRecommendItemMarkTwoV.setVisibility(View.GONE);
            productListHolder.personRecommendItemMarkOneV.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(listBean.getTag())) {
                for (String string : listBean.getTag().toString().split(",")) {
                    TextView textView = (TextView) LayoutInflater.from(productListHolder.personRecommendMarkGroupV
                            .getContext())

                            .inflate(R
                                    .layout.productmark_layout, productListHolder.personRecommendMarkGroupV, false);
                    textView.setText(string);
                    productListHolder.personRecommendMarkGroupV.addView(textView);
                }
            } else {
                productListHolder.personRecommendMarkGroupV.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(listBean.getAcceptIntegral())) {
                productListHolder.personRecommendCanDeductible.setVisibility(View.GONE);
            } else {
                productListHolder.personRecommendCanDeductible.append(listBean.getAcceptIntegral());
            }

            productListHolder.personRecommendProductPriceV.setText("¥" + listBean.getPrice());

            productListHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpHelper.startCommodityDetailActivity(productListHolder.itemView.getContext()
                            , listBean.getId());
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(this.getClass().getName(), "填充数据异常");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataBeanList.size() == 0) {
            return NOT_DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    class ProductListHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView personRecommendItemImgV;
        private TextView personRecommendItemMarkOneV;
        private TextView personRecommendItemMarkTwoV;
        private TextView personRecommendProductNameV;
        private TextView personRecommendProductDesV;
        private TextView personRecommendProductPriceV;
        private RelativeLayout personRecommendMarkGroupV;
        private TextView personRecommendCanDeductible;

        public ProductListHolder(View itemView) {
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

    class NotDataHolder extends RecyclerView.ViewHolder {

        public NotDataHolder(View itemView) {
            super(itemView);
        }
    }
}
