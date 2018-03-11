package com.chy.ran.Util;

import com.chy.ran.Bean.KeyZone;
import com.chy.ran.Manager.ArcInfoManager;
import com.chy.ran.Service.ArcServiceHYRHandler;

/**
 * Created by chhy on 2018/3/5.
 */

public final class ConstantFunc
{
    public static KeyZone getDownZone(float x, float y, float arckeyboardLayoutWidth, final KeyZone[][] lister)
    {// from letp-top index (0,0)
        int row = getIndexCurRadio(x, y, arckeyboardLayoutWidth);
        if (row < 0 || row > 6 || lister == null ||  lister[row] == null) return null;
        for (int col = 0; col < lister[row].length; col++){

            if (lister[row][col] == null || lister[row][col].marcZoneInfo == null) continue;

            float sinVar = (float) ((y) / (Math.sqrt(x * x + y * y)));
            float asinVar = (float) Math.toDegrees(Math.asin(sinVar));

            float angle = (((float) 90) / ((float) lister[row][col].marcZoneInfo.arcOutDivdCount)) * ((float) (col + 1));
            if (asinVar < angle){return lister[row][col];}
        }
        return null;
    }

    public static int getIndexCurRadio(float downY, float downX, float arckeyboardLayoutWidth)
    {
        float curRadios = (float)Math.sqrt(downX * downX + downY * downY);
        return ArcInfoManager.keyboardLineNum - (int) Math.ceil(curRadios / arckeyboardLayoutWidth * ArcInfoManager.keyboardLineNum);
    }

    public static float getInerRadio(int inxRadio, float arckeyboardLayoutWidth)
    {
        return arckeyboardLayoutWidth - ((arckeyboardLayoutWidth / (float) ArcInfoManager.keyboardLineNum) * (float) (inxRadio + 1));
    }

    public static float getOutRadio(int inxRadio, float arckeyboardLayoutWidth)
    {
        return arckeyboardLayoutWidth - ((arckeyboardLayoutWidth / (float) ArcInfoManager.keyboardLineNum) * (float) inxRadio);
    }

    public static int getDived(int i)
    {
        if (i == 0) return 11;
        if (i == 1) return 10;
        if (i == 2) return 9;
        if (i == 3) return 8;
        if (i == 4) return 4;
        if (i == 5) return 1;
        return 11;
    }

    public static String getKeyZoneValue(float x, float y,
                                         float keyBoardWidth, float keyBoardHeigh,
                                         boolean isLeftHander, KeyZone[][] lstKeyZone){
        float radius          = (float) keyBoardWidth;//
        float radiusStep      = (float) keyBoardWidth/6;
        int UnusedLength       = ArcInfoManager.UnusedLength;
        float iner_x = isLeftHander ? x : (keyBoardWidth - x);
        float iner_y = keyBoardHeigh - y;
        float curRadius = (float) Math.sqrt(iner_x * iner_x + iner_y * iner_y);
        if (curRadius > radius) return null;
        if (curRadius < UnusedLength) return ArcInfoManager.ENTER;

        int iradius = (int) (
                (
                        Math.ceil((keyBoardWidth - curRadius) / radiusStep)
                                - 1
                ) % 4);
        if (iradius < 0) iradius = 0;
        if (iradius > 3) iradius = 3;
        for(int j = 0; j < (ArcInfoManager.keyboardColNum - iradius); j++){
            KeyZone keyZone = lstKeyZone[iradius][j];
            if (keyZone.contains(iner_x, iner_y, keyBoardWidth, keyBoardHeigh, isLeftHander,
                    ArcInfoManager.keyboardColNum - iradius, j + 1))
                return keyZone.keyboardValue;
        }
        return null;
    }

    public static void initLstKeyZone(String[][] keyValue, int keyBoardWidth, int keyBoardHeigh,
                                      KeyZone[][] lstKeyZone, boolean isLeftHander)
    {

        float radius          = (float) keyBoardWidth;//
        float radiusStep      = (float)keyBoardWidth / ArcInfoManager.keyboardLineNum;
        int UnusedLength       = ArcInfoManager.UnusedLength;

        int i = 0 ;
        for (; i < ArcInfoManager.keyboardLineNum - 2; i++) {
            for(int j = 0; j < (ArcInfoManager.keyboardColNum - i); j++){
                lstKeyZone[i][j] = new KeyZone(i, j, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh,
                        keyValue[i][j],
                        UnusedLength);
            }
        }

        lstKeyZone[i][0] = new KeyZone(i, 0, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh, keyValue[i][0], UnusedLength);
        lstKeyZone[i][1] = new KeyZone(i, 1, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh, keyValue[i][1], UnusedLength);
        lstKeyZone[i][2] = new KeyZone(i, 2, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh, keyValue[i][2], UnusedLength);
        lstKeyZone[i][3] = new KeyZone(i, 3, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh, keyValue[i][3], UnusedLength);

        i++;
        lstKeyZone[i][0] = new KeyZone(i, 0, radius, radiusStep, isLeftHander, keyBoardWidth, keyBoardHeigh, keyValue[i][0], UnusedLength);
    }

    public static float getAsianVar(float upX, float upY) {
        float sinVar = (float) ((upY) / (Math.sqrt(upX * upX + upY * upY)));
        return (float) Math.toDegrees(Math.asin(sinVar));
    }

    public static boolean isOneOf26Char(String input) {
        return ('A' <= input.charAt(0) && input.charAt(0) <= 'Z') || ('a' <= input.charAt(0) && input.charAt(0) <= 'z');
    }

    public static String getChoosedCh(float upX, float upY)
    {
        float asianVar = getAsianVar(upX, upY);
        float angle = ((float) 90) / ArcInfoManager.keyboardColNum;
        int iRow = (int) Math.ceil(asianVar / angle);

        int iIndx = ArcServiceHYRHandler.instance().getFirstIndexInCandiList();

        boolean isValid = ArcServiceHYRHandler.getCandidate() != null
                && ArcServiceHYRHandler.getCandidate().length > iRow + iIndx;
        return isValid ? ArcServiceHYRHandler.getCandidate()[iRow + iIndx] : "";
    }
}
