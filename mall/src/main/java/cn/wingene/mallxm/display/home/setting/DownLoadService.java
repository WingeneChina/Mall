package cn.wingene.mallxm.display.home.setting;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

import java.io.File;

import cn.wingene.mall.R;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxm.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * 版本下载服务
 * Created by zqc on 16/8/25.
 *
 * @author zqc
 */
public class DownLoadService extends IntentService {

    private int DOWN_FAIL = 0;
    private int DOWN_SUCCESS = 1;
    private int DOWNING = 2;
    private int DOWN_START = 3;
    private int DOWN_CANCEL = 4;
    /**
     * 下载行为
     */
    private static final String DOWN_ACTION = "com.klebreath.patient.mvp";

    /**
     * 下载地址
     */
    private static final String EXTRA_FILE_URL = "com.klebreath.patient.mvp.url";
    private NotificationManager mManager;
    private Notification mNotification;

    public DownLoadService() {
        super("DownLoadService");
    }

    /**
     * @param context 上下文
     * @param url     下载地址
     */
    public static void startDownloadFile(Context context, String url) {
        Intent intent = new Intent(context, DownLoadService.class);
        intent.setAction(DOWN_ACTION);
        intent.putExtra(EXTRA_FILE_URL, url);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification();
        if (intent != null && intent.getAction().equals(DOWN_ACTION)) {
            String url = intent.getStringExtra(EXTRA_FILE_URL);
            String strPkgName = url.substring(url.lastIndexOf("/") + 1);
            String filePath = Environment.getExternalStorageDirectory() + "/breath/app";
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            } else {
                file.mkdir();
            }
            NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
            noHttpRequest.downLoadFile(1, url, filePath, strPkgName
                    , new DownloadListener() {
                        @Override
                        public void onDownloadError(int what, Exception exception) {
                            Log.e("down", "onProgress: ---->" + exception.getMessage());
                            notification("下载失败", 0, DOWN_FAIL);
                        }

                        @Override
                        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long
                                allCount) {
                            Log.e("down", "onStart: ------>" + rangeSize);
                            notification("开始下载", 0, DOWN_START);
                        }

                        @Override
                        public void onProgress(int what, int progress, long fileCount, long speed) {
                            Log.e("down", "onProgress: ---->" + progress);
                            notification("正在下载更新包", progress, DOWNING);
                        }

                        @Override
                        public void onFinish(int what, String filePath) {
                            Log.e("down", "onFinish: ---->" + filePath);
                            notification(filePath, 1, DOWN_SUCCESS);
                        }

                        @Override
                        public void onCancel(int what) {
                            notification("下载失败", 0, DOWN_CANCEL);
                        }
                    });
        }
    }

    /**
     * 通知
     *
     * @param text     show text
     * @param progress download progress
     */
    private void notification(String text, int progress, int type) {

        Intent mIntent = new Intent(this, SettingActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.download_layout);
        mNotification.icon = R.drawable.sjdl_logo;
        mNotification.tickerText = text;
        mNotification.priority = Notification.PRIORITY_MAX;
        mNotification.when = System.currentTimeMillis();
        view.setProgressBar(R.id.progressBar, 100, progress, true);
        view.setTextColor(R.id.tv_progress, getResources().getColor(R.color.six_text_color));

        switch (type) {
            case 0://DOWN_FAIL
                Log.e("down", "onFail: ---->");
//                mManager.cancel(1);
                view.setTextViewText(R.id.tv_progress, text);
                break;
            case 1://DOWN_SUCCESS
                Log.e("down", "SUCCESS: ---->");
                notification(text);
                break;
            case 2://DOWNING
                Log.e("down", "DOWNING: ---->");
            case 3://DOWN_START
                Log.e("down", "START: ---->");
                view.setTextViewText(R.id.tv_progress, progress + "%" + text);
                mNotification.contentView = view;
                mNotification.contentIntent = mPendingIntent;
                mManager.notify(1, mNotification);
                break;
            case 4://DOWN_CANCEL
                Log.e("down", "DOWN_CANCEL: ---->");
//                mManager.cancel(1);
                view.setTextViewText(R.id.tv_progress, text);
                break;
        }

    }

    /**
     * 通知
     *
     * @param filePath file path
     */
    private void notification(String filePath) {
        mManager.cancel(1);
        Uri uri = Uri.fromFile(new File(filePath));
        String type = "application/vnd.android.package-archive";
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mIntent.setDataAndType(uri, type);
        startActivity(mIntent);
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
//        RemoteViews view = new RemoteViews(getPackageName(), R.layout.download_layout);
//        mNotification.icon = R.drawable.logo_green;
//        mNotification.tickerText = filePath;
//        view.setProgressBar(R.id.progressBar, 100, 100, true);
//        view.setTextViewText(R.id.tv_progress, "下载完成,点击安装");
//        mNotification.contentView = view;
//        mNotification.contentIntent = mPendingIntent;
//        mNotification.flags |=Notification.FLAG_AUTO_CANCEL;
//        mManager.notify(1, mNotification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
