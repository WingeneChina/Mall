package cn.wingene.mallxm.display.home.setting.data;

/**
 * Created by wangcq on 2017/9/5.
 * 获取版本信息model
 */

public class VersionModel {
    /**
     * err : 0
     * msg : 获取Android版本
     * data : {"VersionCode":1,"VersionName":"1.0.1","DownloadUrl":"http://img.usoft100.com/upload/android/1.01.apk",
     * "VersionData":"2017-09-05","Reason":"升级内容"}
     */

    private int err;
    private String msg;
    /**
     * VersionCode : 1
     * VersionName : 1.0.1
     * DownloadUrl : http://img.usoft100.com/upload/android/1.01.apk
     * VersionData : 2017-09-05
     * Reason : 升级内容
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
        private int VersionCode;
        private String VersionName;
        private String DownloadUrl;
        private String VersionData;
        private String Reason;

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int VersionCode) {
            this.VersionCode = VersionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String VersionName) {
            this.VersionName = VersionName;
        }

        public String getDownloadUrl() {
            return DownloadUrl;
        }

        public void setDownloadUrl(String DownloadUrl) {
            this.DownloadUrl = DownloadUrl;
        }

        public String getVersionData() {
            return VersionData;
        }

        public void setVersionData(String VersionData) {
            this.VersionData = VersionData;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }
    }
}
