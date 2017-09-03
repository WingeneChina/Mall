package cn.wingene.mallxm.display.home.firstMenu.data;

/**
 * Created by wangcq on 2017/9/2.
 * 上传头像结果model
 */

public class HeadUpLoadModel {
    /**
     * err : 0
     * msg : 编辑个人设置成功！
     * data : {"Avatar":"http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg"}
     */

    private int err;
    private String msg;
    /**
     * Avatar : http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String Avatar;

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }
    }
}
