package cn.wingene.mallxf.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 王传强 on 2017/3/4.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mHeight = 10;
    private int top = 0;
    private int left = 0;
    private int right = 0;
    private int bottom = 0;
    private Paint mPaint;

    public SpaceItemDecoration(int top, int left, int right, int bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        parent.setPadding(0, 0, 0, 0);
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childV = parent.getChildAt(i);
            int vleft = childV.getLeft()-left;
            int vtop = childV.getTop();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        parent.setPadding(0, 0, 0, 0);
        outRect.top = this.top;
        outRect.left = this.left;
        outRect.right = this.right;
        outRect.bottom = this.bottom;

//        int position = parent.getChildAdapterPosition(view);
//        if (position != 0) {
//            outRect.top = mHeight;
//
//        }

    }
}