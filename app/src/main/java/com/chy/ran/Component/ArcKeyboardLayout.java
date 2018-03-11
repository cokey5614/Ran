package com.chy.ran.Component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.chy.ran.Bean.KeyZone;
import com.chy.ran.Manager.ArcInfoManager;
import com.chy.ran.Manager.ArcStatManger;
import com.chy.ran.Service.ArcServiceHYRHandler;
import com.chy.ran.Stat.KeyboardStat;
import com.chy.ran.Util.LogUtil;


/**
 * Created by chhy on 2017/5/3.
 */

public class ArcKeyboardLayout extends LinearLayout
{
    private String inputModeFlat = "中";
    public static final String[] inputModeFlatArray = {"中", "ABC", "abc", "123"};
    public void setInputModeFlat(String value){this.inputModeFlat = value;}


    Paint paintInputMode = null;
    @Override
    protected void onDraw(Canvas mCanvas)
    {
        super.onDraw(mCanvas);

        LogUtil.d(this.getClass().getName(), "begin ArcKeyboardLayout.onDraw");

        KeyboardStat keyStat = ArcStatManger.keyboardStats[ArcServiceHYRHandler.instance().getInputMode()];
        keyStat.InitOneKeyboardStat(mCanvas);

        boolean isLeftHand = ArcInfoManager.isLeftHand();
        float width = ArcServiceHYRHandler.instance().getArcKeyBoardLayoutWidth();
        int fixedX = isLeftHand ? (int)width - 100 : 100;
        int fixedY_InputMode = 100;
        if (null == paintInputMode) {
            paintInputMode = new Paint();
            paintInputMode.setAntiAlias(true);
            paintInputMode.setStyle(Paint.Style.STROKE);//FILL
            paintInputMode.setColor(Color.parseColor("#000000"));
            paintInputMode.setAlpha(200);
            paintInputMode.setTextSize(40);
        }
        mCanvas.drawText(inputModeFlat, fixedX, fixedY_InputMode, paintInputMode);

        LogUtil.d(this.getClass().getName(), "end ArcKeyboardLayout.onDraw");
    }


    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
    }


    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(this.getClass().getName(), "begin ArcKeyboardLayout.onTouchEvent");

        KeyboardStat keyStat = ArcStatManger.keyboardStats[ArcServiceHYRHandler.instance().getInputMode()];
        final KeyZone[][] lister = keyStat.getListKeyZone();
        keyStat.setLayout(this);
        keyStat.OntouchKeyboard(event, lister);

        LogUtil.d(this.getClass().getName(), "end ArcKeyboardLayout.onTouchEvent");
        return true;
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        LogUtil.d(this.getClass().getName(), "begin ArcKeyboardLayout.onMeasure");
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        LogUtil.d(this.getClass().getName(), "end ArcKeyboardLayout.onMeasure");
    }
    public ArcKeyboardLayout(Context context) {
        super(context);
    }
    public ArcKeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ArcKeyboardLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ArcKeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
