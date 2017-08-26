package cn.wingene.mallxm.account.data;

/**
 * Created by wangcq on 2017/8/26.
 */

public class LoginModel {
    /**
     * err : 0
     * msg : 登录成功
     * data : {"UserId":1,"Nickname":"昵称","Phone":"13799999999","Grade":1,"GradeName":"普通会员",
     * "Avatar":"http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg"," VerifiCode ":"SWDEFRTF25",
     * " LoginTime ":"2017-07-20 12:20:20"," ShareTag ":" 1499164318637103"}
     */

    private int err;
    private String msg;
    /**
     * UserId : 1
     * Nickname : 昵称
     * Phone : 13799999999
     * Grade : 1
     * GradeName : 普通会员
     * Avatar : http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg
     * VerifiCode  : SWDEFRTF25
     * LoginTime  : 2017-07-20 12:20:20
     * ShareTag  :  1499164318637103
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

    /**
     * UserId : 1
     * Nickname : 昵称
     * Phone : 13799999999
     * Grade : 1
     * GradeName : 普通会员
     * Avatar : http://120.25.105.30:8080/upload/image/20170712/s_1499827969961321.jpg
     * VerifiCode  : SWDEFRTF25
     * LoginTime  : 2017-07-20 12:20:20
     * ShareTag  :  1499164318637103
     */
    public class DataBean {
        private int UserId;
        private String Nickname;
        private String Phone;
        private int Grade;
        private String GradeName;
        private String Avatar;
        private String VerifiCode;
        private String LoginTime;
        private String ShareTag;

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

        public String getVerifiCode() {
            return VerifiCode;
        }

        public void setVerifiCode(String verifiCode) {
            VerifiCode = verifiCode;
        }

        public String getLoginTime() {
            return LoginTime;
        }

        public void setLoginTime(String loginTime) {
            LoginTime = loginTime;
        }

        public String getShareTag() {
            return ShareTag;
        }

        public void setShareTag(String shareTag) {
            ShareTag = shareTag;
        }
    }


}