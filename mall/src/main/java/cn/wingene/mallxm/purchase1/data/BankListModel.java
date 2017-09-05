package cn.wingene.mallxm.purchase1.data;

import java.util.List;

/**
 * Created by wangcq on 2017/9/5.
 * 银行卡列表返回模型
 */

public class BankListModel {
    /**
     * err : 0
     * msg : 进入银行卡成功！
     * act : null
     * data : {"BankTypeList":[{"Id":"1","Name":"中国银行"},{"Id":"2","Name":"建设银行"},{"Id":"3","Name":"农业银行"}],
     * "BankBack":{"Id":1,"IsDefault":1,"BankType":1,"BankTypeDesp":"中国银行","BankAccount":"林泉",
     * "BankCardNo":"320125856565125845","OpenBank":"福清分行"}}
     */

    private int err;
    private String msg;
    private Object act;
    /**
     * BankTypeList : [{"Id":"1","Name":"中国银行"},{"Id":"2","Name":"建设银行"},{"Id":"3","Name":"农业银行"}]
     * BankBack : {"Id":1,"IsDefault":1,"BankType":1,"BankTypeDesp":"中国银行","BankAccount":"林泉",
     * "BankCardNo":"320125856565125845","OpenBank":"福清分行"}
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
        /**
         * Id : 1
         * IsDefault : 1
         * BankType : 1
         * BankTypeDesp : 中国银行
         * BankAccount : 林泉
         * BankCardNo : 320125856565125845
         * OpenBank : 福清分行
         */

        private BankBackBean BankBack;
        /**
         * Id : 1
         * Name : 中国银行
         */

        private List<BankTypeListBean> BankTypeList;

        public BankBackBean getBankBack() {
            return BankBack;
        }

        public void setBankBack(BankBackBean BankBack) {
            this.BankBack = BankBack;
        }

        public List<BankTypeListBean> getBankTypeList() {
            return BankTypeList;
        }

        public void setBankTypeList(List<BankTypeListBean> BankTypeList) {
            this.BankTypeList = BankTypeList;
        }

        public static class BankBackBean {
            private String Id;
            private int IsDefault;
            private int BankType;
            private String BankTypeDesp;
            private String BankAccount;
            private String BankCardNo;
            private String OpenBank;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public int getIsDefault() {
                return IsDefault;
            }

            public void setIsDefault(int IsDefault) {
                this.IsDefault = IsDefault;
            }

            public int getBankType() {
                return BankType;
            }

            public void setBankType(int BankType) {
                this.BankType = BankType;
            }

            public String getBankTypeDesp() {
                return BankTypeDesp;
            }

            public void setBankTypeDesp(String BankTypeDesp) {
                this.BankTypeDesp = BankTypeDesp;
            }

            public String getBankAccount() {
                return BankAccount;
            }

            public void setBankAccount(String BankAccount) {
                this.BankAccount = BankAccount;
            }

            public String getBankCardNo() {
                return BankCardNo;
            }

            public void setBankCardNo(String BankCardNo) {
                this.BankCardNo = BankCardNo;
            }

            public String getOpenBank() {
                return OpenBank;
            }

            public void setOpenBank(String OpenBank) {
                this.OpenBank = OpenBank;
            }
        }

        public static class BankTypeListBean {
            private String Id;
            private String Name;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
