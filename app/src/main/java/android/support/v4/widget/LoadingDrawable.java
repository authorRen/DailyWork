package android.support.v4.widget;

import android.content.Context;
import android.view.View;

import com.caiyi.dailywork.R;


/**
 * MaterialProgressDrawable
 *
 * support v4 里面的MaterialProgressDrawable类只限包内使用，故单独写个继承
 *
 * @author CJL
 * @since 2015-10-27
 */
public class LoadingDrawable extends MaterialProgressDrawable {

    /**
     * Constructor
     * 
     * @param context
     *            Context
     * @param parent
     *            此Drawable所在的View
     */
    public LoadingDrawable(Context context, View parent) {
        super(context, parent);

        setColorSchemeColors(context.getResources().getIntArray(R.array.gjj_loading_colors));
        setAlpha(255); // SUPPRESS CHECKSTYLE
    }
}
