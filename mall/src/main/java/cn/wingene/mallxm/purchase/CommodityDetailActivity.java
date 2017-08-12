package cn.wingene.mallxm.purchase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import cn.wingene.mall.R;
import cn.wingene.mall.util.TextViewHtmlLoader;
import cn.wingene.mallx.frame.FileUtil;
import cn.wingene.mallxf.adapter.ImagePagerAdapter;
import cn.wingene.mallxf.ui.MyBaseActivity;
import junze.widget.Tile;
import junze.widget.ViewPager;

import junze.androidxf.tool.HtmlLoader;

/**
 * Created by Wingene on 2017/8/6.
 */

public class CommodityDetailActivity extends MyBaseActivity {
    private ImagePagerAdapter mImagePagerAdapter;
    private List<String> urlList = new ArrayList<>();
    private TextViewHtmlLoader mHtmlLoad;

    private Tile tlBack;
    private ViewPager vpImage;
    private WebView wvDetail;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        vpImage = (ViewPager) super.findViewById(R.id.vp_image);
        wvDetail = (WebView) super.findViewById(R.id.wv_detail);
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
        initRollPager();
        try {
            InputStream inputStream = getAssets().open("temp/commodity_detail.txt");
            String htmlCode = FileUtil.readTextfile(inputStream,"utf-8");
            if(htmlCode!=null){
                mHtmlLoad = new TextViewHtmlLoader(this);
                HtmlLoader.loadWebViewByHtmlCode(this,wvDetail,getHtmlData(htmlCode));
//                mHtmlLoad.loadTextViewByHtmlCode(tvDetail,String.format("<html>%s</html>",htmlCode),null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        junze.android.R.dimen.
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHtmlLoad !=  null){
            mHtmlLoad.onDestory();
        }
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
