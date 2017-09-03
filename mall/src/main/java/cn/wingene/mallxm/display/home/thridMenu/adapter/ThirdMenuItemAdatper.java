package cn.wingene.mallxm.display.home.thridMenu.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxm.display.home.secondMenu.SpecialDetailActivity;
import cn.wingene.mallxm.display.home.secondMenu.adapter.SelectItemAdapter;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemContentModel;
import cn.wingene.mallxm.display.home.thridMenu.data.ThridItemModel;

/**
 * Created by wangcq on 2017/8/27.
 * 周边子页面adapter
 */

public class ThirdMenuItemAdatper extends RecyclerView.Adapter {
    private List<ThridItemModel.DataBean.ListBean> mListBean;

    public ThirdMenuItemAdatper(List<ThridItemModel.DataBean.ListBean> listBean) {
        mListBean = listBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_menu_item_layout, parent,
                false);
        return new ThirdMenuItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mListBean.size() > 0) {
            final ThirdMenuItemHolder selectHolder = (ThirdMenuItemHolder) holder;
            final ThridItemModel.DataBean.ListBean listBean = mListBean.get(position);
            selectHolder.titleV.setText(listBean.getTitle());
            selectHolder.markOneV.setText(listBean.getFrom());
            selectHolder.markTwoV.setText(String.valueOf(listBean.getDistance()));

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
            selectHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(selectHolder.itemView.getContext(), SpecialDetailActivity.class);
                    intent.putExtra("detailId", listBean.getId());
                    intent.putExtra("title",listBean.getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mListBean.size();
    }

    class ThirdMenuItemHolder extends RecyclerView.ViewHolder {
        private TextView titleV;
        private SimpleDraweeView desOneImgV;
        private SimpleDraweeView desTwoImgV;
        private SimpleDraweeView desThreeImgV;
        private TextView markOneV;
        private TextView markTwoV;

        public ThirdMenuItemHolder(View itemView) {
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
