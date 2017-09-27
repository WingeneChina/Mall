package cn.wingene.mallxm.purchase;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.limecn.ghmall.R;

import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.purchase.ask.AskCartList;
import cn.wingene.mallxm.purchase.ask.AskCartList.Response;
import cn.wingene.mallxm.purchase.fragment.ShoppingCartFragment;

/**
 * Created by Wingene on 2017/8/13.
 */

public class ShoppingCartActivity extends MyBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initFragmentParentId(R.id.flyt_content);
        turntoFragment(ShoppingCartFragment.class,null);


    }
}
