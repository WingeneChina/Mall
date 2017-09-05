package cn.wingene.mallx.frame.holder;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import junze.java.net.IHttpCacheElement.ICacheRequest;
import junze.java.net.IHttpElement.IResponse;
import junze.java.util.DateFormater;

import junze.widget.PullToRefreshListView;

import junze.android.ui.ViewHolder;
import junze.androidxf.http.BaseParamsRequest;
import junze.androidxf.kit.AKit;
import junze.androidxf.manager.FormManager;
import junze.androidxf.manager.FormManager.DBForm;

public class PullListViewHolder extends ViewHolder {
    private static final String TAG = "PullListViewHolder";

    public interface PageAble {
        public abstract void loadNextPageInBack(int nextPosition);

        public abstract void loadFirstPageInBack();

    }

    private int pageIndex = 1;
    private boolean loadPageSuccess = false;
    private boolean haveNext = false;
    private String exceptionMsg;

    // 线程池
    private ExecutorService pool;
    private final Handler mHandler = new Handler();
    private boolean isLoading = false;
    private int rid = -1;
    private PullToRefreshListView lvContent;
    private View vFooter;
    private boolean hadAdapter = false;
    private boolean isDestoried = false;

    private TextView tvFooter;
    private ProgressBar pbarFooter;
    private final PageAble mPageAble;

    public PullListViewHolder(PullToRefreshListView view, PageAble pageAble) {
        super(view);
        lvContent = view;
        this.mPageAble = pageAble;
        initPullView();
    }

    public PullListViewHolder(View mView, int rid, PageAble pageAble) {
        super(mView);
        this.mPageAble = pageAble;
        this.rid = rid;
        initPullView();
    }

    public PullListViewHolder(Context mContext, int layoutId, int rid, PageAble pageAble) {
        super(mContext, layoutId);
        this.mPageAble = pageAble;
        this.rid = rid;
        initPullView();
    }

    public synchronized boolean isLoading() {
        return isLoading;
    }

    public synchronized void loadCompleteSuccess() {
        loadComplete("最近更新：" + DateFormater.getDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * @param lastRefreshTime if null then gone
     */
    public synchronized void loadComplete(String lastRefreshTime) {
        if (lastRefreshTime != null) {
            lvContent.onRefreshComplete(lastRefreshTime);
        } else {
            lvContent.onRefreshComplete();
        }
        isLoading = false;
    }

    public void loadFirstPage() {
        if (hadAdapter) {
            lvContent.clickRefresh();
        } else {
            new RuntimeException("please set adapter for " + TAG);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener l) {
        lvContent.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int max = lvContent.getAdapter().getCount() - lvContent.getHeaderViewsCount()
                        - lvContent.getFooterViewsCount();
                position = position - 1;
                if (0 <= position && position < max) {
                    l.onItemClick(parent, view, position, id);
                }

            }
        });
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener l) {
        lvContent.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int max = lvContent.getAdapter().getCount() - lvContent.getHeaderViewsCount()
                        - lvContent.getFooterViewsCount();
                position = position - 1;
                if (0 <= position && position < max) {
                    l.onItemLongClick(parent, view, position, id);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setAdapter(ListAdapter adapter) {
        hadAdapter = true;
        lvContent.setAdapter(adapter);
    }

    /**
     * {@link #getLoadingPageIndex()}
     * @param askRun
     */
    public void loadNextPageIfNeed(Runnable askRun) {
        if (isHaveNext()) {
            if (isLoadPageSuccess()) {
                // pageIndex加载成功了，但pageIndex+1还没有加载成功
                setLoadingPageIndex(getLoadingPageIndex() + 1);
                setLoadPageSuccess(false);
                AKit.e("加载第" + getLoadingPageIndex() + "页");
            } else {
                AKit.e("重新加载第" + getLoadingPageIndex() + "页");
            }
            askRun.run();
            // askItems(getPageIndex());
        } else {
            post(new Runnable() {

                @Override
                public void run() {
                    loadCompleteSuccess();
                    // String msg = "－－已加载全部－－";
                    //                    String msg = null;
                    String msg = "已加载全部";
                    AKit.e(msg);
                    setTvFooter(msg, false);
                }
            });
        }
    }

    public void initPullView() {
        pool = Executors.newFixedThreadPool(2);
        if (rid != -1) {
            lvContent = (PullToRefreshListView) super.findViewById(rid);
        }
        vFooter = LayoutInflater.from(mContext).inflate(junze.android.R.layout.pull_listview_footer, null);
        tvFooter = (TextView) vFooter.findViewById(junze.android.R.id.listview_foot_more);
        pbarFooter = (ProgressBar) vFooter.findViewById(junze.android.R.id.listview_foot_progress);
        lvContent.addFooterView(vFooter);
        lvContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, int scrollState) {
                lvContent.onScrollStateChanged(view, scrollState);
                // 判断是否滚动到底部
                final int lastPostion = view.getLastVisiblePosition();
                if (lastPostion == view.getCount() - 1 && !isLoading()) {
                    // 滚动到底部处理
                    // int lvDataState = STATUS;
                    isLoading = true;
                    setTvFooter("加载中...", true);
                    pbarFooter.setVisibility(View.VISIBLE);
                    pool.execute(new Runnable() {
                        @Override
                        public void run() {
                            mPageAble.loadNextPageInBack(lastPostion);
                        }
                    });
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lvContent.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        lvContent.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading()) {
                    return;
                }
                // 编辑模式无需执行下拉
                isLoading = true;
                hideFooter();
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mPageAble.loadFirstPageInBack();
                    }
                });

            }
        });
    }

    public void setLoadPageInfo(boolean isLoadPageSuccess, boolean haveNext) {
        this.haveNext = haveNext;
        this.loadPageSuccess = isLoadPageSuccess;
        if (isLoadPageSuccess) {
            exceptionMsg = null;
        }
    }

    public int getLoadingPageIndex() {
        return pageIndex;
    }

    public void setLoadingPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public boolean isLoadPageSuccess() {
        return loadPageSuccess;
    }

    public void setLoadPageSuccess(boolean loadPageSuccess) {
        this.loadPageSuccess = loadPageSuccess;
    }

    public boolean isHaveNext() {
        return haveNext || exceptionMsg != null;
    }

    public void setHaveNext(boolean haveNext) {
        this.haveNext = haveNext;
    }

    @Override
    protected void initComponent() {

    }

    public int getPullListViewId() {
        return junze.android.R.id.lvContent;
    }

    public void setTvFooter(final String info, final boolean isLoading) {
        if (tvFooter != null) {
            tvFooter.setVisibility(!info.contains("全部") ? View.VISIBLE : View.INVISIBLE);
            tvFooter.setText("" + info);
            tvFooter.postInvalidate();
        }
        if (pbarFooter != null) {
            if (isLoading) {
                pbarFooter.setVisibility(View.VISIBLE);
            } else {
                pbarFooter.setVisibility(View.GONE);
            }
        }
    }

    public void hideFooter() {
        tvFooter.setVisibility(View.INVISIBLE);
        pbarFooter.setVisibility(View.INVISIBLE);
    }

    public void onDestory() {
        isDestoried = true;
        pool.shutdown();
    }

    public void post(final Runnable runnable) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (!isDestoried) {
                    runnable.run();
                }
            }
        });
    }

    public <T extends IResponse> void askInBack(final BaseParamsRequest<T> request) {
        askInBack(request, true);
    }

    public <T extends IResponse> void askInBack(final BaseParamsRequest<T> request, final boolean autoHandleException) {
        final T response = request.request();
        post(new Runnable() {
            @Override
            public void run() {

                if (response != null) {
                    request.updateUI(response);
                } else {
                    if (autoHandleException) {
                        String exceptionMsg = AKit.getFriendExceptionMessage(mContext, request.getException());
                        loadComplete("上次更新失败：" + exceptionMsg);
                        setTvFooter(exceptionMsg, false);
                    } else {
                        request.updateUIWhenException();
                    }
                }
                request.updateFinally();
            }
        });
    }

    public <T extends IResponse> void cacheAskInBack(final ICacheRequest<T> request) {
        cacheAskInBack(request, true);
    }

    public <T extends IResponse> void cacheAskInBack(final ICacheRequest<T> request, final boolean autoHandleException) {
        final FormManager formManager = new FormManager(mContext);
        final String type = request.getClass().getName();
        final String sign = request.getSign();
        if (sign != null) { // 为null，则不使用缓存
            try {
                final DBForm dbForm = formManager.findForm(type, sign);
                if (dbForm != null) {
                    request.initResponse(dbForm.content);
                    post(new Runnable() {

                        @Override
                        public void run() {
                            request.showCacheResponse(dbForm.saveTime);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final T response = request.request();
        post(new Runnable() {
            @Override
            public void run() {
                if (response != null) {
                    if (request.needSaveCache() && sign != null) {
                        try {
                            formManager.saveForm(type, sign, response.getResponseStr());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    request.updateUI(response);
                } else {
                    if (autoHandleException) {
                        exceptionMsg = AKit.getFriendExceptionMessage(mContext, request.getException());
                        loadComplete("上次更新失败：" + exceptionMsg);
                        setTvFooter(exceptionMsg, false);
                    } else {
                        request.updateUIWhenException();
                    }
                }
                request.updateFinally();
            }
        });
    }
}
