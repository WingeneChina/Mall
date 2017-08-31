package cn.wingene.mallxm.display.home.firstMenu.data;

import java.util.List;

/**
 * Created by wangcq on 2017/8/30.
 * 商品分组模型
 */

public class ProductGroupModel {

    /**
     * err : 0
     * msg : 获取商品分类成功
     * data : [{"Code":"A401","Name":"T恤","Level":1},{"Code":"A402","Name":"裤装","Level":1},{"Code":"A403",
     * "Name":"内裤","Level":1},{"Code":"A404","Name":"内衣","Level":1}]
     */

    private int err;
    private String msg;
    /**
     * Code : A401
     * Name : T恤
     * Level : 1
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
        private int Level;

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

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }
    }
}
