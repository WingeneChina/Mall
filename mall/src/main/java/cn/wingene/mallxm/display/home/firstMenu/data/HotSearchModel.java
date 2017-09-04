package cn.wingene.mallxm.display.home.firstMenu.data;

import java.util.List;

/**
 * Created by wangcq on 2017/9/4.
 * 热门搜索模型
 */

public class HotSearchModel {

    /**
     * err : 0
     * msg : 获取热门搜索列表成功
     * act : null
     * data : {"Total":3,"List":[{"Name":"法国"},{"Name":"智能电子"},{"Name":"双飞燕"}]}
     */

    private int err;
    private String msg;
    private Object act;
    /**
     * Total : 3
     * List : [{"Name":"法国"},{"Name":"智能电子"},{"Name":"双飞燕"}]
     */

    private DataBean data;

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

    public Object getAct() {
        return act;
    }

    public void setAct(Object act) {
        this.act = act;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int Total;
        /**
         * Name : 法国
         */

        private java.util.List<ListBean> List;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            private String Name;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
