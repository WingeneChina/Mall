package cn.wingene.mallxm.purchase;

import android.os.Bundle;
import android.widget.FrameLayout;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
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
