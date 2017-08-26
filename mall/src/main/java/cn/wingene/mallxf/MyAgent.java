package cn.wingene.mallxf;

import android.app.Activity;
import android.os.AsyncTask;

import junze.java.net.IHttpElement.IRequest;
import junze.java.net.IHttpElement.IResponse;

import junze.androidxf.core.Agent;

import cn.wingene.mallxf.http.Ask.NeedLoginException;
import cn.wingene.mallxm.JumpHelper;

/**
 * Created by Wingene on 2017/8/26.
 */

public class MyAgent extends Agent {
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
                        if (request.getException() != null && request.getException() instanceof NeedLoginException) {
                            JumpHelper.startLoginActivity(getActivity());
                        } else {
                            showToast(request.getException());
                        }
                    } else {
                        request.updateUIWhenException();
                    }
                }
                request.updateFinally();
                cancelWaitDialog();
            }

        }.execute("");
    }


}
