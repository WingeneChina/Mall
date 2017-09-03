package cn.wingene.mallxf.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangcq on 2017/9/2.
 * 本地搜索记录保存
 */

public class LocalSearchData {
    private List<String> mLocalList = new ArrayList<>();

    private LocalSearchData() {
    }

    private static LocalSearchData mLocalSearch;

    public static LocalSearchData getInstance() {
        if (mLocalSearch == null) {
            synchronized (LocalSearchData.class) {
                if (mLocalSearch == null) {
                    mLocalSearch = new LocalSearchData();
                }
            }
        }
        return mLocalSearch;
    }

    /**
     * 添加数据到保存
     *
     * @param localData
     */
    public void addLocalSearchData(String localData) {
        mLocalList.add(localData);
    }

    public List<String> getLocalList() {
        return mLocalList;
    }

    public void setLocalList(List<String> localList) {
        mLocalList = localList;
    }
}
