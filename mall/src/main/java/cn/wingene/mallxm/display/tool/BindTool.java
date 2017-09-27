package cn.wingene.mallxm.display.tool;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limecn.ghmall.R;

import junze.android.util.TextViewUtil;

import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.data.IProductItem;

/**
 * Created by Wingene on 2017/9/9.
 */

public class BindTool {
    /**
     * @param item
     * @param itemView
     * @param ivImg
     * @param tvName
     * @param tvDesc        support null
     * @param llytMarkGroup
     * @param tvMark1       support null
     * @param tvMark2       support null
     * @param tvPrice
     * @param tvDeductible
     */
    public static void bindProducItemView(final IProductItem item, final View itemView, SimpleDraweeView ivImg,
            TextView tvName, TextView tvDesc, LinearLayout llytMarkGroup, TextView tvMark1, TextView tvMark2,
            TextView tvPrice, TextView tvDeductible) {
        ivImg.setImageURI(item.getProductImage());

        tvName.setText(item.getProductName());
        if (tvDesc != null) {
            TextViewUtil.showOrGone(tvDesc, item.getSellingPoint());
        }
        if (tvMark1 != null) {
            tvMark1.setVisibility(View.GONE);
        }
        if (tvMark2 != null) {
            tvMark2.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(item.getTag())) {
            llytMarkGroup.setVisibility(View.VISIBLE);
            llytMarkGroup.removeAllViews();
            int index = 0;
            for (String string : item.getTag().split(",")) {
                TextView textView = (TextView) LayoutInflater.from(llytMarkGroup.getContext()).inflate(index++ == 0
                        ? R.layout.productmark_layout_first : R.layout.productmark_layout, llytMarkGroup, false);
                textView.setText(string);
                llytMarkGroup.addView(textView);
            }
        } else {
            llytMarkGroup.setVisibility(View.GONE);
        }

        tvPrice.setText("¥" + String.valueOf(item.getProductPrice()));
        if (!TextUtils.isEmpty(item.getAcceptIntegral())) {
            tvDeductible.setVisibility(View.VISIBLE);
            tvDeductible.setText("可抵金币¥" + String.valueOf(item.getAcceptIntegral()));
        } else {
            tvDeductible.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startCommodityDetailActivity(itemView.getContext(), item.getProductId());
            }
        });
    }
}
