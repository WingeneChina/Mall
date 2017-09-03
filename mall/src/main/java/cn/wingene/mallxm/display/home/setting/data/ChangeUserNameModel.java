package cn.wingene.mallxm.display.home.setting.data;

/**
 * Created by wangcq on 2017/9/3.
 */

public class ChangeUserNameModel {
    /**
     * err : 0
     * msg : 编辑个人设置成功！
     */

    private int err;
    private String msg;

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
}
