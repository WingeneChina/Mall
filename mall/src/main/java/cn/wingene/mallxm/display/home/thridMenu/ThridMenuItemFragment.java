package cn.wingene.mallxm.display.home.thridMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.ThirdMenuFragment;
import cn.wingene.mallxm.display.home.secondMenu.adapter.SelectItemAdapter;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemContentModel;
import cn.wingene.mallxm.display.home.thridMenu.adapter.ThirdMenuItemAdatper;
import junze.androidx.baidu.LocationHelper;

import static cn.wingene.mallxm.display.home.SecondMenuFragment.MENU_CODE_ARG;

/**
 * Created by wangcq on 2017/8/27.
 */

public class ThridMenuItemFragment extends MyBaseFragment implements HttpListener<String> {
    private RecyclerView selectRecyclerV;
    private ThirdMenuItemAdatper mSelectItemAdapter;

    private int mOrderBy = 0;
    private int mPagerIndex = 1;//分页索引

    public static ThridMenuItemFragment newInstance(Bundle bundle) {
        ThridMenuItemFragment thirdMenuItemFragment = new ThridMenuItemFragment();
        thirdMenuItemFragment.setArguments(bundle);

        return thirdMenuItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_layout, container, false);
        initViews(view);
        requestData();
        return view;
    }

    private void initViews(View root) {
        selectRecyclerV = (RecyclerView) root.findViewById(R.id.selectRecyclerV);
    }

    private void initRecyclerV(MenuItemContentModel menuItemContentModel) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        selectRecyclerV.setLayoutManager(linearLayoutManager);
        mSelectItemAdapter = new ThirdMenuItemAdatper(menuItemContentModel.getData().getList());
        selectRecyclerV.setAdapter(mSelectItemAdapter);
    }

    private void requestData() {
        if (getArguments() != null) {
            BDLocation location = LocationHelper.getInstance().getLocation();

            NoHttpRequest<MenuItemContentModel> noHttpRequest = new NoHttpRequest<>(MenuItemContentModel.class);
            HashMap<String, Object> hashMapParams = new HashMap<>();
            hashMapParams.put("CategoryCode", getArguments().getString(MENU_CODE_ARG));
            hashMapParams.put("OrderBy", mOrderBy);
            hashMapParams.put("PageIndex", mPagerIndex);
            hashMapParams.put("Lat", location.getLatitude());
            hashMapParams.put("Lng", location.getLongitude());
            noHttpRequest.request(getActivity(), HttpConstant.NEARBY_LIST, hashMapParams, 1, this, false,
                    "thirdMenuList", false, true);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<MenuItemContentModel> gsonUtil = new GsonUtil<>(MenuItemContentModel.class);
            MenuItemContentModel menuItemContentModel = gsonUtil.fromJson(response.get());
            initRecyclerV(menuItemContentModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
