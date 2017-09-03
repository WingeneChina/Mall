package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_youlike_item_layout, parent,
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

        productListHolder.youLikeItemImgV.setImageURI(listBean.getDefaultImage());
        productListHolder.youLikeProductNameV.setText(listBean.getName());
        productListHolder.youLikeProductDesV.setVisibility(View.GONE);
        productListHolder.youLikeProductPriceV.setText("¥" + listBean.getPrice());

        productListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(productListHolder.itemView.getContext()
                        , listBean.getId());
            }
        });
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
        private SimpleDraweeView youLikeItemImgV;
        private TextView youLikeProductDesV;
        private TextView youLikeProductNameV;
        private TextView youLikeProductPriceV;

        public ProductListHolder(View itemView) {
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

    class NotDataHolder extends RecyclerView.ViewHolder {

        public NotDataHolder(View itemView) {
            super(itemView);
        }
    }
}
