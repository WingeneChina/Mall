package cn.wingene.mallxm.purchase.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limecn.ghmall.R;

import cn.wingene.mallxm.purchase.bean.ProductModel;
import cn.wingene.mallxm.purchase.bean.ProductModel.AttributesEntity.AttributeMembersEntity;


/**
 * 项目名称：Sku
 * 类描述：
 * 创建人：N.Sun
 * 创建时间：2016/11/24 下午4:38
 * 修改人：N.Sun
 * 修改时间：2016/11/24 下午4:38
 * 修改备注：
 */
public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    private List<ProductModel.AttributesEntity.AttributeMembersEntity> mAttributeMembersEntities;
    OnClickListener mOnClickListener;
    private ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem;

    public SkuAdapter(List<ProductModel.AttributesEntity.AttributeMembersEntity> attributeMembersEntities) {
        this.mAttributeMembersEntities = attributeMembersEntities;
    }

    public ProductModel.AttributesEntity.AttributeMembersEntity getCurrentSelectedItem() {
        return currentSelectedItem;
    }

    public void setCurrentSelectedItem(ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem) {
        this.currentSelectedItem = currentSelectedItem;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getAttributeMembersEntities() {
        return mAttributeMembersEntities;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sku_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mAttributeMembersEntities.get(position));
        holder.setClick(position);
    }

    @Override
    public int getItemCount() {
        for (AttributeMembersEntity item : mAttributeMembersEntities) {
            if (item.getAttributeMemberId() != 0) {
                return mAttributeMembersEntities.size();
            }
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }

        public void setData(ProductModel.AttributesEntity.AttributeMembersEntity entity) {
            mTextView.setText(entity.getName());
            switch (entity.getStatus()) {
                case 0:
//                    mTextView.setAlpha(1f);
//                    mTextView.setBackgroundResource(android.R.color.darker_gray);
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(R.drawable.shape_spec_stroke_darkgray);
                    break;
                case 1:
//                    mTextView.setAlpha(1f);
//                    mTextView.setBackgroundResource(android.R.color.holo_red_light);
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(R.drawable.shape_spec_solid_color_primary);
                    break;
                case 2:
                    mTextView.setAlpha(0.4f);
                    mTextView.setBackgroundResource(R.drawable.shape_spec_stroke_darkgray);
//                    mTextView.setAlpha(0.4f);
//                    mTextView.setBackgroundResource(android.R.color.darker_gray);
                    break;
            }
        }

        public void setClick(final int position) {
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null)
                        mOnClickListener.onItemClickListener(position);
                }
            });
        }
    }

    public interface OnClickListener {
        void onItemClickListener(int position);
    }

}
