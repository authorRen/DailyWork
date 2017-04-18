package com.caiyi.dailywork.compant;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.caiyi.dailywork.R;

public class SpannedActivity extends BaseActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    TextView tv8;
    TextView tv9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanned);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);

        addUrlSpan();
        addBackColorSpan();
        addForeColorSpan();
        addFontSpan();
        addStyleSpan();
        addStrikeSpan();
        addUnderLineSpan();
        addImageSpan();
        addConbine();
    }

    /**混合 */
    private void addConbine() {
        SpannableStringBuilder spannable = new SpannableStringBuilder("组合运用啊");
        CharacterStyle span1 = new BackgroundColorSpan(Color.BLUE);
        CharacterStyle span2 = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(span1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span2, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv9.setText(spannable);
    }

    /**图片 */
    private void addImageSpan() {
        SpannableString spanString = new SpannableString(" ");
        Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv8.setText(spanString);
    }

    /**下划线 */
    private void addUnderLineSpan() {
        SpannableString spanString = new SpannableString("下划线");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv7.setText(spanString);
    }

    /**删除线 */
    private void addStrikeSpan() {
        SpannableString spanString = new SpannableString("删除线");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv6.setText(spanString);
    }

    /**粗体斜体 */
    private void addStyleSpan() {
        SpannableString spanString = new SpannableString("BIBI");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv5.setText(spanString);
    }

    /**字体大小 */
    private void addFontSpan() {
        SpannableString spanString = new SpannableString("36号字体");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(36);
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv4.setText(spanString);
    }

    /**文字颜色 */
    private void addForeColorSpan() {
        SpannableString spanString = new SpannableString("字体色");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv3.setText(spanString);
    }

    /**文字背景颜色 */
    private void addBackColorSpan() {
        SpannableString spanString = new SpannableString("背景色");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.BLUE);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv2.setText(spanString);
    }

    /** 超链接 */
    private void addUrlSpan() {
        SpannableString spanString = new SpannableString("超链接");
        URLSpan span = new URLSpan("tel:17317375253");
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(spanString);
    }
}
