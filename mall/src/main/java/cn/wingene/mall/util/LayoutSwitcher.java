package cn.wingene.mall.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Wingene on 2017/9/10.
 */

public class LayoutSwitcher {
    View mNormal;
    View mOther;

    public LayoutSwitcher(View normal) {
       this.mNormal = normal;
    }

    public LayoutSwitcher(Activity activity,int ridNormal) {
       this.mNormal = activity.findViewById(ridNormal);
    }
    public LayoutSwitcher(View parent,int ridNormal) {
       this.mNormal = parent.findViewById(ridNormal);
    }

    public void switchOther(View v) {
        if (mNormal instanceof RelativeLayout || mNormal instanceof FrameLayout) {
            if (mOther != null) {
                ((ViewGroup) mNormal).removeView(mOther);
            }
            ((ViewGroup) mNormal).addView(v);
            mOther = v;
        } else if (mNormal.getParent() != null && mNormal.getParent() instanceof LinearLayout) {
                LinearLayout parent = (LinearLayout) mNormal.getParent();
                mNormal.setVisibility(View.GONE);
                if (mOther != null && mOther.getParent() == parent) {
                    parent.removeView(mOther);
                }
                parent.addView(v, parent.indexOfChild(mNormal), mNormal.getLayoutParams());
                v.setVisibility(View.VISIBLE);
                mOther = v;
        }else {
            throw new RuntimeException("normal is not RelativeLayout or FragmeLayout and normal's parent is not " +
                    "LinearLayout");
        }
    }

    public void switchNormal() {
        if (mNormal instanceof RelativeLayout || mNormal instanceof FrameLayout) {
            if (mOther != null) {
                ((ViewGroup) mNormal).removeView(mOther);
                mOther = null;
            }
        } else if (mNormal.getParent() != null && mNormal.getParent() instanceof LinearLayout) {
                LinearLayout parent = (LinearLayout) mNormal.getParent();
                mNormal.setVisibility(View.VISIBLE);
                if (mOther != null && mOther.getParent() == parent) {
                    parent.removeView(mOther);
                    mOther = null;
                }
        }else{
            throw new RuntimeException("normal is not RelativeLayout or FragmeLayout and normal's parent is not " +
                    "LinearLayout");
        }
    }

}
