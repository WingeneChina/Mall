package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import junze.java.util.StringUtil;

import junze.widget.HightMatchListView;
import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;
import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.purchase.ask.AskLogisticsDetail.Logistics;
import cn.wingene.mallxm.purchase.ask.AskLogisticsDetail.Traces;

/**
 * Created by Wingene on 2017/9/3.
 */

public class LogisticsActivity extends MyBaseActivity {
    public static Major major = new Major(LogisticsActivity.class);

    private Tile tlBack;
    private Tile tlService;
    private ImageView ivImage;
    private TextView tvValue1;
    private TextView tvValue2;
    private TextView tvValue3;
    private TextView tvValue4;
    private HightMatchListView lvContent;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tlService = (Tile) super.findViewById(R.id.tl_service);
        ivImage = (ImageView) super.findViewById(R.id.iv_image);
        tvValue1 = (TextView) super.findViewById(R.id.tv_value_1);
        tvValue2 = (TextView) super.findViewById(R.id.tv_value_2);
        tvValue3 = (TextView) super.findViewById(R.id.tv_value_3);
        tvValue4 = (TextView) super.findViewById(R.id.tv_value_4);
        lvContent = (HightMatchListView) super.findViewById(R.id.lv_content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Logistics bean = major.parseParams(this);
        ImageHelper.displayImage(bean.getProductImage(), ivImage);
        tvValue1.setText(makeLabel(bean.getState()));
        tvValue2.setText(bean.getShipperName());
        tvValue3.setText(bean.getLogisticCode());
        //        tvValue4.setText(bean.getShipperName());
        tvValue4.setText(""); // TODO: 2017/9/3 无电话
        ItemHolder itemHolder = new ItemHolder(this,lvContent);
        itemHolder.addAll(bean.getTraces());
        if(StringUtil.isValid(bean.getReason())){
            showToast(bean.getReason());
        }

    }

    public String makeLabel(Integer state) {
        switch (state) {
        case 2:
            return "在途中";
        case 3:
            return "签收";
        case 4:
            return "问题件";
        default:
            return "";
        }
    }

    private static class ItemHolder extends ItemViewHolder<Traces> {
        private ImageView ivIcon;
        private View vTop;
        private View vBottom;
        private TextView tvStation;
        private TextView tvTime;

        @Override
        protected void initComponent() {
            ivIcon = (ImageView) super.findViewById(R.id.iv_icon);
            vTop = (View) super.findViewById(R.id.v_top);
            vBottom = (View) super.findViewById(R.id.v_bottom);
            tvStation = (TextView) super.findViewById(R.id.tv_station);
            tvTime = (TextView) super.findViewById(R.id.tv_time);
        }


        public ItemHolder(Context mContext,ListView lv) {
            super(mContext,lv, R.layout.listitem_logistics);
        }

        @Override
        public ItemViewHolder<Traces> buildNewSelf(Context context) {
            return new ItemHolder(context,null);
        }

        @Override
        public void display(int i, Traces item) {
            vTop.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
            vBottom.setVisibility(i == getList().size() ? View.INVISIBLE : View.VISIBLE);
            int color = getContext().getResources().getColor(i == 0 ? R.color.fontYellow : R.color.gray);
            vTop.setBackgroundColor(color);
            vBottom.setBackgroundColor(color);
            tvStation.setTextColor(color);
            tvTime.setTextColor(color);
            ivIcon.setSelected(i==0);

            tvStation.setText(item.getAcceptStation());
            tvTime.setText(item.getAcceptTime());
        }
    }

    public static class Major extends Agent.Major {
        public Major(Class<? extends LogisticsActivity> clazz) {
            super(clazz);
        }

        public void startForBean(Context src, Logistics bean) {
            buildParams(src, bean).startActivity();
        }

        public Logistics parseParams(Activity target) {
            return parseParam(target, Logistics.class);
        }
    }


}
