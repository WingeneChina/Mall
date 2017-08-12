package cn.wingene.mallxf.nohttp;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by wangcq on 2016/6/27.
 * gson 解析数据
 */
public class GsonUtil<T> {

    private Gson gson;

    private Class<T> mTClass;

    public GsonUtil(Class<T> tClass) {
        gson = new Gson();
        this.mTClass = tClass;
    }

    /**
     * 对象转化为json格式
     *
     * @param object
     * @return
     */
    public String dataToJson(Object object) {

        return gson.toJson(object);
    }

    /**
     * @param jsonString
     * @return
     * @throws JsonSyntaxException
     */
    public T fromJson(String jsonString) throws JsonSyntaxException {
        return gson.fromJson(jsonString, mTClass);
    }


    public ArrayList<T> listFormJson(String json) {
        return gson.fromJson(json, new
                TypeToken<ArrayList<T>>() {
                }.getType());
    }


    /**
     * 把json 转为树状字符串输出
     *
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }


}
