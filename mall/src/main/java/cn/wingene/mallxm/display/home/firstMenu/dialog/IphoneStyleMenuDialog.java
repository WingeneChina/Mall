package cn.wingene.mallxm.display.home.firstMenu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wingene.mall.R;

/**
 * 模仿ios样式的底部弹窗菜单
 *
 * @author wangcq
 */
public class IphoneStyleMenuDialog extends Dialog implements
        View.OnClickListener {
    private String[] menus;
    private OnIphoneStyleDialogMenuItemClickListener mListener;

    public IphoneStyleMenuDialog(Context context, String[] menus) {
        super(context);
        this.menus = menus;
        initView(context);
    }

    public IphoneStyleMenuDialog(Context context, int themeResId) {
        super(context, themeResId);
        // TODO Auto-generated constructor stub
    }

    private void initView(Context context) {
        this.getWindow().setBackgroundDrawable(
                getContext().getResources().getDrawable(
                        R.drawable.popupwindwo_bg));
        this.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        this.getWindow().setGravity(Gravity.BOTTOM);

        this.getWindow().getDecorView().setPadding(18, 0, 18, 0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);

        View view = LayoutInflater.from(context).inflate(
                R.layout.iphonestylemendialog_layout, null);

        LinearLayout menuGroup = (LinearLayout) view
                .findViewById(R.id.menuGroup);

        for (int i = 0; i < menus.length; i++) {
            String menu = menus[i];
            TextView menuItem = new TextView(context);
            menuItem.setId(i);
            LayoutParams lparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    (int) (50 * context.getResources().getDisplayMetrics().density));
            menuItem.setLayoutParams(lparams);
            menuItem.setGravity(Gravity.CENTER);
            menuItem.setTextColor(Color.parseColor("#666666"));
            menuItem.setTextSize(15);
            menuItem.setText(menu);
            menuItem.setBackgroundResource(R.drawable.listitem_selector);
            menuItem.requestFocus();
            View viewLinear = new View(context);
            LayoutParams viewParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, (int) (1 * context
                    .getResources().getDisplayMetrics().density));
            viewLinear.setLayoutParams(viewParams);
            viewLinear.setBackgroundResource(R.color.nine_text_color);
            if (i >= 1) {
                menuGroup.addView(viewLinear);
            }
            menuGroup.addView(menuItem);

            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onIphoneStyleDialogMenuItemClick(v);
                    }
                }
            });
        }
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                this.dismiss();
                break;

            default:
                break;
        }

    }

    /**
     * 设置事件回调（除取消外);
     *
     * @param listener
     */
    public void setOnIphoneStyleDialogMenuItemClickListener(
            OnIphoneStyleDialogMenuItemClickListener listener) {
        mListener = listener;
    }

    /**
     * 仿iphone 底部弹出对话框作为菜单的菜单栏监听器
     * <p/>
     * viewId 与传入的位置对应
     *
     * @author wangcq
     */
    public interface OnIphoneStyleDialogMenuItemClickListener {
        void onIphoneStyleDialogMenuItemClick(View view);
    }

}
