package cn.wingene.mallxm.display.home.secondMenu.data;

import java.util.List;

/**
 * Created by wangcq on 2017/8/27.
 * 专题菜单类型
 */

public class MenuItemModel {
    /**
     * err : 0
     * msg : 获取专题分类成功
     * data : [{"Code":"I1","Name":"精选"},{"Code":"I2","Name":"家居"},{"Code":"I3","Name":"家电"},{"Code":"I4","Name":"汽车"}]
     */

    private int err;
    private String msg;
    /**
     * Code : I1
     * Name : 精选
     */

    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String Code;
        private String Name;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
