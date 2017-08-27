package cn.wingene.mallxm.display.home.secondMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemContentModel;

/**
 * Created by wangcq on 2017/8/14.
 * 精选适配器
 */

public class SelectItemAdapter extends RecyclerView.Adapter {
    private List<MenuItemContentModel.DataBean.ListBean> mListBean;


    public SelectItemAdapter(List<MenuItemContentModel.DataBean.ListBean> listBean) {
        mListBean = listBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_select_item_layout, parent,
                false);
        return new SelectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mListBean.size() > 0) {
            SelectHolder selectHolder = (SelectHolder) holder;
            MenuItemContentModel.DataBean.ListBean listBean = mListBean.get(position);
            selectHolder.titleV.setText(listBean.getTitle());
            selectHolder.markOneV.setText(listBean.getFrom());
            selectHolder.markTwoV.setText(String.valueOf(listBean.getClick()));
            for (int i = 0; i < listBean.getImageList().size(); i++) {
                switch (i) {
                    case 0:
                        selectHolder.desOneImgV.setImageURI(listBean.getImageList().get(0).getThumbSrc());
                        break;
                    case 1:
                        selectHolder.desTwoImgV.setImageURI(listBean.getImageList().get(1).getThumbSrc());

                        break;
                    case 2:
                        selectHolder.desThreeImgV.setImageURI(listBean.getImageList().get(2).getThumbSrc());

                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListBean.size() > 0 ? mListBean.size() : 8;
    }

    class SelectHolder extends RecyclerView.ViewHolder {

        private TextView titleV;
        private SimpleDraweeView desOneImgV;
        private SimpleDraweeView desTwoImgV;
        private SimpleDraweeView desThreeImgV;
        private TextView markOneV;
        private TextView markTwoV;

        public SelectHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View root) {
            titleV = (TextView) root.findViewById(R.id.titleV);
            desOneImgV = (SimpleDraweeView) root.findViewById(R.id.desOneImgV);
            desTwoImgV = (SimpleDraweeView) root.findViewById(R.id.desTwoImgV);
            desThreeImgV = (SimpleDraweeView) root.findViewById(R.id.desThreeImgV);
            markOneV = (TextView) root.findViewById(R.id.markOneV);
            markTwoV = (TextView) root.findViewById(R.id.markTwoV);
        }
    }
}
