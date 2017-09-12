package cn.wingene.mall.util;

import java.io.File;
import java.net.URISyntaxException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import junze.java.util.StringUtil;

import junze.androidxf.core.Agent;

import cn.wingene.mallxm.display.home.firstMenu.dialog.IphoneStyleMenuDialog;

/**
 * Created by Wingene on 2017/9/12.
 */

public class MapUtil {
    //    /**
    //     * 确定起终点坐标BY高德
    //     */
    //    void setUpGaodeAppByLoca(){
    //        try {
    //            Intent intent = Intent.getIntent
    // ("androidamap://route?sourceApplication=softname&slat="+LATITUDE_A+"&slon="+LONGTITUDE_A+"&sname
    // ="+"万家丽国际Mall"+"&dlat="+LATITUDE_B+"&dlon="+LONGTITUDE_B+"&dname="+"东郡华城广场|A座"+"&dev=0&m=0&t=1");
    //            if(isInstallByread("com.autonavi.minimap")){
    //                startActivity(intent);
    //                Log.e(TAG, "高德地图客户端已经安装") ;
    //            }else {
    //                Log.e(TAG, "没有安装高德地图客户端") ;
    //            }
    //        } catch (URISyntaxException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //
    //    /**
    //     * 确认起终点名称BY高德
    //     */
    //    void setUpGaodeAppByName(){
    //        try {
    //            Intent intent = Intent.getIntent
    // ("androidamap://route?sourceApplication=softname"+"&sname="+"万家丽国际Mall"+"&dname="+"东郡华城广场|A座"+"&dev=0&m=0&t
    // =1");
    //            if(isInstallByread("com.autonavi.minimap")){
    //                startActivity(intent);
    //                Log.e(TAG, "高德地图客户端已经安装") ;
    //            }else {
    //                Log.e(TAG, "没有安装高德地图客户端") ;
    //            }
    //        } catch (URISyntaxException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //
    //    /**
    //     * 我的位置BY高德
    //     */
    //    void setUpGaodeAppByMine(){
    //        try {
    //            Intent intent = Intent.getIntent
    // ("androidamap://route?sourceApplication=softname&sname=我的位置&dlat="+LATITUDE_B+"&dlon="+LONGTITUDE_B+"&dname
    // ="+"东郡华城广场|A座"+"&dev=0&m=0&t=1");
    //            if(isInstallByread("com.autonavi.minimap")){
    //                startActivity(intent);
    //                Log.e(TAG, "高德地图客户端已经安装") ;
    //            }else {
    //                Log.e(TAG, "没有安装高德地图客户端") ;
    //            }
    //        } catch (URISyntaxException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //
    //    /**
    //     * 注意下面的起终点坐标都是百度坐标，如果使用高德坐标系有很大的误差
    //     */
    //    void setUpBaiduAPPByLoca(){
    //        try {
    //            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:"+LATITUDE_QIDIAN+",
    // "+LONGTITUDE_QIDIAN+"|name:万家丽国际Mall&destination=latlng:"+LATITUDE_ZHONGDIAN+",
    // "+LONGTITUDE_ZHONGDIAN+"|name:东郡华城广场|A座&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;
    // package=com.baidu.BaiduMap;end");
    //            if(isInstallByread("com.baidu.BaiduMap")){
    //                startActivity(intent);
    //                Log.e(TAG, "百度地图客户端已经安装") ;
    //            }else {
    //                Log.e(TAG, "没有安装百度地图客户端") ;
    //            }
    //        } catch (URISyntaxException e) {
    //            e.printStackTrace();
    //        }
    //    }


    //
    //    /**
    //     * 通过起终点名字使用百度地图
    //     */
    //    void setUpBaiduAPPByName(){
    //        try {
    //            Intent intent = Intent.getIntent
    // ("intent://map/direction?origin=万家丽国际Mall&destination=东郡华城广场|A座&mode=driving&src=yourCompanyName|yourAppName
    // #Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
    //            if(isInstallByread("com.baidu.BaiduMap")){
    //                startActivity(intent);
    //                Log.e(TAG, "百度地图客户端已经安装") ;
    //            }else {
    //                Log.e(TAG, "没有安装百度地图客户端") ;
    //            }
    //        } catch (URISyntaxException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //
    public static void startNavActivity(final Agent agent, final String name, final Double lat, final Double lng) {
        //        List<String> list = new ArrayList<>();
        String baidu = "百度地图";
        if (!isInstallByread("com.baidu.BaiduMap")) {
            baidu += "(未安装)";
        }
        String gaode = "高德地图";
        if (!isInstallByread("com.autonavi.minimap")) {
            gaode += "(未安装)";
        }
        final IphoneStyleMenuDialog dialog = new IphoneStyleMenuDialog(agent.getActivity(), new String[]{baidu,
                gaode});
        dialog.setOnIphoneStyleDialogMenuItemClickListener(new IphoneStyleMenuDialog
                .OnIphoneStyleDialogMenuItemClickListener() {
            @Override
            public void onIphoneStyleDialogMenuItemClick(View view) {
                switch (view.getId()) {
                case 0:
                    setUpBaiduAPPByMine(agent, name, lat, lng);
                    break;
                case 1:
                    double[] gaode = bdToGaoDe(lat, lng);
                    setUpGaodeAppByMine(agent, name, gaode[0], gaode[1]);
                    break;
                }
                dialog.dismiss();
            }
        });
        agent.showDialog(dialog);
    }

    private static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.sin(theta);
        gd_lat_lon[1] = z * Math.cos(theta);
        return gd_lat_lon;
    }


    /**
     * 我的位置BY高德
     */
    public static void setUpGaodeAppByMine(Agent agent, String name, Double lat, Double lng) {
        try {
            String uri = String.format("androidamap://route?sourceApplication=softname&sname=我的位置&%s&dev=0&m=0&t=1"
                    + "", makeGaoDeEndPlace(name, lat, lng));
            Intent intent = Intent.getIntent(uri);
            if (isInstallByread("com.autonavi.minimap")) {
                agent.getActivity().startActivity(intent);
            } else {
                showInstallGaodeDialog(agent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String makeGaoDeEndPlace(String name, Double lat, Double lng) {
        String latlng = null;
        if (lat != null && lng != null) {
            latlng = String.format("dlat=%s&dlon=%s", lat, lng);
        }
        return StringUtil.spellBy(new Object[]{latlng, name}, "&");
    }

    /**
     * 我的位置到终点通过百度地图
     */
    public static void setUpBaiduAPPByMine(Agent agent, String endName, Double endLat, Double endLng) {
        try {
            Intent intent = Intent.getIntent(String.format
                    ("intent://map/direction?origin=我的位置&destination=%s&mode=driving" +
                    "" + "&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                            makeBaiduPlace(endName, endLat, endLng)));
            if (isInstallByread("com.baidu.BaiduMap")) {
                agent.getActivity().startActivity(intent);
            } else {
                showInstallBaiduDialog(agent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private static void showInstallBaiduDialog(final Agent agent) {
        showInstallDialog(agent, "百度地图", "http://map.baidu.com/zt/qudao/map/html/index.html");
    }

    private static void showInstallGaodeDialog(final Agent agent) {
        showInstallDialog(agent, "高德地图", "http://wap.amap.com/index.html");
    }

    private static void showInstallDialog(final Agent agent, String appName, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(agent.getActivity());
        builder.setMessage(String.format("您尚未安装%sapp或app版本过低，点击确认安装？", appName));
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //                    BaiduMapNavigation.GetLatestBaiduMapApp(getActivity());
                Intent var1 = new Intent();
                var1.setAction("android.intent.action.VIEW");
                Uri var2 = Uri.parse(url);
                var1.setData(var2);
                agent.getActivity().startActivity(var1);
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        agent.showDialog(builder.create());
    }

    public static String makeBaiduPlace(String name, Double lat, Double lng) {
        String latlng = null;
        if (lat != null && lng != null) {
            latlng = String.format("latlng:%s,%s", lat, lng);
        }
        return StringUtil.spellBy(new Object[]{latlng, name}, "|");
    }

}
