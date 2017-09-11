package cn.wingene.mallxf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;

import junze.java.net.IHttpElement.IRequest;
import junze.java.net.IHttpElement.IResponse;
import junze.java.util.CheckUtil;
import junze.java.util.StringUtil;

import junze.android.ui.ViewHolder;
import junze.androidx.baidu.LocationHelper;
import junze.androidx.baidu.OnReceiveLoactionListener;
import junze.androidxf.core.Agent;
import junze.androidxf.http.BaseRequest.NotOKException;

import cn.wingene.mall.R;
import cn.wingene.mall.util.LayoutSwitcher;
import cn.wingene.mallx.frame.ui.EditViewDialogDeclare.EditViewDialog;
import cn.wingene.mallx.frame.ui.EditViewDialogDeclare.OnEditCompleteListener;
import cn.wingene.mallx.frame.ui.EditViewDialogDeclare.Option;
import cn.wingene.mallxf.http.Ask.NeedLoginException;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.AddressManagerActivity;
import cn.wingene.mallxm.purchase.OrderListActivity;
import cn.wingene.mallxm.purchase.RechargeIndexActivity;
import cn.wingene.mallxm.purchase.ShoppingCartActivity;

/**
 * Created by Wingene on 2017/8/26.
 */

public class MyAgent extends Agent {
    private EditViewDialog mEditViewDialog;
    LayoutSwitcher mLayoutSwitcher;
    public Map<String, ViewHolder> mLayoutMap = new HashMap<>();

    public MyAgent(Activity activity) {
        super(activity);
    }

    public <T extends IResponse> AsyncTask<String, Integer, T> ask(final CharSequence msg, final boolean
            autoHandleException, final IRequest<T> request) {
        return new AsyncTask<String, Integer, T>() {

            @Override
            protected void onPreExecute() {
                showWaitDialog(msg);
            }

            @Override
            protected T doInBackground(String... params) {
                T response = request.request();
                return response;
            }

            @Override
            protected void onPostExecute(T response) {
                if (response != null) {
                    request.updateUI(response);
                } else {
                    if (autoHandleException) {
                        handleException(request.getException());
                    } else {
                        request.updateUIWhenException();
                    }
                }
                request.updateFinally();
                cancelWaitDialog();
            }

        }.execute("");
    }

    private <T extends IResponse> void handleException(Exception exception) {
        if (exception != null && exception instanceof NeedLoginException) {
            if (CheckUtil.isInclude(getActivity().getClass(), ShoppingCartActivity.class, OrderListActivity.class,
                    AddressManagerActivity.class, RechargeIndexActivity.class)) {
                getActivity().finish();
            }
            JumpHelper.startLoginActivity(getActivity());
            return;
        }
        if (exception != null && exception instanceof NotOKException) {
            NotOKException e = (NotOKException) exception;
            if (e.responseCode == 400) {
                showToast("网络不稳定!!!!");
                return;
            }
        }
        showToast(exception);
    }


    public void showEditViewDialog2(CharSequence title, Option option, OnEditCompleteListener onEditComplete) {
        if(this.mEditViewDialog == null) {
            EditText et = new EditText(getActivity());
            et.setBackgroundResource(R.drawable.shape_stroke_darkgray_buttom);
            mEditViewDialog = new EditViewDialog(getActivity());
        }
        this.mEditViewDialog.setParams(title, onEditComplete, option);
        this.showDialog(this.mEditViewDialog.getDialog());
    }

    public void initLayoutSwitch(LayoutSwitcher layoutSwitcher) {
        this.mLayoutSwitcher = layoutSwitcher;
    }

    public void switchLayoutNormal() {
        if (mLayoutSwitcher == null) {
            return;
        }
        mLayoutSwitcher.switchNormal();
    }

    /**
     * @param clazz
     * @param <T>
     * @return possible null
     */
    public <T extends ViewHolder> T switchLayoutOther(Class<T> clazz) {
        return switchLayoutOther(null, clazz);
    }

    /**
     * @param key
     * @param clazz
     * @param <T>
     * @return possible null
     */
    public <T extends ViewHolder> T switchLayoutOther(String key, Class<T> clazz) {
        if (mLayoutSwitcher == null) {
            return null;
        }
        try {
            key = key != null ? key : "";
            String mapKey = StringUtil.spellBy(new String[]{key, clazz.getName()}, "#");
            T holder = (T) mLayoutMap.get(mapKey);
            if (holder != null) {
                return holder;
            }
            Constructor<T> constructor = clazz.getConstructor(Context.class);
            holder = constructor.newInstance(getActivity());
            mLayoutSwitcher.switchOther(holder.getView());
            return holder;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 启动百度地图Poi周边检索
     */
    public void startPoiActivity(String region, double lat, double lng) {
        PoiParaOption para = new PoiParaOption().key(region).center(new LatLng(lat, lng)).radius(5);
        try {
            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            showPromptDialog();
        }
    }

    public void startNavActivity(final String region, final double lat, final double lng) {
        showWaitDialog("定位中...");
        LocationHelper.getInstance().start(new OnReceiveLoactionListener() {
            @Override
            public void onReceiveLocationListener(BDLocation loc) {
                cancelWaitDialog();
                if (LocationHelper.getInstance().isLocationSuccess(loc)) {
                    _startBaiduMapNav(loc.getAddrStr(), new LatLng(loc.getLatitude(), loc.getLongitude()), region,
                            new LatLng(lat, lng));
                } else {
                    showToast("定位失败！");
                }
            }
        });
    }


    private void _startBaiduMapNav(String startName, LatLng start, String endName, LatLng end) {
        NaviParaOption para = new NaviParaOption();
        para.startName(startName);
        para.startPoint(start);
        para.endName(endName);
        para.endPoint(end);
        try {
            BaiduMapNavigation.openBaiduMapNavi(para, getActivity());
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //                    BaiduMapNavigation.GetLatestBaiduMapApp(getActivity());
                    OpenClientUtil.getLatestBaiduMapApp(getActivity());
                }
            });
            builder.setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showPromptDialog() {
        showConfirmDialog("提示", "您尚未安装百度地图app或app版本过低，点击确认安装？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OpenClientUtil.getLatestBaiduMapApp(getActivity());
            }

        });
    }
}
