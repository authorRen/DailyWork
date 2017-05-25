package com.caiyi.dailywork.compant;


import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/5/23.
 */

public class GlideActivity extends BaseActivity {

    private static final String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    private static final String urlGIF = "http://p1.pstatp.com/large/166200019850062839d3";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        imageView = (ImageView) findViewById(R.id.iv_glide);

        loadImage(imageView);
    }

    public void loadImage(ImageView imageView) {
        /**
         * Glide三步：1.先with 2.再load 3.最后into
         */
        Glide.with(this).load(urlGIF).into(imageView);
        /**
         *  load
         *  1.加载本地图片
         *      File file = getImagePath();
         *      Glide.with(this).load(file).into(imageView);
         *
         *  2.加载应用资源
         *      int resource = R.drawable.image;
         *      Glide.with(this).load(resource).into(imageView);
         *
         *  3.加载二进制流
         *      byte[] image = getImageByte();
         *      Glide.with(this).load(image).into(imageView);
         *
         *  4.加载Url对象
         *      Uri uriImage = getImageUri();
         *      Glide.with(this).load(urlImage).into(imageView);
         */
    }
}

