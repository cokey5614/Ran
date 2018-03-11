package com.chy.ran.Service;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import com.chy.ran.Component.ArcPinyinViewer;
import com.chy.ran.Bean.InputObj;
import com.chy.ran.Bean.KeyZone;
import com.chy.ran.Manager.ArcHYRManager;
import com.chy.ran.Manager.ArcInfoManager;
import com.chy.ran.Manager.ArcStatManger;
import com.chy.ran.Stat.KeyboardStat;
import com.chy.ran.Util.LogUtil;
import com.chy.ran.Util.RanRanContext;


/**
 * Created by chhy on 2018/2/25.
 */

public class ArcServiceHYRHandler {
    private static ArcServiceHYR serviceHYR         = null;
    private static ArcHYRManager aArcManager        = null;
    private static ArcPinyinViewer arcPinyinViewer  = null;
    private static LinearLayout mKeyBoardLayout     = null;

    public ArcServiceHYRHandler(ArcServiceHYR serviceHYR)
    {
        this.serviceHYR   = serviceHYR;
        this.aArcManager  = new ArcHYRManager();
        instance           = this;
        RanRanContext.init(serviceHYR);
    }


    public void restCursorCandi()
    {
        cursorCandi = 0;
    }

    public void handMsgToApp(String value) {
        if (mObj == null) mObj = new InputObj();
        mObj.freshOutput(InputObj.TP_INPUT2APP, new String[]{value});
        serviceHYR.onFinishInput();
    }

    public void chooseDefaultCandi() {
        if (null == candidateList || candidateList.length == 0) return;
        handMsgToApp(candidateList[0]);

        doAfterChooseCandi();
    }

    protected void doAfterChooseCandi() {
        setCandidateNull();
        String pinyin = this.arcPinyinViewer.getText().toString();
        LogUtil.i(this.getClass().getName(), " doAfterChooseCandi1:" + pinyin);
        this.arcPinyinViewer.setText(pinyin.length() > aArcManager.getCurcio() ?
                pinyin.substring(aArcManager.getCurcio()) : "");
        LogUtil.i(this.getClass().getName(), " doAfterChooseCandi2:" + this.arcPinyinViewer.getText().toString());
        if (this.arcPinyinViewer.getText().toString().length() > 0) {
            analyze(this.arcPinyinViewer.getText().toString());
        }
        else
        {
            serviceHYR.setCandidatesViewShown(false);
        }
        firstIndexInCandiList = 0;
    }

    public void chooseSpecialCandi() {
        if (null == candidateList || candidateList.length == 0) return;

        doAfterChooseCandi();
    }

    int firstIndexInCandiList = 0;// used for candidate mover index

    int cursorCandi = 0;
    public void smooze(int offset) {
        if (null == candidateList) return;

        KeyboardStat keyStat = ArcStatManger.keyboardStats[ArcServiceHYRHandler.instance().getInputMode()];
        KeyZone[][] lister = keyStat.getListKeyZone();
        int countNotNullCandi = 0;
        for (; countNotNullCandi < candidateList.length; countNotNullCandi++)
        {
            if (candidateList[countNotNullCandi] == null) break;
        }
        countNotNullCandi--;
        if (countNotNullCandi <= 11
                || ("".equals(lister[0][10].keyboardValue) && offset > 0)
                || (cursorCandi == 0 && offset < 0))
        {
            return;
        }

        String candiChar = null;
        for (int col = 0; col < 11; col++)
        {
            candiChar = (col + offset + cursorCandi >= 0
                    && col + offset + cursorCandi < countNotNullCandi) ? candidateList[col + offset + cursorCandi] : "";
            lister[0][col].keyboardValue = candiChar;
            lister[0][col].keyboardValue = candiChar;
            lister[0][col].keyboardValue = candiChar;
            lister[0][col].keyboardValue = candiChar;
        }
        cursorCandi += offset;
    }

    public void analyze(String pinyin) {
        aArcManager.analyze(pinyin.toUpperCase());
        candidateList = aArcManager.answerResponse();
        if (null == candidateList)
        {
            LogUtil.i(this.getClass().getName(), " result of analyze:" + pinyin + " is null");
            return;
        }
        LogUtil.i(this.getClass().getName(), " result of analyze:" + pinyin + " is " + candidateList[0] + " of "+ candidateList.length);

        KeyboardStat keyStat = ArcStatManger.keyboardStats[ArcServiceHYRHandler.instance().getInputMode()];
        KeyZone[][] lister = keyStat.getListKeyZone();

        for (int col = 0; col < lister[0].length && col < candidateList.length; col++)
        {
            if (null == lister[0][col]// || null == candidateList[col]
                    ) continue;
            if (null == candidateList[col]){
                candidateList[col]="";}
            lister[0][col].keyboardValue = candidateList[col];
        }
    }

    public int getFirstIndexInCandiList()
    {
        return firstIndexInCandiList;
    }
    public static String[] candidateList = null;
    public void setCandidateNull() {
        candidateList = null;// set item null more well

        KeyboardStat keystat = ArcStatManger.keyboardStats[ArcServiceHYRHandler.instance().getInputMode()];
        KeyZone[][] lister = keystat.getListKeyZone();

        for (int col = 0; col < lister[0].length; col++)
        {
            if (null == lister[0][col]) continue;
            lister[0][col].keyboardValue = "";
        }
    }

    public static String[] getCandidate(){ return candidateList;}

    public void InitArcInfo(int width, int height)
    {
        aArcManager.InitArcInfo(width, height);
    }

    public void InitAftCreateCandidatesView()
    {
        this.aArcManager.InitArcImeCore();
        this.mKeyBoardLayout = ArcServiceHYR.getKeyboardLayout();
        this.arcPinyinViewer    = ArcServiceHYR.getArcPinYinViewer();
    }

    protected InputObj mObj               = null;

    public void onFinishInput(InputConnection ic)
    {

        if (mObj == null || mObj.getTpo() ==  null || mObj.getTpo().length <= 0) return;

        if (mObj.getTp() != InputObj.TP_INPUT2APP) return;

        String targetStr = mObj.getTpo()[0];
        if (ArcInfoManager.BACK_SPACE.equals(targetStr))
        {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
        else if (ArcInfoManager.SPACE.equals(targetStr))
        {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
        }
        else if (ArcInfoManager.ENTER.equals(targetStr))
        {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        }
        else
        {
            mObj.freshOutput(mObj.getTp(), null);
            ic.commitText(targetStr, 1);
        }
    }

    private static ArcServiceHYRHandler instance = null;
    public static ArcServiceHYRHandler instance()
    {
        return instance;
    }

    public void setCandidatesViewShown(boolean value)
    {
        this.serviceHYR.setCandidatesViewShown(value);
    }

    public String getArcPinyinViewText()
    {
        return this.arcPinyinViewer.getText().toString();
    }

    public void setArcPinyinViewText(String value)
    {
        this.arcPinyinViewer.setText(value);
    }

    public boolean isLeftHand()
    {
        return aArcManager.isLeftHand();
    }

    public void setIsLeftHand(boolean value)
    {
        aArcManager.setIsLeftHand(value);
    }

    public void reInitArcHYRManager(int windowWith, int windowHeight, boolean b, int inputMode)
    {
        aArcManager.reInit(windowWith, windowHeight, b, inputMode);
    }
    public int getInputMode() {
        return ArcInfoManager.getInputMode();
    }
    public void setInputMode(int inputMode) {
        ArcInfoManager.setInputMode(inputMode);
    }

    public int getArcKeyBoardLayoutWidth() {
        return mKeyBoardLayout.getWidth();
    }
    public int getArcKeyBoardLayoutHeight() {
        return mKeyBoardLayout.getHeight();
    }
}
