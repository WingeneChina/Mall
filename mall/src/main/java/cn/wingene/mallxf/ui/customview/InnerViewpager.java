package cn.wingene.mallxf.ui.customview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InnerViewpager extends ViewPager {
    private PointF downP;
    private PointF currP;

    public InnerViewpager(Context context) {
        super(context);
        init();
    }

    public InnerViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        downP = new PointF();
        currP = new PointF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currP.x = event.getX();
        currP.y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downP.x = event.getX();
                downP.y = event.getY();
                if (getChildCount() <= 1) {
                    return super.onTouchEvent(event);
                }

                if (this.getChildCount() > 1) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (this.getChildCount() > 1) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 归还滑动事件
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height) {
//                height = h;
//            }
//        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
