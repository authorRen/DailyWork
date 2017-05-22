package com.caiyi.dailywork.compant;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/5/22.
 */

public class ShapeActivity extends BaseActivity {

    private TextView tvRectShape;
    private TextView tvRoundRectShape;
    private TextView tvOvalShape;
    private TextView tvarcShape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        tvRectShape = (TextView) findViewById(R.id.tv_rectShape);
        tvRoundRectShape = (TextView) findViewById(R.id.tv_roundRectShape);
        tvOvalShape = (TextView) findViewById(R.id.tv_ovalShape);
        tvarcShape = (TextView) findViewById(R.id.tv_arcShape);
        getRectShape(tvRectShape);

        getRoundRectShape(tvRoundRectShape);

        getOvalShape(tvOvalShape);

        getArcShape(tvarcShape);
    }

    private void getArcShape(View view) {
        ArcShape arcShape = new ArcShape(45, 270); //顺时针    开始角度45度，扫描的角度的270度，扇形
        ShapeDrawable shapeDrawable = new ShapeDrawable(arcShape);
        shapeDrawable.getPaint().setColor(Color.RED);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        view.setBackgroundDrawable(shapeDrawable);
    }

    private void getOvalShape(View view) {
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setColor(Color.RED);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        view.setBackgroundDrawable(shapeDrawable);
    }

    private void getRoundRectShape(View view) {
//        float[] outerRadii = {20,20,40,40,60,60,80,80}; //外矩形 左上、右下、右下、左下 圆角半径
        float[] outerRadii = {20,20,20,20,20,20,20,20}; //外矩形 左上、右下、右下、左下 圆角半径
        RectF inset = new RectF(100, 100, 100, 100); //内矩形距外矩形，左上角x,y的距离，右下角x,y的距离

        float[] innerRadii = {10,10,10,10,10,10,10,10}; //内矩形
//        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, innerRadii); //无内矩形
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii); //有内矩形

        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.MAGENTA);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        view.setBackgroundDrawable(shapeDrawable);
    }

    private void getRectShape(View view) {
        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(Color.RED);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL); //填充
        view.setBackgroundDrawable(shapeDrawable);
    }


}
