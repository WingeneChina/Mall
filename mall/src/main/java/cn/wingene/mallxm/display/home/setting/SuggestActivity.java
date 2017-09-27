package cn.wingene.mallxm.display.home.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mallxf.adapter.RecycleViewImgAdapter;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.util.GlideImageLoader;

public class SuggestActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {
    private List<ImageItem> imgItemList = new ArrayList<>();

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private final int MAX_IMG_COUNT = 8;
    private ImageView backIcon;
    private TextView titleV;
    private EditText suggestEditV;
    private ImageView addSuggestV;
    private RecyclerView suggestRecyclerV;
    private TextView suggestCommitV;
    private RecycleViewImgAdapter mRecycleViewImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        initViews();
        initEvent();
        initSuggestImgRecyclerV();
        initImagePicker();

    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        suggestEditV = (EditText) findViewById(R.id.suggestEditV);
        addSuggestV = (ImageView) findViewById(R.id.addSuggestV);
        suggestRecyclerV = (RecyclerView) findViewById(R.id.suggestRecyclerV);
        suggestCommitV = (TextView) findViewById(R.id.suggestCommitV);
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        addSuggestV.setOnClickListener(this);
        suggestCommitV.setOnClickListener(this);

    }

    private void initSuggestImgRecyclerV() {
        mRecycleViewImgAdapter = new RecycleViewImgAdapter(imgItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        suggestRecyclerV.setLayoutManager(linearLayoutManager);

        suggestRecyclerV.setAdapter(mRecycleViewImgAdapter);

        mRecycleViewImgAdapter.setItemImageClickListener(new RecycleViewImgAdapter.ItemImageClickListener() {
            @Override
            public void onItemImageClick(int position) {
                //打开预览
                Intent intentPreview = new Intent(SuggestActivity.this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) mRecycleViewImgAdapter
                        .getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();

                break;
            case R.id.addSuggestV:
                Log.e(this.getClass().getName(), "点击添加意见图片 ");
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(MAX_IMG_COUNT - imgItemList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true);
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(MAX_IMG_COUNT);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    /**
     * 提交意见反馈
     */
    private void commitSuggestImage() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                imgItemList.addAll(images);
                mRecycleViewImgAdapter.setImgItemList(imgItemList);
                mRecycleViewImgAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_IMAGE_ITEMS);
                imgItemList.clear();
                imgItemList.addAll(images);
                mRecycleViewImgAdapter.setImgItemList(imgItemList);
                mRecycleViewImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {

    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
