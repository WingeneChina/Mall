package cn.wingene.mallxm.display.home.fiveMenu;

/**
 * Created by wangcq on 2017/8/29.
 * 个人中心接口返回
 */

public class UserInfoModel {
    /**
     * err : 0
     * msg : 获取个人中心成功！
     * act : null
     * data : {"UserId":1,"Nickname":"光合","Phone":"13950329150","Grade":0,"GradeName":"普通会员",
     * "Avatar":"http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg","Certification":0,
     * "Amount":0,"Integral":0}
     */

    private int err;
    private String msg;
    private Object act;
    /**
     * UserId : 1
     * Nickname : 光合
     * Phone : 13950329150
     * Grade : 0
     * GradeName : 普通会员
     * Avatar : http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg
     * Certification : 0
     * Amount : 0.0
     * Integral : 0
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
        private int UserId;
        private String Nickname;
        private String Phone;
        private int Grade;
        private String GradeName;
        private String Avatar;
        private int Certification;
        private double Amount;
        private int Integral;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String Nickname) {
            this.Nickname = Nickname;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public int getGrade() {
            return Grade;
        }

        public void setGrade(int Grade) {
            this.Grade = Grade;
        }

        public String getGradeName() {
            return GradeName;
        }

        public void setGradeName(String GradeName) {
            this.GradeName = GradeName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public int getCertification() {
            return Certification;
        }

        public void setCertification(int Certification) {
            this.Certification = Certification;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }
    }
}
