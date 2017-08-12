package cn.wingene.mallx.frame;

import android.app.Activity;
import android.content.Context;

import junze.androidxf.core.Agent.Major;


/**
 * Created by Wingene on 2017/8/12.
 */

public class IntentBuilder extends Major.IntentBuilder {
    public IntentBuilder(Context mContext) {
        super(mContext);
    }

    public static IntentBuilder create(Context context) {
        return new IntentBuilder(context);
    }

    public IntentBuilder putClass(Class<? extends Activity> cls) {
        super.setClass(cls);
        return this;
    }
}
