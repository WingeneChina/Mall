package cn.wingene.mallxm.display.home.fiveMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.home.fiveMenu.daa.CollectionDataModel;

/**
 * Created by wangcq on 2017/9/10.
 * 收藏列表适配器
 */

public class CollectionAdapter extends RecyclerView.Adapter {

    private List<CollectionDataModel.DataBean.ListBean> mCollectionList;
    private OnCollectionItemClickListener mOnCollectionItemClickListener;
    private OnCollectionDeleteViewClickListener mOnCollectionDeleteViewClickListener;

    public CollectionAdapter(List<CollectionDataModel.DataBean.ListBean> collectionList) {
        mCollectionList = collectionList;
    }

    public void setOnCollectionItemClickListener(OnCollectionItemClickListener onCollectionItemClickListener) {
        mOnCollectionItemClickListener = onCollectionItemClickListener;
    }

    public void setOnCollectionDeleteViewClickListener(OnCollectionDeleteViewClickListener
                                                               onCollectionDeleteViewClickListener) {
        mOnCollectionDeleteViewClickListener = onCollectionDeleteViewClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item_layout, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CollectionHolder collectionHolder = (CollectionHolder) holder;
        mCollectionList.get(position);
        CollectionDataModel.DataBean.ListBean listBean = mCollectionList.get(position);
        collectionHolder.productImgV.setImageURI(listBean.getProductImage());
        collectionHolder.productNameV.setText(listBean.getProductName());
        collectionHolder.productPriceV.setText("￥" + listBean.getProductPrice());

        collectionHolder.deleteCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnCollectionDeleteViewClickListener != null) {
                    mOnCollectionDeleteViewClickListener.onCollectionItemDelete(position);
                }
            }
        });

        collectionHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCollectionItemClickListener != null) {
                    mOnCollectionItemClickListener.onCollectionItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCollectionList.size();
    }


    class CollectionHolder extends RecyclerView.ViewHolder {
        private TextView productPriceV;
        private SimpleDraweeView productImgV;
        private ImageView deleteCollection;
        private TextView productNameV;

        public CollectionHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }


        private void initViews(View root) {
            productImgV = (SimpleDraweeView) root.findViewById(R.id.productImgV);
            deleteCollection = (ImageView) root.findViewById(R.id.deleteCollection);
            productPriceV = (TextView) root.findViewById(R.id.productPriceV);
            productNameV = (TextView) root.findViewById(R.id.productNameV);
        }
    }

    public interface OnCollectionItemClickListener {
        void onCollectionItemClick(int position);
    }

    public interface OnCollectionDeleteViewClickListener {
        void onCollectionItemDelete(int position);
    }
}
