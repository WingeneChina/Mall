package cn.wingene.mallxm.purchase1.data;

/**
 * Created by wangcq on 2017/9/5.
 * 提交提现结果
 */

public class CashCommitResultModel {
    /**
     * err : 0
     * msg : 游币提现成功
     * data : {"AmountPrice":101,"RealArrivalAmount":98.98,"CounterAmount":2.02,"BankBack":{"BankType":1,
     * "BankTypeDesp":"中国银行","BankAccount":"林泉","BankCardNo":"320125856565125845","OpenBank":"福清分行"}}
     */

    private int err;
    private String msg;
    /**
     * AmountPrice : 101
     * RealArrivalAmount : 98.98
     * CounterAmount : 2.02
     * BankBack : {"BankType":1,"BankTypeDesp":"中国银行","BankAccount":"林泉","BankCardNo":"320125856565125845",
     * "OpenBank":"福清分行"}
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
        private int AmountPrice;
        private double RealArrivalAmount;
        private double CounterAmount;
        /**
         * BankType : 1
         * BankTypeDesp : 中国银行
         * BankAccount : 林泉
         * BankCardNo : 320125856565125845
         * OpenBank : 福清分行
         */

        private BankBackBean BankBack;

        public int getAmountPrice() {
            return AmountPrice;
        }

        public void setAmountPrice(int AmountPrice) {
            this.AmountPrice = AmountPrice;
        }

        public double getRealArrivalAmount() {
            return RealArrivalAmount;
        }

        public void setRealArrivalAmount(double RealArrivalAmount) {
            this.RealArrivalAmount = RealArrivalAmount;
        }

        public double getCounterAmount() {
            return CounterAmount;
        }

        public void setCounterAmount(double CounterAmount) {
            this.CounterAmount = CounterAmount;
        }

        public BankBackBean getBankBack() {
            return BankBack;
        }

        public void setBankBack(BankBackBean BankBack) {
            this.BankBack = BankBack;
        }

        public static class BankBackBean {
            private int BankType;
            private String BankTypeDesp;
            private String BankAccount;
            private String BankCardNo;
            private String OpenBank;

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
