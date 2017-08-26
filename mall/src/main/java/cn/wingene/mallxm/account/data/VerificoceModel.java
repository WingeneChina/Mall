package cn.wingene.mallxm.account.data;

/**
 * Created by wangcq on 2017/8/26.
 */

public class VerificoceModel {

    /**
     * err : 0
     * msg : 获取成功
     * data :
     */

    private int err;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
