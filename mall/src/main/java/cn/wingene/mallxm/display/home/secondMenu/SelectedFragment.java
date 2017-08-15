package cn.wingene.mallxm.display.home.secondMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.secondMenu.adapter.SelectItemAdapter;

/**
 * Created by wangcq on 2017/8/14.
 * 精选
 */

public class SelectedFragment extends MyBaseFragment {

    private RecyclerView selectRecyclerV;
    private SelectItemAdapter mSelectItemAdapter;

    public static SelectedFragment newInstance(Bundle bundle) {
        SelectedFragment selectedFragment = new SelectedFragment();
        selectedFragment.setArguments(bundle);

        return selectedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View root) {
        selectRecyclerV = (RecyclerView) root.findViewById(R.id.selectRecyclerV);
        initRecyclerV();
    }

    private void initRecyclerV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        selectRecyclerV.setLayoutManager(linearLayoutManager);

        mSelectItemAdapter = new SelectItemAdapter();
        selectRecyclerV.setAdapter(mSelectItemAdapter);
    }
}
