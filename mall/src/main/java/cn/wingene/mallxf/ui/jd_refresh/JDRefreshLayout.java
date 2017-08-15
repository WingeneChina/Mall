package cn.wingene.mallxf.ui.jd_refresh;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * 京东下拉刷新的layout
 * Created  on 2016/10/24.
 */

public class JDRefreshLayout extends RefreshLayout {
    public JDRefreshLayout(Context context) {
        super(context);
    }

    public JDRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        JdHeader header = new JdHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }

    @Override
    public boolean getPullDownIntercept() {
        boolean intercept = false;
        View child = getFirstVisiableChild();
        if (child == null) {
            intercept = false;
        } else if (child instanceof NestedScrollView) {
            intercept = svPullDownIntercept(child);
        } else {
            return super.getPullDownIntercept();
        }
        return intercept;
    }

    /**
     * 获取第一个可见的子view
     *
     * @return
     */
    private View getFirstVisiableChild() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            } else {
                return child;
            }
        }
        return null;
    }

    @Override
    public boolean getPullUpIntercept() {
        boolean intercept = false;
        View child = getLastVisiableChild();
        if (child == null) {
            intercept = false;
        } else if (child instanceof NestedScrollView) {
            intercept = svPullUpIntercept(child);
        } else {
            return super.getPullUpIntercept();
        }
        return intercept;
    }

    /**
     * 获取ScrollView是否滚动到底部
     *
     * @param child
     * @return
     */
    public boolean svPullUpIntercept(View child) {
        boolean intercept = false;
        if (child instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) child;
            View scrollChild = scrollView.getChildAt(0);
            if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
                intercept = true;
            }

        }else{
            NestedScrollView scrollView = (NestedScrollView) child;
            View scrollChild = scrollView.getChildAt(0);
            if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
                intercept = true;
            }
        }

        return intercept;
    }

    /**
     * 获取最后一个可见的子view
     *
     * @return
     */
    private View getLastVisiableChild() {
        for (int i = lastChildIndex; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            } else {
                return child;
            }
        }
        return null;
    }
}
