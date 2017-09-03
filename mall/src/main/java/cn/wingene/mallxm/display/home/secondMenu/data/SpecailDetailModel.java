package cn.wingene.mallxm.display.home.secondMenu.data;

/**
 * Created by wangcq on 2017/9/3.
 */

public class SpecailDetailModel {

    /**
     * err : 0
     * msg : 获取专题详情成功
     * act : null
     * data : {"Id":1,"Title":"上海迪士尼乐园","Click":2,"From":"迪士尼乐园","CreateTime":"2017-08-18 17:15","Content":"<p><img
     * src=\"http://img.usoft100.com/upload/image/20170818/1503047750467652.jpg\"
     * title=\"5762e1cbNe0ddd104.jpg\"/><\/p><p><img src=\"http://img
     * .usoft100.com/upload/image/20170818/1503047750870692.jpg\" title=\"57830f1aN335b44a4.jpg\"/><\/p><p><img
     * src=\"http://img.usoft100.com/upload/image/20170818/1503047750787688.jpg\" title=\"577273e5Nf56c18ce
     * .jpg\"/><\/p><p><br/><\/p>","Region":"福建省福州市鼓楼区","RegionCode":"350102","Lat":"26.092265","Lng":"119.313135"}
     */

    private int err;
    private String msg;
    private Object act;
    /**
     * Id : 1
     * Title : 上海迪士尼乐园
     * Click : 2
     * From : 迪士尼乐园
     * CreateTime : 2017-08-18 17:15
     * Content : <p><img src="http://img.usoft100.com/upload/image/20170818/1503047750467652.jpg"
     * title="5762e1cbNe0ddd104.jpg"/></p><p><img src="http://img
     * .usoft100.com/upload/image/20170818/1503047750870692.jpg" title="57830f1aN335b44a4.jpg"/></p><p><img
     * src="http://img.usoft100.com/upload/image/20170818/1503047750787688.jpg" title="577273e5Nf56c18ce
     * .jpg"/></p><p><br/></p>
     * Region : 福建省福州市鼓楼区
     * RegionCode : 350102
     * Lat : 26.092265
     * Lng : 119.313135
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
        private int Id;
        private String Title;
        private String Click;
        private String From;
        private String CreateTime;
        private String Content;
        private String Region;
        private String RegionCode;
        private String Lat;
        private String Lng;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getClick() {
            return Click;
        }

        public void setClick(String Click) {
            this.Click = Click;
        }

        public String getFrom() {
            return From;
        }

        public void setFrom(String From) {
            this.From = From;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getRegionCode() {
            return RegionCode;
        }

        public void setRegionCode(String RegionCode) {
            this.RegionCode = RegionCode;
        }

        public String getLat() {
            return Lat;
        }

        public void setLat(String Lat) {
            this.Lat = Lat;
        }

        public String getLng() {
            return Lng;
        }

        public void setLng(String Lng) {
            this.Lng = Lng;
        }
    }
}
