package com.chy.ran.Stat;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.chy.ran.Bean.KeyZone;

/**
 * Created by chhy on 2018/2/25.
 */

public class KeyboardStat {
    public static final String ENTER        = "ENT";
    public static final String SPACE        = "space";
    public static final String CROSS        = "#";
    public static final String BLANK        = "";

    protected String[][] keyValue = null;

    protected Handler OneKeyboardStatHandler = null;

    public void SetOneKeyboardStatHandler(Handler OneKeyboardStatHandler) {
        this.OneKeyboardStatHandler = OneKeyboardStatHandler;
    }

    public void InitOneKeyboardStat(Canvas mCanvas)
    {
        // 委托出去
        this.OneKeyboardStatHandler.InitOneKeyboardStat(mCanvas);
    }

    public void OntouchKeyboard(MotionEvent event, KeyZone[][] lister)
    {
        // 委托出去
        this.OneKeyboardStatHandler.OnTouchKeyboard(event, lister);
    }

    public void init(int keyBoardWidth, int keyBoardHeigh)
    {
        // 委托出去
        this.OneKeyboardStatHandler.init(keyValue, keyBoardWidth, keyBoardHeigh);
    }

    public String getKeyZoneValue(float x, float y, float keyBoardWidth, float keyBoardHeigh)
    {
        return this.OneKeyboardStatHandler.getKeyZoneValue(x, y, keyBoardWidth, keyBoardHeigh);
    }

    public KeyZone[][] getListKeyZone()
    {
        return this.OneKeyboardStatHandler.getListKeyZone();
    }

    public void setLayout(View arcKeyboardLayout) {
        this.OneKeyboardStatHandler.setLayout(arcKeyboardLayout);
    }
}
