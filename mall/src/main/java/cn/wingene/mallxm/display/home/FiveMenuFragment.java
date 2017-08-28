package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.LoginModel;

/**
 * Created by wangcq on 2017/8/7.
 * 个人菜单页面
 */

public class FiveMenuFragment extends MyBaseFragment implements View.OnClickListener {

    private ImageView settingV;
    private SimpleDraweeView personHeadV;
    private TextView personNameV;
    private TextView yingMoneyV;
    private TextView youMoneyV;
    private LinearLayout findOrderV;
    private TextView orderReadyPayV;
    private TextView orderReadySendV;
    private TextView orderAlreadySendV;
    private TextView orderCompletedV;
    private TextView mallMoneyV;
    private TextView customerV;
    private TextView updateAddressV;
    private TextView luckyDesV;
    private TextView oneLuckyDesV;
    private TextView versonInfoV;
    private TextView aboutAsV;

    public static FiveMenuFragment newInstance(Bundle args) {
        FiveMenuFragment fiveMenuFragment = new FiveMenuFragment();
        fiveMenuFragment.setArguments(args);

        return fiveMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_menu_layout, container, false);
//        initViews(view);
        initClickViews(view);
        initEvent();

        return view;
    }

    private void initViews(View root) {
        settingV = (ImageView) root.findViewById(R.id.settingV);
        personHeadV = (SimpleDraweeView) root.findViewById(R.id.personHeadV);
        personNameV = (TextView) root.findViewById(R.id.personNameV);
        yingMoneyV = (TextView) root.findViewById(R.id.yingMoneyV);
        youMoneyV = (TextView) root.findViewById(R.id.youMoneyV);

    }

    private void initClickViews(View root) {
        settingV = (ImageView) root.findViewById(R.id.settingV);
        personHeadV = (SimpleDraweeView) root.findViewById(R.id.personHeadV);
        personNameV = (TextView) root.findViewById(R.id.personNameV);
        yingMoneyV = (TextView) root.findViewById(R.id.yingMoneyV);
        youMoneyV = (TextView) root.findViewById(R.id.youMoneyV);
        findOrderV = (LinearLayout) root.findViewById(R.id.findOrderV);
        orderReadyPayV = (TextView) root.findViewById(R.id.orderReadyPayV);
        orderReadySendV = (TextView) root.findViewById(R.id.orderReadySendV);
        orderAlreadySendV = (TextView) root.findViewById(R.id.orderAlreadySendV);
        orderCompletedV = (TextView) root.findViewById(R.id.orderCompletedV);
        mallMoneyV = (TextView) root.findViewById(R.id.mallMoneyV);
        customerV = (TextView) root.findViewById(R.id.customerV);
        updateAddressV = (TextView) root.findViewById(R.id.updateAddressV);
        luckyDesV = (TextView) root.findViewById(R.id.luckyDesV);
        oneLuckyDesV = (TextView) root.findViewById(R.id.oneLuckyDesV);
        versonInfoV = (TextView) root.findViewById(R.id.versonInfoV);
        aboutAsV = (TextView) root.findViewById(R.id.aboutAsV);
    }

    private void initEvent() {
        personHeadV.setOnClickListener(this);
        settingV.setOnClickListener(this);
        findOrderV.setOnClickListener(this);
        orderReadyPayV.setOnClickListener(this);
        orderReadySendV.setOnClickListener(this);
        orderAlreadySendV.setOnClickListener(this);
        orderCompletedV.setOnClickListener(this);
        updateAddressV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personHeadV:
                Log.e(this.getClass().getName(), "点击头像");
                JumpHelper.startLoginActivity(this.getActivity());

                break;
            case R.id.settingV:
                Log.e(this.getClass().getName(), "设置");
//                JumpHelper.startOrderListActivity(this.getActivity(), -1);
                break;
            case R.id.findOrderV:
                JumpHelper.startOrderListActivity(getActivity(),-1);
                break;
            case R.id.orderReadyPayV:
                JumpHelper.startOrderListActivity(getActivity(),0);

                break;
            case R.id.orderReadySendV:
                JumpHelper.startOrderListActivity(getActivity(),1);


                break;
            case R.id.orderAlreadySendV:
                JumpHelper.startOrderListActivity(getActivity(),3);


                break;
            case R.id.orderCompletedV:
                JumpHelper.startOrderListActivity(getActivity(),4);



                break;
            case R.id.updateAddressV:
                JumpHelper.startAddressManagerActivity(getActivity());
                break;
            default:

        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            try {
                String userInfoJson = UserData.getUserInfo();
                GsonUtil<LoginModel> gsonUtil = new GsonUtil<>(LoginModel.class);
                LoginModel loginModel = gsonUtil.fromJson(userInfoJson);
                if (loginModel != null && loginModel.getData() != null) {
                    personHeadV.setImageURI(loginModel.getData().getAvatar());
                    personNameV.setText(loginModel.getData().getNickname());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
