package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import cn.wingene.mall.R;

/**
 * Created by wangcq on 2017/8/11.
 */

public class DaySpecialPriceAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_person_recommend_item_layout,
                parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("", "click !");
//                ObjectAnimator scale_X_objectAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.2f);
//                scale_X_objectAnimator.setDuration(100);
//                scale_X_objectAnimator.setInterpolator(new AccelerateInterpolator());
//
//                ObjectAnimator scale_Y_objectAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.2f);
//                scale_Y_objectAnimator.setDuration(100);
//                scale_Y_objectAnimator.setInterpolator(new AccelerateInterpolator());
//
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "translationZ", 0, 30);
//                objectAnimator.setDuration(100);
//                objectAnimator.setInterpolator(new AccelerateInterpolator());
//                objectAnimator.start();
//
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(objectAnimator).with(scale_X_objectAnimator).with(scale_Y_objectAnimator);
            }
        });
        return new DaySpecialPrice(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class DaySpecialPrice extends RecyclerView.ViewHolder {

        public DaySpecialPrice(View itemView) {
            super(itemView);
        }
    }
}
