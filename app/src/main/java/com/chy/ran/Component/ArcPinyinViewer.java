package com.chy.ran.Component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by chhy on 2017/4/20.
 */

@SuppressLint("AppCompatCustomView")
public class ArcPinyinViewer extends TextView {
    public ArcPinyinViewer(Context context) {
        super(context);
    }

    public ArcPinyinViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcPinyinViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArcPinyinViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
