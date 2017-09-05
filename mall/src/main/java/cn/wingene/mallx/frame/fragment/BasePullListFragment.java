package cn.wingene.mallx.frame.fragment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;

import junze.java.manager.ObserverManager;
import junze.java.manager.ObserverManager.Observer;
import junze.java.net.IHttpCacheElement.ICacheRequest;
import junze.java.net.IHttpElement.IResponse;

import junze.widget.PullToRefreshListView;

import junze.android.ui.ItemViewHolder;
import junze.androidxf.http.BaseParamsRequest;
import junze.androidxf.ui.holder.PullListViewHolder;
import junze.androidxf.ui.holder.PullListViewHolder.PageAble;

import cn.wingene.mall.R;
import cn.wingene.mallxf.MyAgent;
import cn.wingene.mallxf.ui.MyBaseFragment;


/**
 * 一个普通的可下拉刷新的 listView组成的 fragment
 * @author Wingene
 *
 */
public abstract class BasePullListFragment extends MyBaseFragment implements PageAble, Observer, OnItemClickListener, OnItemLongClickListener {
    public final static String ACTION_NEED_UPDATE = "BasePullListFragment.ACTION_NEED_UPDATE";
    private boolean mNeedLoadFirstPage = false;
    private PullListViewHolder mListViewHolder;
    private PullToRefreshListView mListView;
    private boolean schemeInited;
    public static final int PAGE_SIZE = 20;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pull_list, null);
        mListViewHolder = new PullListViewHolder(v, R.id.lvContent, this);
        mListView = (PullToRefreshListView) v.findViewById(R.id.lvContent);
        ObserverManager.getInstance().registerObserver(this);
        return v;
    }

    public final boolean needLoadFirstPage() {
        return mNeedLoadFirstPage && getScheme().haveInit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needLoadFirstPage()) {
            mNeedLoadFirstPage = false;
            loadFirstPage();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && needLoadFirstPage()) {
            mNeedLoadFirstPage = false;
            loadFirstPage();
        }
    }

    @Override
    public void onDestroy() {
        if(mListViewHolder != null){
            mListViewHolder.onDestory();
        }
        super.onDestroy();
    }

    public void loadFirstPage() {
        mListViewHolder.loadFirstPage();
    }

    protected BaseSchemeOption buildSchemeOption(Bundle bundle) {
        BaseSchemeOption option = new BaseSchemeOption();
        option.bundle = bundle;
        return option;
    }

    @Override
    protected void onHandleBundle(Bundle bundle, boolean isOnViewCreated) {
        super.onHandleBundle(bundle, isOnViewCreated);
        if (!schemeInited) {
            getScheme().onInit(buildSchemeOption(bundle));
            getListViewHolder().setAdapter(getScheme().getAdapter());
            getListViewHolder().loadFirstPage();
            getListViewHolder().setOnItemClickListener(this);
            getListViewHolder().setOnItemLongClickListener(this);
            schemeInited = true;
        } else {
            getScheme().onUpdate(buildSchemeOption(bundle));
        }
    }

    @Override
    public void onDestroyView() {
        ObserverManager.getInstance().unregisterObserver(this);
        super.onDestroyView();
    }

    @Override
    public void loadNextPageInBack(final int nextPosition) {
        mListViewHolder.loadNextPageIfNeed(new Runnable() {

            @Override
            public void run() {
                askItem(mListViewHolder.getLoadingPageIndex());
            }

        });
    }

    @Override
    public void loadFirstPageInBack() {
        mListViewHolder.setLoadingPageIndex(1);
        askItem(1);
    }

    public <T extends IResponse> void askInBack(BaseParamsRequest<T> request) {
        mListViewHolder.askInBack(request);
    }

    public <T extends IResponse> void askInBack(BaseParamsRequest<T> request, boolean autoHandleException) {
        mListViewHolder.askInBack(request, autoHandleException);
    }

    public <T extends IResponse> void cacheAskInBack(ICacheRequest<T> request) {
        mListViewHolder.cacheAskInBack(request);
    }

    public <T extends IResponse> void cacheAskInBack(ICacheRequest<T> request, boolean autoHandleException) {
        mListViewHolder.cacheAskInBack(request, autoHandleException);
    }

    @Override
    public void updateForObserver(String filter, Map<String, Object> map) {
        if (ACTION_NEED_UPDATE.equals(filter)) {
            mNeedLoadFirstPage = true;
        }
    }

    public void setNeedLoadFirstPage(boolean isNeed) {
        this.mNeedLoadFirstPage = isNeed;
    }

    public PullListViewHolder getListViewHolder() {
        return mListViewHolder;
    }

    protected abstract Scheme<?, ?> getScheme();

    protected abstract void askItem(final int pageIndex);

    public PullToRefreshListView getListView() {
        return mListView;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public static class BaseSchemeOption {
        public Bundle bundle;
    }

    public static abstract class Scheme<T, B> {
        public final static String PARAMS_TYPE = "PARAMS_TYPE";
        private final BasePullListFragment mFragment;
        private T mType;
        private ItemViewHolder<B> mItemViewHolder;

        public Scheme(BasePullListFragment fragmetn) {
            this.mFragment = fragmetn;
        }

        /**
         * 取得数据后，调用{@link #peformatInit(T, ItemViewHolder)} 进行初始化;
         * @param option
         */
        public abstract void onInit(BaseSchemeOption option);

        public void onUpdate(BaseSchemeOption option) {

        }

        /**
         * @param type support null
         * @param itemViewHolder
         */
        public void peformatInit(T type, ItemViewHolder<B> itemViewHolder) {
            this.mType = type;
            this.mItemViewHolder = itemViewHolder;
        }

        public boolean haveInit() {
            return mItemViewHolder != null;
        }

        public B getItem(int position) {
            return mItemViewHolder.getItem(position);
        }

        public List<B> getItems() {
            return mItemViewHolder.getList();
        }

        /**
         * 
         * @return possible null 未指定类型时候，默认也就是全部
         */
        public T getType() {
            return mType;
        }

        public PullListViewHolder getListViewHolder() {
            return mFragment.getListViewHolder();
        }

        public void loadCompleteSuccess() {
            getListViewHolder().loadCompleteSuccess();
        }

        public ItemViewHolder<B> getItemViewHolder() {
            return mItemViewHolder;
        }

        public MyAgent getAgent() {
            return mFragment.agent();
        }

        public final FragmentActivity getActivity() {
            return mFragment.getActivity();
        }

        public ListAdapter getAdapter() {
            return mItemViewHolder.getAdapter();
        }

        public void schemeUpdateUI(final int pageIndex, List<B> list) {
            if (pageIndex == 1) {
                getItemViewHolder().clear();
            }
            getItemViewHolder().addAll(list);
            getItemViewHolder().notifyDataSetChanged();
            getListViewHolder().loadCompleteSuccess();
            boolean loadAll = list.size() < PAGE_SIZE;
            if (loadAll) {
                getListViewHolder().setTvFooter("已加载全部", false);
            }
            getListViewHolder().setLoadPageInfo(true, !loadAll);
        }

        public void schemeUpdateUIByCache(final int pageIndex, List<B> list, Date saveTime) {
            if (pageIndex == 1) {
                getItemViewHolder().clear();
                getItemViewHolder().addAll(list);
                getItemViewHolder().notifyDataSetChanged();
            }
        }

        public void runOnUiThread(Runnable action) {
            mFragment.runOnUiThread(action);
        }

        public void loadFirstPage() {
            getListViewHolder().loadFirstPage();
        }

        public <T extends IResponse> void cacheAskInBack(ICacheRequest<T> request) {
            mFragment.cacheAskInBack(request);
        }

    }

}
