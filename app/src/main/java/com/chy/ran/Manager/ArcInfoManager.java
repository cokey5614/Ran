package com.chy.ran.Manager;

import com.chy.ran.Stat.KeyboardStat;
import com.chy.ran.Util.LogUtil;

public class ArcInfoManager {

    public static final String BACK_SPACE   = "←";
    public static final String ENTER        = "ENT";
    public static final String SPACE        = "space";
    public static final int inputMode_CN      = 0;

    public static ArcInfoManager getInstance() {
        return new ArcInfoManager();
    }

    public void reInit(float _keyBoardWidth, float _keyBoardHeigh, boolean _isLeftHander, int _inputMode)
    {
        inputMode    = _inputMode;
        isLeftHand = _isLeftHander;

        init((int)_keyBoardWidth, (int)_keyBoardHeigh);
    }
    public void init(int keyBoardWidth, int keyBoardHeigh)
    {
        LogUtil.d(this.getClass().getName(), "begin init:inputMode=" + inputMode + ";Width=" + keyBoardWidth + ";Heigh=" + keyBoardHeigh);
        KeyboardStat keyStat = ArcStatManger.keyboardStats[inputMode];
        keyStat.init(keyBoardWidth, keyBoardHeigh);
        LogUtil.d(this.getClass().getName(), "end init" + inputMode);
    }

    private static int inputMode         = 0;//0:chinese 1:english 2:english_CAPS 3:number
    public static int getInputMode() {
		return inputMode;
	}
    public static int setInputMode(int inputMode) {
		return ArcInfoManager.inputMode = inputMode;
	}

    public static boolean isLeftHand() {
		return isLeftHand;
	}
    public void setIsLeftHand(boolean LeftHander)
    {
        isLeftHand = LeftHander;
    }
    private static boolean isLeftHand = false;

    public static final int keyboardLineNum = 6;
    public static final int keyboardColNum = 11;

    public  static int UnusedLength       = 0;
}