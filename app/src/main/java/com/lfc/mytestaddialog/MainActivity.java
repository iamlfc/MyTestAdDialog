package com.lfc.mytestaddialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonme;
    private Button buttondemo;
    private List<AdInfo> advList = null;
    AdManager adManager = null;
    private boolean isshow = true; // 设置开关 避免 重复 显示 弹窗视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initdata();
    }

    private void initdata() {
        advList = new ArrayList<>();
        AdInfo adInfo = new AdInfo();
        adInfo.setAdId("0");
//        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage1.png");
        adInfo.setActivityImg("http://4493bz.1985t.com/uploads/allimg/150413/3-150413152624.jpg");
//        adInfo.setActivityImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1479059812,471001669&fm=11&gp=0.jpg");
        advList.add(adInfo);

        adInfo = new AdInfo();
//        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage2.png");
        adInfo.setActivityImg("http://image.tianjimedia.com/uploadImages/2012/229/4K288XCY7OJ0.jpg");
        adInfo.setAdId("1");
        advList.add(adInfo);

        adManager = new AdManager(MainActivity.this, advList);
    }

    private void initView() {

        buttonme = (Button) findViewById(R.id.buttonme);
        buttondemo = (Button) findViewById(R.id.buttondemo);
        buttonme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adManager.dismissAdDialog();
//                adManager.
                if (!isshow) {
                    return;
                }
                isshow = false;
                adManager.setOverScreen(true)//设置弹窗背景是否覆盖全屏幕
                        .setPageTransformer(new DepthPageTransformer())//设置ViewPager滑动动画效果
                        .setPadding(10)//设置弹窗距离屏幕两侧的距离（单位dp）
                        .setWidthPerHeight(0.75f)//设置弹窗的宽高比
//                        .setDialogCloseable(false)//设置弹窗关闭图标是否可见
                        .setOnImageClickListener(new AdManager.OnImageClickListener() {
                            @Override
                            public void onImageClick(View view, AdInfo advInfo) {
                                Toast.makeText(MainActivity.this, advInfo.getAdId() + "号美女！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnCloseClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isshow = true;
                            }
                        })

                        .showAdDialog(AdConstant.ANIM_LEFT_TO_RIGHT);// 链式结构中  最后在写这一句;


            }
        });

        buttondemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Demo.class));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!isshow)
            {
//                 返回键 结束掉 视图
                if (adManager != null) {
                    adManager.dismissAdDialog();//  好像没有调用 setOnCloseClickListener 方法
                    isshow = true;
                }

            }else {

                onBackPressed();
            }
        }
        return false;
    }
}
