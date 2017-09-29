package cn.wingene.mallxm.display.home.fourthMenu.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/9/27.
 */

public class DriveModel implements Parcelable {
    public List<RecommendModel.DataBean.HeadMenuListBean> HeadMenuList;
    public List<RecommendModel.DataBean.BannerListBean> BannerList;
    public List<RecommendModel.DataBean.BannerListBean> MenuList;
    public List<RecommendModel.DataBean.RecommendBean.ProductListBean> ProductList;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.HeadMenuList);
        dest.writeList(this.BannerList);
        dest.writeList(this.MenuList);
        dest.writeList(this.ProductList);
    }

    public DriveModel() {
    }

    protected DriveModel(Parcel in) {
        this.HeadMenuList = new ArrayList<RecommendModel.DataBean.HeadMenuListBean>();
        in.readList(this.HeadMenuList, RecommendModel.DataBean.HeadMenuListBean.class.getClassLoader());
        this.BannerList = new ArrayList<RecommendModel.DataBean.BannerListBean>();
        in.readList(this.BannerList, RecommendModel.DataBean.BannerListBean.class.getClassLoader());
        this.MenuList = new ArrayList<RecommendModel.DataBean.BannerListBean>();
        in.readList(this.MenuList, RecommendModel.DataBean.BannerListBean.class.getClassLoader());
        this.ProductList = new ArrayList<RecommendModel.DataBean.RecommendBean.ProductListBean>();
        in.readList(this.ProductList, RecommendModel.DataBean.BrandBean.ProductListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DriveModel> CREATOR = new Parcelable.Creator<DriveModel>() {
        @Override
        public DriveModel createFromParcel(Parcel source) {
            return new DriveModel(source);
        }

        @Override
        public DriveModel[] newArray(int size) {
            return new DriveModel[size];
        }
    };
}
