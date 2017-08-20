package cn.wingene.mallxm.purchase;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import junze.widget.Tile;

/**
 * Created by Wingene on 2017/8/19.
 */

public class AddressAddActivity extends MyBaseActivity {
    private Tile tlBack;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        initComponent();
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
