package cn.wingene.mallxm.purchase.tool;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import static cn.wingene.mallx.frame.fragment.BasePullListFragment.ACTION_NEED_UPDATE;
import com.alipay.sdk.app.PayTask;

import junze.java.able.ICallBack;
import junze.java.bean.Duo;
import junze.java.manager.ObserverManager;
import junze.java.net.IHttpElement.IRequest;
import junze.java.net.IHttpElement.IResponse;

import junze.androidxf.core.Agent;

import cn.wingene.mallxf.http.Ask.NeedLoginException;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.OrderDetailActivity;
import cn.wingene.mallxm.purchase.PayResult;
import cn.wingene.mallxm.purchase.ask.AskSubmitPayNow;
import cn.wingene.mallxm.purchase.bean.Order;
import cn.wingene.mallxm.purchase.dialog.BottomPayChoiseDialog;

/**
 * Created by Wingene on 2017/9/3.
 */

public class PayHelper {
    private BottomPayChoiseDialog mPayChoiseDialog;
    private Agent mAgent;
    private OnOrderBuild mOnOrderBuild;

    public PayHelper(Agent agent) {
        mAgent = agent;
    }

    public void showBottomDialog(final double mPayPrice, final double amount, final int integral) {
        if (mPayChoiseDialog == null) {
            mPayChoiseDialog = new BottomPayChoiseDialog(getAgent().getActivity());
        }
        if (mPayPrice == 0) {
            onOrderBuild(PayHelper.this, PayMothed.ACCOUNT, amount, integral);
            return;
        }
        mPayChoiseDialog.show(getAgent(), mPayPrice, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayChoiseDialog.hide();
            }
        }, new ICallBack<Boolean>() {
            @Override
            public void callBack(final Boolean isAlipay) {
                onOrderBuild(PayHelper.this, PayMothed.ALIPAY, amount, integral);
            }
        });
    }

    public static interface OnOrderBuild {
        void onOrderBuild(PayHelper helper, PayMothed payMothed, final double amount, final int integral);
    }

    private void onOrderBuild(PayHelper helper, PayMothed payMothed, final double amount, final int integral) {
        if (mOnOrderBuild != null) {
            mOnOrderBuild.onOrderBuild(helper, payMothed, amount, integral);
        }
    }


    public void setOnOrderBuild(OnOrderBuild onOrderBuild) {
        mOnOrderBuild = onOrderBuild;
    }

    public Agent getAgent() {
        return mAgent;
    }

    public <T extends IResponse> AsyncTask<String, Integer, T> ask(IRequest<T> request) {
        return mAgent.ask(request);
    }

    public <T extends IResponse> AsyncTask<String, Integer, T> ask(CharSequence msg, boolean autoHandleException,
            IRequest<T> request) {
        return mAgent.ask(msg, autoHandleException, request);
    }

    public void askPay(final Order order, final PayMothed payMothed, double amount, int integral) {
        ask("提交中...", false, new AskSubmitPayNow.Request(order.getNo(), payMothed, 0, amount, integral) {
            @Override
            public void updateUI(final AskSubmitPayNow.Response rsp) {
                if (rsp.data.getPayState() == 1) {
                    jumpToPayResultActivityAndFinish(order.getNo());
                    return;
                }
                if(payMothed == PayMothed.ACCOUNT){
                    jumpToWaitPayActivityAndFinish(order.getNo());
                    return;
                }else if (payMothed ==PayMothed.ALIPAY) {
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            //调用支付宝
                            PayTask payTask = new PayTask(getActivity());
                            Map<String, String> result = payTask.payV2(rsp.data.getPayDataForAlipay().getSignText()
                                    , true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = new Duo<Map<String, String>, String>(result,order.getNo());
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    showMsgDialog("提示", "微信支付功能开发中...", "我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jumpToWaitPayActivityAndFinish(order.getNo());
                        }
                    });
                }
            }

            @Override
            protected void updateUIWhenException(Exception e) {
                if (e instanceof NeedLoginException) {
                    jumpToWaitPayActivityAndFinish(order.getNo(), true);
                } else {
                    showToast(e);
                    jumpToWaitPayActivityAndFinish(order.getNo());
                }
            }
        });
    }


    public static final int SDK_PAY_FLAG = 2000;
    public static final int ORDER_NO = 3000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case SDK_PAY_FLAG: {
                Duo<Map<String, String>, String> duo = (Duo<Map<String, String>, String>) msg.obj;
                PayResult payResult = new PayResult((Map<String, String>) duo.getFirst());
                String resultStatus = payResult.getResultStatus();// 同步返回需要验证的信息
                if (TextUtils.equals(resultStatus, "9000")) {
                    showToast("支付成功");
                    jumpToPayResultActivityAndFinish(duo.getSecond());
                } else {
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服<span style="white-space:pre">
                    // </span>务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        showToast("支付结果确认中");
                        jumpToPayResultActivityAndFinish(duo.getSecond());
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        showToast("支付取消");
                        jumpToWaitPayActivityAndFinish(duo.getSecond());
                    } else if (TextUtils.equals(resultStatus, "6002")) {
                        showToast("网络异常");
                        jumpToWaitPayActivityAndFinish(duo.getSecond());
                    } else if (TextUtils.equals(resultStatus, "5000")) {
                        showToast("重复请求");
                        jumpToPayResultActivityAndFinish(duo.getSecond());
                    } else {
                        showToast("支付失败");
                        jumpToWaitPayActivityAndFinish(duo.getSecond());
                    }
                }
                break;
            }
            }
        }
    };

    public void jumpToWaitPayActivityAndFinish(String orderNo) {
        jumpToWaitPayActivityAndFinish(orderNo, false);
    }

    public void jumpToWaitPayActivityAndFinish(String orderNo, boolean notLogin) {
        finish();
        OrderDetailActivity.major.startForOrderNo(getActivity(),orderNo);
//        JumpHelper.startOrderListActivity(getActivity(), 0);
        if (notLogin) {
            JumpHelper.startLoginActivity(getActivity());
        }
    }

    public void jumpToPayResultActivityAndFinish(String orderNo) {
        finish();
        OrderDetailActivity.major.startForOrderNo(getActivity(),orderNo);
        ObserverManager.getInstance().notifyUpdate(ACTION_NEED_UPDATE);
    }

    public Activity getActivity() {
        return mAgent.getActivity();
    }

    public void finish() {
        getActivity().finish();
    }

    public void showToast(String format, Object... args) {
        mAgent.showToast(format, args);
    }

    public void showToast(Exception e) {
        mAgent.showToast(e);
    }

    public void showMsgDialog(CharSequence title, CharSequence message, String text, DialogInterface
            .OnClickListener l) {
        mAgent.showMsgDialog(title, message, text, l);
    }

    public enum PayMothed {
        ACCOUNT(11,0), ALIPAY(21,2102), WX(22,2202);
        public int code;
        public int sonCode;

        PayMothed(int code,int sonCode) {
            this.code = code;
            this.sonCode = sonCode;
        }
    }
}
