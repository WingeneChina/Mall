package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/9/4.
 * 居家/服饰..适配器
 */

public class ProductGroupAdapter extends RecyclerView.Adapter {

    private List<ProductListModel.DataBean.ListBean> mProductListBeen;

    public ProductGroupAdapter(List<ProductListModel.DataBean.ListBean> productListBeen) {
        mProductListBeen = productListBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_youlike_item_layout, parent,
                false);

        return new ProductGroupAdapter.YouLikeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductGroupAdapter.YouLikeHolder youLikeHolder = (ProductGroupAdapter.YouLikeHolder) holder;
        final ProductListModel.DataBean.ListBean productListBean = mProductListBeen.get(position);
        youLikeHolder.youLikeItemImgV.setImageURI(productListBean.getDefaultImage());
        youLikeHolder.youLikeProductNameV.setText(productListBean.getName());
        youLikeHolder.youLikeProductDesV.setText(productListBean.getSellingPoint());
        youLikeHolder.youLikeProductPriceV.setText("¥" + productListBean.getPrice());

        youLikeHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(youLikeHolder.itemView.getContext()
                        , productListBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductListBeen.size();
    }


    class YouLikeHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView youLikeItemImgV;
        private TextView youLikeProductDesV;
        private TextView youLikeProductNameV;
        private TextView youLikeProductPriceV;

        public YouLikeHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View root) {
            youLikeItemImgV = (SimpleDraweeView) root.findViewById(R.id.youLikeItemImgV);
            youLikeProductDesV = (TextView) root.findViewById(R.id.youLikeProductDesV);
            youLikeProductNameV = (TextView) root.findViewById(R.id.youLikeProductNameV);
            youLikeProductPriceV = (TextView) root.findViewById(R.id.youLikeProductPriceV);
        }
    }

}
