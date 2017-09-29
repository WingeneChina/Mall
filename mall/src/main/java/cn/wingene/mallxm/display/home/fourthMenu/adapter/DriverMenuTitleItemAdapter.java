package cn.wingene.mallxm.display.home.fourthMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limecn.ghmall.R;

import java.util.List;

import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/9/28.
 */

public class DriverMenuTitleItemAdapter extends RecyclerView.Adapter {

    private List<RecommendModel.DataBean.BannerListBean> menuList;
    private OnMenuItemClickListener mOnMenuItemClickListener;


    public DriverMenuTitleItemAdapter(List<RecommendModel.DataBean.BannerListBean> menuList) {
        this.menuList = menuList;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener){
        this.mOnMenuItemClickListener = menuItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_title_item_layout, parent, false);

        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecommendModel.DataBean.BannerListBean bannerListBean = menuList.get(position);
        MenuHolder menuHolder = (MenuHolder) holder;
        menuHolder.titleImgV.setImageURI(bannerListBean.getImage());
        menuHolder.titleNameV.setText(bannerListBean.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnMenuItemClickListener !=null){
                    mOnMenuItemClickListener.onMenuItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class MenuHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView titleImgV;
        private TextView titleNameV;

        public MenuHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }


        private void initViews(View root) {
            titleImgV = (SimpleDraweeView) root.findViewById(R.id.titleImgV);
            titleNameV = (TextView) root.findViewById(R.id.titleNameV);
        }
    }

    public interface OnMenuItemClickListener{
        void onMenuItemClick(int position);
    }
}
