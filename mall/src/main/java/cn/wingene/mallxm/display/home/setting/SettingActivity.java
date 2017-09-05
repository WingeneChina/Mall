package cn.wingene.mallxm.display.home.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageCropActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.util.VersionUtil;
import cn.wingene.mallxm.account.LoginActivity;
import cn.wingene.mallxm.account.RegisterFirstStepActivity;
import cn.wingene.mallxm.account.data.LoginModel;
import cn.wingene.mallxm.display.home.dialog.VersionUpdateDialog;
import cn.wingene.mallxm.display.home.firstMenu.data.HeadUpLoadModel;
import cn.wingene.mallxm.display.home.firstMenu.dialog.IphoneStyleMenuDialog;
import cn.wingene.mallxm.display.home.setting.data.VersionModel;

/**
 * 设置界面
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private static final int FILE_UPLOAD = 1;//文件上传
    private static final int VERSION_INFO = 2;//版本信息

    private ImageView backIcon;
    private TextView titleV;
    private TextView headDesV;
    private SimpleDraweeView headV;
    private TextView updateUserNameV;
    private TextView bindPhoneNumberV;
    private TextView versionInfoV;
    private TextView clearCacheV;
    private TextView goScoreV;
    private TextView suggestionV;
    private TextView aboutAsV;
    private TextView aboutClauseV;
    private Button loginOutV;

    private IphoneStyleMenuDialog mIphoneStyleMenuDialog;
    private String mHeadImgPath;
    private LoginModel loginModel;

    private VersionUpdateDialog mDialog;
    /**
     * download url
     */
    private String mDownloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initEvent();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        headDesV = (TextView) findViewById(R.id.headDesV);
        headV = (SimpleDraweeView) findViewById(R.id.headV);
        updateUserNameV = (TextView) findViewById(R.id.updateUserNameV);
        bindPhoneNumberV = (TextView) findViewById(R.id.bindPhoneNumberV);
        versionInfoV = (TextView) findViewById(R.id.versionInfoV);
        clearCacheV = (TextView) findViewById(R.id.clearCacheV);
        goScoreV = (TextView) findViewById(R.id.goScoreV);
        suggestionV = (TextView) findViewById(R.id.suggestionV);
        aboutAsV = (TextView) findViewById(R.id.aboutAsV);
        aboutClauseV = (TextView) findViewById(R.id.aboutClauseV);
        loginOutV = (Button) findViewById(R.id.loginOutV);

        versionInfoV.append(VersionUtil.getAppVersionName(this));
        headV.setImageURI(UserData.getPersonHeadUrl());

    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        headV.setOnClickListener(this);
        updateUserNameV.setOnClickListener(this);
        bindPhoneNumberV.setOnClickListener(this);
        clearCacheV.setOnClickListener(this);
        goScoreV.setOnClickListener(this);
        suggestionV.setOnClickListener(this);
        aboutAsV.setOnClickListener(this);
        aboutClauseV.setOnClickListener(this);
        loginOutV.setOnClickListener(this);

        versionInfoV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.headV:
                mIphoneStyleMenuDialog = new IphoneStyleMenuDialog(this, new String[]{"相册", "相机"});
                mIphoneStyleMenuDialog.show();
                mIphoneStyleMenuDialog.setOnIphoneStyleDialogMenuItemClickListener(new IphoneStyleMenuDialog
                        .OnIphoneStyleDialogMenuItemClickListener() {


                    @Override
                    public void onIphoneStyleDialogMenuItemClick(View view) {
                        switch (view.getId()) {
                            case 1://拍照
                                ImagePicker.getInstance().takePicture(SettingActivity.this, ImagePicker
                                        .REQUEST_CODE_TAKE);

                                break;
                            case 0://从相册选择
                                Intent photoPagesIntent = new Intent(SettingActivity.this, ImageGridActivity.class);
                                startActivityForResult(photoPagesIntent, ImagePicker.REQUEST_CODE_CROP);//用裁剪完成的请求码

                                break;
                        }
                        mIphoneStyleMenuDialog.dismiss();
                    }
                });
                break;
            case R.id.updateUserNameV:
                Intent intent = new Intent(this, ChangeUserNameActivity.class);
                startActivity(intent);

                break;
            case R.id.bindPhoneNumberV:
                Intent intent1 = new Intent(this, RegisterFirstStepActivity.class);
                intent1.putExtra("title", "修改密码");
                intent1.putExtra("type", 2);
                startActivity(intent1);
                break;
            case R.id.clearCacheV:
                Fresco.getImagePipeline().clearDiskCaches();
                Toast toast = Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.goScoreV:

                break;
            case R.id.suggestionV:

                break;
            case R.id.aboutAsV:
                Intent aboutIntent = new Intent(this, AboutAsActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.aboutClauseV:

                break;
            case R.id.loginOutV:
                UserData.clearAllUserInfo();
                Intent loginOutIntent = new Intent(this, LoginActivity.class);
                startActivity(loginOutIntent);
                break;
            case R.id.versionInfoV:
                requestVersionInfo();
                break;
            case R.id.btn_ok:
                downApp();
                mDialog.dismiss();
                break;
            case R.id.btn_cancel:
                mDialog.dismiss();
                break;
        }
    }

    /**
     * 下载App
     */
    private void downApp() {
//        DownLoadService.startDownloadFile(this, mDownloadUrl);

        Uri uri = Uri.parse(mDownloadUrl);
        Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(downloadIntent);

    }

    /**
     * 请求版本信息
     */
    private void requestVersionInfo() {//APP_VERSION
        NoHttpRequest<VersionModel> noHttpRequest = new NoHttpRequest(VersionModel.class);
        noHttpRequest.request(this, HttpConstant.APP_VERSION, null, VERSION_INFO, this, false, null, true, false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE_TAKE && RESULT_OK == resultCode) {
            ImagePicker imagePicker = ImagePicker.getInstance();
            //发送广播通知图片增加了
            ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
            ImageItem imageItem = new ImageItem();
            imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
            imagePicker.clearSelectedImages();
            imagePicker.addSelectedImageItem(0, imageItem, true);

            if (imagePicker.isCrop()) {
                Intent intent = new Intent(this, ImageCropActivity.class);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);
            }

        } else if (requestCode == ImagePicker.REQUEST_CODE_CROP && data != null) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                    .EXTRA_RESULT_ITEMS);
            mHeadImgPath = images.get(0).path;
            Log.e(this.getClass().getName(), "headVPath = " + mHeadImgPath);
            headV.setImageURI("file://" + mHeadImgPath);

            upLoadHeadFile(mHeadImgPath);
        }
    }

    /**
     * 上传头像数据
     */
    private void upLoadHeadFile(String headImgPath) {
        try {
            NoHttpRequest<HeadUpLoadModel> noHttpRequest = new NoHttpRequest<>(HeadUpLoadModel.class);
            File file = new File(headImgPath);
            HashMap<String, Object> hashMap = new HashMap<>();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] imgByte = new byte[fileInputStream.available()];
            fileInputStream.read(imgByte);
            String imgBase64 = Base64.encodeToString(imgByte, Base64.DEFAULT);
            hashMap.put("ByteAvatar", imgBase64);
            noHttpRequest.upLoadFile(this, FILE_UPLOAD, HttpConstant.UPLOAD_HEAD_IMG, hashMap, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(), "reponse.get() = " + response.get());
        try {
            switch (what) {
                case FILE_UPLOAD:
                    GsonUtil<HeadUpLoadModel> gsonUtil = new GsonUtil<>(HeadUpLoadModel.class);
                    HeadUpLoadModel headUpLoadModel = gsonUtil.fromJson(response.get());
                    if (headUpLoadModel.getErr() == 0) {
                        UserData.savePersonHeadUrl(headUpLoadModel.getData().getAvatar());
                    }
                    break;
                case VERSION_INFO:
                    GsonUtil<VersionModel> versonGson = new GsonUtil<>(VersionModel.class);
                    VersionModel versionModel = versonGson.fromJson(response.get());
                    if (versionModel.getData().getVersionCode() == VersionUtil.getAppVersionCode(this.getBaseContext
                            ())) {
                        ToastUtil.show("当前已经是最新版本了", this);
                    } else if (versionModel.getData().getVersionCode() > VersionUtil.getAppVersionCode(this
                            .getBaseContext
                                    ())) {

                        mDialog = new VersionUpdateDialog(this
                                , VersionUtil.getAppVersionName(this)
                                , versionModel.getData().getVersionName()
                                , versionModel.getData().getReason());
                        mDownloadUrl = versionModel.getData().getDownloadUrl();

                        mDialog.showDialog();
                        mDialog.getmBtnOk().setOnClickListener(this);
                        mDialog.getmBtnCancel().setOnClickListener(this);

                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
