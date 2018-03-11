package com.chy.ran.Stat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.chy.ran.Bean.ArcZoneInfo;
import com.chy.ran.Bean.KeyZone;
import com.chy.ran.Component.ArcKeyboardLayout;
import com.chy.ran.Manager.ArcInfoManager;
import com.chy.ran.Service.ArcServiceHYRHandler;
import com.chy.ran.Util.ConstantFunc;
import com.chy.ran.Util.LogUtil;

/**
 * Created by chhy on 2018/2/25.
 */

public class Handler {
    /// init board func
//    private static boolean Init = false;
    private static Paint paint = null;
    private static Path  path  = null;
    public boolean InitOneKeyboardStat(Canvas mCanvas)
    {
        float windowWith = ArcServiceHYRHandler.instance().getArcKeyBoardLayoutWidth();
        LogUtil.d(this.getClass().getName(), "begin InitOneKeyboardStat");

        KeyZone[][] lister = getListKeyZone();
        KeyZone kz = null;
        int tmpi = 0;
        int tmpRow = 0;
        for (int i = 0; i < 43; i++) {

            if (i <= 10) {
                tmpi = i;
                tmpRow = 0;
            } else if (i <= 20) {
                tmpi = i - 11;
                tmpRow = 1;
            } else if (i <= 29) {
                tmpi = i - 21;
                tmpRow = 2;
            } else if (i <= 37) {
                tmpi = i - 30;
                tmpRow = 3;
            } else if (i <= 41) {
                tmpi = i - 38;
                tmpRow = 4;
            } else {
                tmpi = i - 42;
                tmpRow = 5;
            }
            kz = lister[tmpRow][tmpi];
            if (kz == null) continue;

            //if (!Init)
            {
                //i means cols index,getDived means how many cols
                ArcZoneInfo arcZoneInfo = new ArcZoneInfo(tmpRow, ConstantFunc.getDived(tmpRow),
                        ConstantFunc.getOutRadio(tmpRow, windowWith), ConstantFunc.getInerRadio(tmpRow, windowWith));
                kz.setArcZoneInfo(arcZoneInfo);
                if (arcZoneInfo == null || kz == null || null == mCanvas) {
                    continue;
                }

                if (null == paint) {
                    paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE);//FILL
                    paint.setColor(Color.parseColor("#000000"));
                    paint.setAlpha(200);
                }
                path = new Path();//408080
                if (i < 42) {
                    if (0 == tmpRow) {
                        path.moveTo(kz.getLeft().getX(), kz.getLeft().getY());
                        path.lineTo(kz.getUp().getX(), kz.getUp().getY());
                        path.lineTo(kz.getRight().getX(), kz.getRight().getY());
                        path.lineTo(kz.getDown().getX(), kz.getDown().getY());
                        path.close();
                    } else if (4 > tmpRow) {
                        path.moveTo(kz.getLeft().getX(), kz.getLeft().getY());
                        path.lineTo(lister[tmpRow - 1][tmpi].getRight().getX(), lister[tmpRow - 1][tmpi].getRight().getY());
                        path.lineTo(kz.getUp().getX(), kz.getUp().getY());
                        path.lineTo(kz.getRight().getX(), kz.getRight().getY());
                        path.lineTo(kz.getDown().getX(), kz.getDown().getY());

                        path.close();
                    } else {
                        path.moveTo(kz.getLeft().getX(), kz.getLeft().getY());
                        path.lineTo(lister[tmpRow - 1][tmpi * 2].getRight().getX(), lister[tmpRow - 1][tmpi * 2].getRight().getY());
                        path.lineTo(kz.getUp().getX(), kz.getUp().getY());
                        path.lineTo(kz.getRight().getX(), kz.getRight().getY());
                        path.lineTo(kz.getDown().getX(), kz.getDown().getY());

                        path.close();
                    }
                }
                mCanvas.drawPath(path, paint);
                paint.setTextSize(40);

                //Init = true;
            }

            String value = kz.keyboardValue;
            if(i <= 10)
            {
                paint.setTextSize(30);
                boolean isLeftHander = ArcInfoManager.isLeftHand();
                if (!isLeftHander)
                {
                    mCanvas.drawText(ArcInfoManager.SPACE.equals(value) ? "spc" : value,
                            (kz.getLeft().getX() + kz.getRight().getX()) / (i <= 5 ? (i <= 3 ? (float) 3.3 : (float) 2.5) : (float) 2.2 ) ,
                            (kz.getUp().getY() + kz.getDown().getY()) / (float) 2 * (float) (1.05),
                            paint);
                }
                else
                {
                    mCanvas.drawText(ArcInfoManager.SPACE.equals(value) ? "spc" : value,
                            (kz.getLeft().getX() + kz.getRight().getX()) / (float)2.1,
                            (kz.getUp().getY() + kz.getDown().getY()) / (float) 2 * (float) (1.05),
                            paint);
                }
            }
            // start point
            else if (i != 42)
            {
                mCanvas.drawText(ArcInfoManager.SPACE.equals(value) ? "spc" : value,
                        (kz.getLeft().getX() + kz.getRight().getX()) / (float) 2,
                        (kz.getUp().getY() + kz.getDown().getY()) / (float) 2 * (float) (1.025),
                        paint);
            }
            else
            {
                mCanvas.drawText(ArcInfoManager.SPACE.equals(value) ? "spc" : value,
                        (kz.getLeft().getX() + kz.getRight().getX()) / (float) 2.1,
                        (kz.getUp().getY() + kz.getDown().getY()) / (float) 2 * (float) (1.05),
                        paint);
            }
        }

        return true;
    }


    /// init key zone list
    private static KeyZone[][] lstKeyZone = new KeyZone[ArcInfoManager.keyboardLineNum][ArcInfoManager.keyboardColNum];
    public KeyZone[][] getListKeyZone()
    {
        return lstKeyZone;
    }
    public void init(String[][] keyValue, int keyBoardWidth, int keyBoardHeigh)
    {
        boolean isLeftHand = ArcServiceHYRHandler.instance().isLeftHand();

        ConstantFunc.initLstKeyZone(keyValue, keyBoardWidth, keyBoardHeigh, getListKeyZone(), isLeftHand);
    }


    /// touch event
    private View layout = null;
    public void setLayout(View value){this.layout = value;}
    private  static float downY = 0;
    private  static float downX = 0;
    private  static float upY = 0;
    private  static float upX = 0;
    private  static KeyZone downArcZoneInfo = null;
    private  static KeyZone upArcZoneInfo = null;
    public boolean OnTouchKeyboard(MotionEvent event, final KeyZone[][] lister)
    {
        float windowWith = ArcServiceHYRHandler.instance().getArcKeyBoardLayoutWidth();
        float windowHeight = ArcServiceHYRHandler.instance().getArcKeyBoardLayoutHeight();
        boolean isLeftHand = ArcServiceHYRHandler.instance().isLeftHand();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = windowHeight - event.getY();
                downX = isLeftHand? event.getX() : windowWith - event.getX();
                downArcZoneInfo = ConstantFunc.getDownZone(downX, downY, (int)windowWith, lister);
                break;
            case MotionEvent.ACTION_UP:
                upY = windowHeight - event.getY();
                upX = isLeftHand? event.getX() : windowWith - event.getX();
                upArcZoneInfo = ConstantFunc.getDownZone(upX, upY, (int)windowWith, lister);
                OnUpMotion(windowWith, (int) windowHeight, isLeftHand);

                ((ArcKeyboardLayout)layout).invalidate();
                break;
        }

        return true;
    }

    /**
     * 响应抬起的动作
     * @param windowWith
     * @param windowHeight
     * @param isLeftHand
     */
    protected void OnUpMotion(float windowWith, int windowHeight, boolean isLeftHand) {
        if (upArcZoneInfo == null)//输入法面板空白区域，判定为左右手交换
        {
            SwitchHand((int) windowWith, windowHeight, isLeftHand);
        }
        else if (downArcZoneInfo.marcZoneInfo.arcOutDivdCount == upArcZoneInfo.marcZoneInfo.arcOutDivdCount
                && downArcZoneInfo.getLeft().getX() == upArcZoneInfo.getLeft().getX()
                && downArcZoneInfo.getLeft().getY() == upArcZoneInfo.getLeft().getY())
        // 按下和抬起的位置没有发生变化(以单元格为标准)
        {
            OnClickValidCellZone();
        }
        else
        {
            float disY = Math.abs(upY - downY);
            float disX = Math.abs(upX - downX);
            if (disY > disX && disY > 0)
            {
                ArcServiceHYRHandler.instance().smooze(upX > downX ? 4 : -4);// 中文待选词区域滑动
            }
            else if (disX > windowWith / (float)7)//判断为输入法模式切换
            {
                SwitchIME((int) windowWith, windowHeight, isLeftHand);
            }
        }
    }

    /**
     * 输入法面板空白区域，判定为左右手交换
     * @param windowWith
     * @param windowHeight
     * @param isLeftHand
     */
    private void SwitchHand(int windowWith, int windowHeight, boolean isLeftHand) {
        ArcServiceHYRHandler.instance().setArcPinyinViewText("");
        ArcServiceHYRHandler.instance().setCandidatesViewShown(false);
        ArcServiceHYRHandler.instance().setCandidateNull();
        ArcServiceHYRHandler.instance().restCursorCandi();

        ArcServiceHYRHandler.instance().setIsLeftHand(!isLeftHand);

        ArcServiceHYRHandler.instance().reInitArcHYRManager(//因为要左右交换，所以isLeftHand取反
                windowWith, windowHeight, !isLeftHand, ArcServiceHYRHandler.instance().getInputMode());
    }

    /**
     * 判断为输入法模式切换
     * @param windowWith
     * @param windowHeight
     * @param isLeftHand
     */
    private void SwitchIME(int windowWith, int windowHeight, boolean isLeftHand) {
        int dis = upX - downX > 0 ? 1 : -1;
        int input = ArcServiceHYRHandler.instance().getInputMode() + dis;
        input = input == -1 ? (ArcKeyboardLayout.inputModeFlatArray.length - 1) : input;
        input = input % ArcKeyboardLayout.inputModeFlatArray.length;
        ArcServiceHYRHandler.instance().setInputMode(input);
        ((ArcKeyboardLayout)layout).setInputModeFlat(ArcKeyboardLayout.inputModeFlatArray[input]);

        ArcServiceHYRHandler.instance().setArcPinyinViewText("");
        ArcServiceHYRHandler.instance().setCandidatesViewShown(false);
        ArcServiceHYRHandler.instance().setCandidateNull();
        ArcServiceHYRHandler.instance().restCursorCandi();

        ArcServiceHYRHandler.instance().reInitArcHYRManager(
                windowWith, windowHeight, isLeftHand, ArcServiceHYRHandler.instance().getInputMode());
    }

    /**
     * 按下和抬起的位置没有发生变化(以单元格为标准)
     */
    protected void OnClickValidCellZone() {
        if (upArcZoneInfo.marcZoneInfo.arcOutKeyBoarddIndex == 0)// 点击的是自定义带选词区域，不管任何输入法，直接传递给app
        {
            if (upArcZoneInfo.keyboardValue != null) ArcServiceHYRHandler.instance().handMsgToApp(upArcZoneInfo.keyboardValue);

            ArcServiceHYRHandler.instance().chooseSpecialCandi();
            ArcServiceHYRHandler.instance().restCursorCandi();
        }
        else//否则按实际情况，如果是中文输入法，需要词法分析，否则直接传递给app
        {
            String value = upArcZoneInfo.keyboardValue;

            String curPinyin = ArcServiceHYRHandler.instance().getArcPinyinViewText();
            LogUtil.i(this.getClass().getName(), "Pinyin=" + curPinyin + ";keyboardValue=" + value);

            doWithInputKey(value, curPinyin);
            ArcServiceHYRHandler.instance().restCursorCandi();
        }
    }

    private void doWithInputKey(String keyValue, String curPinyin) {
        if (ArcInfoManager.ENTER.equals(keyValue) || ArcInfoManager.SPACE.equals(keyValue))
        {
            doWithEnterKey(keyValue, curPinyin);
        }
        else if(ArcInfoManager.BACK_SPACE.equals(keyValue))
        {
            doWithBackSpace(keyValue, curPinyin);
        }
        else
            // choose input char
            if(ArcInfoManager.inputMode_CN == ArcServiceHYRHandler.instance().getInputMode() && ConstantFunc.isOneOf26Char(keyValue))
            {
                ArcServiceHYRHandler.instance().setArcPinyinViewText(curPinyin + keyValue);
                String pinyinValue = ArcServiceHYRHandler.instance().getArcPinyinViewText();
                if (pinyinValue.length() > 0) {
                    ArcServiceHYRHandler.instance().setCandidatesViewShown(true);
                    ArcServiceHYRHandler.instance().analyze(pinyinValue);
                }
            }
            else
            {
                ArcServiceHYRHandler.instance().handMsgToApp(keyValue);
            }
    }

    private void doWithBackSpace(String keyValue, String curPinyin) {
        if (null == curPinyin || curPinyin.length() == 0)
        {
            ArcServiceHYRHandler.instance().handMsgToApp(keyValue);
        }
        else {
            ArcServiceHYRHandler.instance().setArcPinyinViewText(curPinyin.substring(0, curPinyin.length() - 1));
            String pinyinValue = ArcServiceHYRHandler.instance().getArcPinyinViewText();
            if (pinyinValue.length() > 0) {
                ArcServiceHYRHandler.instance().setCandidatesViewShown(true);
                ArcServiceHYRHandler.instance().analyze(pinyinValue);
            }
            else
            {
                ArcServiceHYRHandler.instance().setCandidateNull();
                ArcServiceHYRHandler.instance().setCandidatesViewShown(false);
            }
        }
    }

    private void doWithEnterKey(String keyValue, String curPinyin) {
        if (null == curPinyin || curPinyin.length() == 0
                || ArcInfoManager.inputMode_CN != ArcServiceHYRHandler.instance().getInputMode())
        {
            ArcServiceHYRHandler.instance().handMsgToApp(keyValue);
        }
        else
        {
            ArcServiceHYRHandler.instance().chooseDefaultCandi();// choose defalut candidate value and re-analyze pinyin
        }
    }

    public String getKeyZoneValue(float x, float y, float keyBoardWidth, float keyBoardHeigh)
    {
        boolean isLeftHand = ArcServiceHYRHandler.instance().isLeftHand();
        return ConstantFunc.getKeyZoneValue(x, y, keyBoardWidth, keyBoardHeigh, isLeftHand, getListKeyZone());
    }
}
