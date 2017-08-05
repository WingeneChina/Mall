package cn.wingene.mallxf;

import android.content.Context;

import junze.androidxf.db.DataBaseManager.DataBaseHelper;
import junze.androidxf.db.DataBaseManager.Tables;

/**
 * Created by Wingene on 2017/6/12.
 */

public class MyDBHelper extends DataBaseHelper {
    public final static String TAG = "MyDBHelper";
    public static final String FILE_NAME = "data.data";
    public static final int VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, FILE_NAME, 1);
    }

    @Override
    protected void initTableClass(Tables tables) {

    }
}
