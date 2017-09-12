//package cn.wingene.mallxm.map;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
//import com.baidu.mapapi.navi.BaiduMapNavigation;
//import com.baidu.mapapi.navi.NaviParaOption;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.utils.OpenClientUtil;
//import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
//import com.baidu.mapapi.utils.poi.PoiParaOption;
//
//import junze.androidx.baidu.LocationHelper;
//import junze.androidx.baidu.OnReceiveLoactionListener;
//import junze.androidxf.core.Agent;
//import junze.androidxf.kit.AKit;
//import junze.androidxf.ui.activity.BaseFragmentActivity;
//
//import cn.wingene.mall.R;
//
//
//public class MapActivity extends BaseFragmentActivity implements OnGetGeoCoderResultListener {
//    public final static Major major = new Major();
//    private BaiduMap mBaiduMap;
//    private GeoCoder mGeoCoder;
//    //    private ReverseGeoCodeResult mResult;
//    private Location mLocation;
//    private boolean neverSetMapZoom = true;
//
//    private ImageView ivBack;
//    private TextView tvTopTitle;
//    private MapView map;
//    private TextView tvTitle;
//    private TextView tvMemo;
//    private Button btnOk;
//
//    protected void initComponent() {
//        ivBack = (ImageView) super.findViewById(R.id.iv_back);
//        tvTopTitle = (TextView) super.findViewById(R.id.tv_top_title);
//        map = (MapView) super.findViewById(R.id.map);
//        tvTitle = (TextView) super.findViewById(R.id.tv_title);
//        tvMemo = (TextView) super.findViewById(R.id.tv_memo);
//        btnOk = (Button) super.findViewById(R.id.btn_ok);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map_choise_point);
//        initComponent();
//        mGeoCoder = GeoCoder.newInstance();
//        mGeoCoder.setOnGetGeoCodeResultListener(this);
//        mBaiduMap = map.getMap();
//        if (getMajor() == Major.MAJOR_CHOISE) {
//            setMapEventForChoise();
//            tvTopTitle.setText("地图选点");
//        } else {
//            tvTopTitle.setText("百度地图");
//        }
//        Location location = major.getInput(this);
//        if (location != null) {
//            mLocation = location;
//            refreshUI();
//            //            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(location
//            // .getLatitude(), location
//            //                    .getLongitude())));
//        } else {
//            if (getMajor() == Major.MAJOR_SHOW) {
//                showToast("没有位置可以显示！");
//                return;
//            }
//            showWaitDialog("请稍候...");
//            LocationHelper.getInstance().start(new OnReceiveLoactionListener() {
//
//                @Override
//                public void onReceiveLocationListener(BDLocation location) {
//                    cancleWaitDialog();
//                    if (LocationHelper.isLocationSuccess(location)) {
//                        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
//                        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latlng));
//                    } else {
//                        finish();
//                    }
//                }
//            });
//        }
//    }
//
//    private void setMapEventForChoise() {
//        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                mLocation = new Location(mapPoi.getPosition().longitude, mapPoi.getPosition().latitude, mapPoi
//                        .getName());
//                refreshUI();
//                return true;
//            }
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
//                showWaitDialog("请稍候...");
//            }
//        });
//    }
//
//    public void onViewClick(View v) {
//        if (v == ivBack) {
//            finish();
//        } else if (v == btnOk) {
//            if (mLocation != null) {
//                if (getMajor() == Major.MAJOR_SHOW) {
//                    int version = OpenClientUtil.getBaiduMapVersion(getActivity());
//                    if(version != 0){
//                        startNavActivity(mLocation.getAddress(), mLocation.getLatitude(), mLocation.getLongitude());
//                    }else{
//                        startPoiActivity(mLocation.getAddress(), mLocation.getLatitude(), mLocation.getLongitude());
//                    }
//                } else {
//                    setResult(Activity.RESULT_OK, major.buildResult(mLocation));
//                    finish();
//                }
//            } else {
//                showToast("请在地图上选点!");
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        map.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        map.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        map.onDestroy();
//        mGeoCoder.destroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult arg0) {
//
//    }
//
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            showToast("抱歉，未能找到结果");
//            mLocation = null;
//        } else {
//            mLocation = result2Location(result);
//        }
//        cancleWaitDialog();
//        refreshUI();
//    }
//
//    public LatLng location2LatLng(Location location) {
//        return new LatLng(location.getLatitude(), location.getLongitude());
//    }
//
//    public void refreshUI() {
//        mBaiduMap.clear();
//        tvTitle.setText("点击地图选点。");
//        tvMemo.setText("");
//        if (mLocation != null) {
//            LatLng latLng = location2LatLng(mLocation);
//            mBaiduMap.addOverlay(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R
//                    .drawable.icon_openmap_focuse_mark)));
//            if (neverSetMapZoom) {
//                // 处理缩放 sdk 缩放级别范围： [3.0,19.0]
//                float zoomLevel = 18.0f; // 精确到50米。
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLng, zoomLevel));
//                neverSetMapZoom = false;
//            } else {
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
//            }
//            tvTitle.setText(mLocation.getAddress());
//            //            tvMemo.setText(mResult.getAddressDetail().toString());
//            tvMemo.setVisibility(View.GONE);
//            if (getMajor() == Major.MAJOR_SHOW) {
//                btnOk.setText("导航");
//            } else {
//                btnOk.setText("确定");
//            }
//
//        }
//    }
//
//    public Location result2Location(ReverseGeoCodeResult result) {
//        return new Location(result.getLocation().longitude, result.getLocation().latitude, result.getAddress());
//    }
//
//    ////
//    public void startNavActivity(final String region, final double lat, final double lng) {
//        showWaitDialog("定位中...");
//        LocationHelper.getInstance().start(new OnReceiveLoactionListener() {
//            @Override
//            public void onReceiveLocationListener(BDLocation loc) {
//                cancleWaitDialog();
//                if (LocationHelper.getInstance().isLocationSuccess(loc)) {
//                    _startBaiduMapNav(loc.getAddrStr(), new LatLng(loc.getLatitude(), loc.getLongitude()), region,
//                            new LatLng(lat, lng));
//                } else {
//                    showToast("定位失败！");
//                }
//            }
//        });
//    }
//
//    /**
//     * 启动百度地图Poi周边检索
//     */
//    public void startPoiActivity(String region, double lat, double lng) {
//        PoiParaOption para = new PoiParaOption().key(region).center(new LatLng(lat, lng)).radius(5);
//        try {
//            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, getActivity());
//        } catch (Exception e) {
//            e.printStackTrace();
//            showInstallDialog();
//        }
//    }
//
//
//    private void _startBaiduMapNav(String startName, LatLng start, String endName, LatLng end) {
//        NaviParaOption para = new NaviParaOption();
//        para.startName(startName);
//        para.startPoint(start);
//        para.endName(endName);
//        para.endPoint(end);
//        try {
//            BaiduMapNavigation.openBaiduMapNavi(para, getActivity());
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showInstallDialog();
//        }
//    }
//
//    private void showInstallDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
//        builder.setTitle("提示");
//        builder.setPositiveButton("确认", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //                    BaiduMapNavigation.GetLatestBaiduMapApp(getActivity());
//                OpenClientUtil.getLatestBaiduMapApp(getActivity());
//            }
//        });
//        builder.setNegativeButton("取消", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.create().show();
//    }
//
//    public static class Major extends Agent.Major {
//        public final static int MAJOR_SHOW = Major.createIntMajor(Major.class, 0);
//        public final static int MAJOR_CHOISE = Major.createIntMajor(Major.class, 1);
//        public final static String INPUT_LOCATION = "INPUT_LOCATION";
//        public final static String RESULT = "RESULT";
//
//        public Major() {
//            super(MapActivity.class);
//        }
//
//        public void startForChoisePoi(Activity src, int requestCode) {
//            src.startActivityForResult(builder(src).setMajor(Major.MAJOR_CHOISE).getIntent(), requestCode);
//        }
//
//        public void startForChoisePoi(Activity src, int requestCode, Location lcoation) {
//            String key = AKit.toJson(lcoation);
//            src.startActivityForResult(builder(src).setMajor(Major.MAJOR_CHOISE).putExtra(INPUT_LOCATION, key)
//                    .getIntent(), requestCode);
//        }
//
//        public void startForShowMap(Context src, Location location) {
//            String key = AKit.toJson(location);
//            builder(src).setMajor(Major.MAJOR_SHOW).putExtra(INPUT_LOCATION, key).startActivity();
//        }
//
//        public Location getInput(Activity target) {
//            String key = target.getIntent().getStringExtra(INPUT_LOCATION);
//            return AKit.fromJson(key, Location.class);
//        }
//
//        public Location getResult(Intent data) {
//            String info = data.getStringExtra(RESULT);
//            return AKit.fromJson(info, Location.class);
//        }
//
//        public Intent buildResult(ReverseGeoCodeResult result) {
//            Location info = new Location(result.getLocation().longitude, result.getLocation().latitude, result
//                    .getAddress());
//            return buildResult(info);
//        }
//
//        public Intent buildResult(Location info) {
//            Intent i = new Intent();
//            i.putExtra(RESULT, AKit.toJson(info));
//            return i;
//        }
//    }
//}
