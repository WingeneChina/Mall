package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.ImagePagerAdapter;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import junze.widget.Tile;
import junze.widget.ViewPager;

import junze.android.util.FileUtil;
import junze.androidxf.tool.HtmlLoader;

/**
 * Created by Wingene on 2017/8/6.
 */

public class CommodityDetailActivity extends MyBaseActivity {
    private ImagePagerAdapter mImagePagerAdapter;
    private List<String> urlList = new ArrayList<>();

    private Tile tlBack;
    private ViewPager vpImage;
    private WebView wvDetail;
    private ImageView ivCart;
    private TextView tvBuy;
    private TextView tvAddCart;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        vpImage = (ViewPager) super.findViewById(R.id.vp_image);
        wvDetail = (WebView) super.findViewById(R.id.wv_detail);
        ivCart = (ImageView) super.findViewById(R.id.iv_cart);
        tvBuy = (TextView) super.findViewById(R.id.tv_buy);
        tvAddCart = (TextView) super.findViewById(R.id.tv_add_cart);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commdity_detail);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ivCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startShoppingCartActivity(getActivity());
            }
        });
        tvAddCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.startShoppingCartActivity(getActivity());
            }
        });
        initRollPager();
        loadWebData();


    }

    private void loadWebData() {
        String htmlCode = FileUtil.readTextFileFromAssets(getActivity(), "temp/commodity_detail.txt");
        if (htmlCode != null) {
            HtmlLoader.loadWebViewByHtmlCode(this, wvDetail, htmlCode, true);
        }
    }

    private void initRollPager() {
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895397&di=98518077960d23213c0aa954ca4dc156" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201504%2F22%2F1910368waa9k0z0z9y9c8a" +
                ".jpg");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895322&di=228c7770fb082ec620cf1f373649b161" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fimgcache.cjmx.com%2Ffilm%2F201608%2F20160830144736364.jpg");
        urlList.add("http://mpic.tiankong.com/ecc/3e3/ecc3e349338dbe58603cf270d9cd7c9c/640.jpg?x-oss-process=image" +
                "/resize,m_lfit,h_600,w_600/watermark,image_cXVhbmppbmcucG5n,t_90,g_ne,x_5,y_5");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895397&di=98518077960d23213c0aa954ca4dc156" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201504%2F22%2F1910368waa9k0z0z9y9c8a" +
                ".jpg");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895322&di=228c7770fb082ec620cf1f373649b161" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fimgcache.cjmx.com%2Ffilm%2F201608%2F20160830144736364.jpg");

        mImagePagerAdapter = new ImagePagerAdapter(urlList);
        vpImage.setAdapter(mImagePagerAdapter);

    }

}
