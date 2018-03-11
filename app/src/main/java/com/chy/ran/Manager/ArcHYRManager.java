package com.chy.ran.Manager;

/**
 * Created by chhy on 2018/2/25.
 */

public class ArcHYRManager {
    private static ArcInfoManager arcInfoManager         = null;// 键盘仪表管理
    private static ArcImeCoreManager aArcImeCoreManager  = null;// 输入法核心管理

    public ArcHYRManager()
    {
        if (null == arcInfoManager) {
            arcInfoManager = ArcInfoManager.getInstance();
        }

        if (null == aArcImeCoreManager) {
            aArcImeCoreManager = ArcImeCoreManager.getInstance();
        }
    }

    public void InitArcInfo(int width, int height)
    {
        arcInfoManager.init(width, height);
    }

    public void InitArcImeCore() {
        aArcImeCoreManager.init();
    }

    private boolean processRequest(String pinyin)
    {
        return aArcImeCoreManager.processRequest(pinyin);
    }

    public String[] answerResponse()
    {
        return aArcImeCoreManager.answerResponse();
    }

    public void analyze(String pinyin)
    {
        if (null == pinyin) return;
        processRequest(pinyin);
    }

    public int getCurcio() {
        return aArcImeCoreManager.getIndexOfCandicateSnapToInputView();
    }

    public boolean isLeftHand()
    {
        return arcInfoManager.isLeftHand();
    }

    public void setIsLeftHand(boolean value)
    {
        arcInfoManager.setIsLeftHand(value);
    }

    public void reInit(int windowWith, int windowHeight, boolean b, int inputMode)
    {
        arcInfoManager.reInit(windowWith, windowHeight, b, inputMode);
    }
}
