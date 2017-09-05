package cn.wingene.mallxm.display.home.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.wingene.mall.R;

/**
 * Created by zqc on 16/8/24.
 * @author zqc
 */
public class VersionUpdateDialog implements View.OnClickListener {
    /**
     * dialog
     */
    private CustomDialog mDialog;
    /**
     * context
     */
    private Context mContext;
    /**
     *btn ok
     */
    private Button mBtnOk;
    /**
     * btn cancel
     */
    private Button mBtnCancel;

    /**
     * constructor
     * @param mContext context
     * @param currentVersion current version
     * @param newVersion new version
     * @param versionDetail version detail
     */
    public VersionUpdateDialog(Context mContext, String currentVersion
            , String newVersion, String versionDetail) {
        this.mContext = mContext;
        LayoutInflater mInflater= LayoutInflater.from(mContext);
        View mView=mInflater.inflate(R.layout.app_upate_dialog_layout,null);
        mDialog=new CustomDialog(mContext).setContentView(mView).setCanceledOnTouchOutside(false);
        mBtnOk= (Button) mView.findViewById(R.id.btn_ok);
        mBtnCancel= (Button) mView.findViewById(R.id.btn_cancel);
        ((TextView) mView.findViewById(R.id.tv_version_detail)).setText(versionDetail);
        ((TextView)mView.findViewById(R.id.version)).setText("当前版本:  "+currentVersion+"\n更新版本:  "+newVersion);
//        ((TextView)mView.findViewById(R.id.tv_version_detail)).setText(versionDetail);

        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
    }

    /**
     * get
     * @return btn ok
     */
    public Button getmBtnOk() {
        return mBtnOk;
    }

    /**
     * show dialog
     */
    public void showDialog(){
        mDialog.show();
    }

    /**
     * dismiss
     */
    public void dismiss(){
        mDialog.dismiss();
    }

    /**
     * get
     * @return btn cancel
     */
    public Button getmBtnCancel() {
        return mBtnCancel;
    }
}
