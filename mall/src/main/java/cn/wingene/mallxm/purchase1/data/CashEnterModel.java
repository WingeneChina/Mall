package cn.wingene.mallxm.purchase1.data;

/**
 * Created by wangcq on 2017/9/5.
 * 进入提现接口返回数据
 */

public class CashEnterModel {
    /**
     * err : 0
     * msg : 进入游币提现成功！
     * data : {"Amount":9999,"BankBack":{"Id":1,"IsDefault":1,"BankType":1,"BankTypeDesp":"中国银行","BankAccount":"林泉",
     * "BankCardNo":"320125856565125845","OpenBank":"福清分行"}}
     */

    private int err;
    private String msg;
    /**
     * Amount : 9999
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private double Amount;
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

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public BankBackBean getBankBack() {
            return BankBack;
        }

        public void setBankBack(BankBackBean BankBack) {
            this.BankBack = BankBack;
        }

        public static class BankBackBean {
            private int Id;
            private int IsDefault;
            private int BankType;
            private String BankTypeDesp;
            private String BankAccount;
            private String BankCardNo;
            private String OpenBank;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
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
    }
}
