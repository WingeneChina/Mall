package cn.wingene.mallxf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limecn.ghmall.R;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

/**
 * Created by wangcq on 2017/9/10.
 * 图片列表
 */

public class RecycleViewImgAdapter extends RecyclerView.Adapter {

    private List<ImageItem> imgItemList;
    private ItemImageClickListener mItemImageClickListener;

    public RecycleViewImgAdapter(List<ImageItem> imgItemList) {
        this.imgItemList = imgItemList;

    }

    public void setItemImageClickListener(ItemImageClickListener itemImageClickListener) {
        mItemImageClickListener = itemImageClickListener;
    }

    public void setImgItemList(List<ImageItem> imgItemList) {
        this.imgItemList = imgItemList;
    }

    public List<ImageItem> getImages() {
        return imgItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_item_layout, parent, false);

        return new ImgeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImgeHolder imgeHolder = (ImgeHolder) holder;
        imgeHolder.mSimpleDraweeView.setImageURI("file://" + imgItemList.get(position).path);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemImageClickListener != null) {
                    mItemImageClickListener.onItemImageClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgItemList.size();
    }


    private class ImgeHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mSimpleDraweeView;

        public ImgeHolder(View itemView) {
            super(itemView);

            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.imgItemV);
        }
    }

    public interface ItemImageClickListener {
        void onItemImageClick(int position);
    }
}
