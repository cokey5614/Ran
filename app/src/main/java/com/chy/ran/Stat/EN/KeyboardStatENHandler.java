package com.chy.ran.Stat.EN;


import android.graphics.Canvas;
import android.view.MotionEvent;

import com.chy.ran.Bean.KeyZone;
import com.chy.ran.Stat.Handler;

/**
 * Created by chhy on 2018/2/25.
 */

public class KeyboardStatENHandler extends Handler {
    @Override
    public boolean InitOneKeyboardStat(Canvas mCanvas)
    {
        super.InitOneKeyboardStat(mCanvas);
        return true;
    }

    @Override
    public boolean OnTouchKeyboard(MotionEvent event, final KeyZone[][] lister) {
        super.OnTouchKeyboard(event, lister);
        return false;
    }
}
