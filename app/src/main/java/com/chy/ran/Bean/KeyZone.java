package com.chy.ran.Bean;


import com.chy.ran.Manager.ArcInfoManager;

public class KeyZone {

    private float mkeyBoardWidth = 0;
    private float mkeyBoardHeigh = 0;

    public KeyZone(int iRow, int iCol, float radius, float radiusStep, boolean isLeftHander,
                   float keyBoardWidth, float keyBoardHeigh, String keyvalue, float UnusedLength){
        mkeyBoardWidth = keyBoardWidth;
        mkeyBoardHeigh = keyBoardHeigh;
        Left  = new Position();
        Up    = new Position();
        Right = new Position();
        Down  = new Position();

        fresh(iRow, iCol, radius, radiusStep, isLeftHander, keyvalue, UnusedLength);
    }

    private void fresh(int iRow, int iCol, float radius, float radiusStep, boolean isLeftHander,
                      String keyvalue, float UnusedLength){
    	UnusedLength = 0;
        if (iRow == ArcInfoManager.keyboardLineNum - 1)
        {
            innerFreshZeroZine(iRow, iCol, radius, radiusStep, isLeftHander, keyvalue);
            return;
        }
        else
        {
            innerFresh(iRow, iCol, radius, radiusStep, isLeftHander, UnusedLength);
        }
        keyboardValue = keyvalue;
    }

    private void innerFreshZeroZine(int iRow, int iCol, float radius, float radiusStep, boolean isLeftHander, String keyvalue) {
        Left.setProperty(radiusStep, 0, isLeftHander);
        Up.setProperty(0, radiusStep, isLeftHander);
        Right.setProperty(0, 0, isLeftHander);
        Down.setProperty(0, 0, isLeftHander);
        keyboardValue = keyvalue;
    }

    private void innerFresh(int iRow, int iCol, float radius, float radiusStep, boolean isLeftHander, float UnusedLength) {
        int dividCount = getDived(iRow);
        float x = (float) ((radius - ((float)iRow) * radiusStep - UnusedLength) * Math.cos((Math.PI / (float)180)*((float)(iCol) * ((float)90/(float)(dividCount)))));
        float y = (float) ((radius - ((float)iRow) * radiusStep - UnusedLength) * Math.sin((Math.PI / (float)180)*((float)(iCol) * ((float)90/(float)(dividCount)))));
        Left.setProperty(x, y, isLeftHander);

        x = (float) ((radius - ((float)iRow) * radiusStep - UnusedLength) * Math.cos((Math.PI / (float)180)*((float)(iCol + 1) * ((float)90/(float)(dividCount)))));
        y = (float) ((radius - ((float)iRow) * radiusStep - UnusedLength) * Math.sin((Math.PI / (float)180)*((float)(iCol + 1) * ((float)90/(float)(dividCount)))));
        Up.setProperty(x, y, isLeftHander);

        x = (float) ((radius - ((float)iRow + (float)1) * radiusStep - UnusedLength) * Math.cos((Math.PI / (float)180)*((float)(iCol + 1) * ((float)90/(float)(dividCount)))));
        y = (float) ((radius - ((float)iRow + (float)1) * radiusStep - UnusedLength) * Math.sin((Math.PI / (float)180)*((float)(iCol + 1) * ((float)90/(float)(dividCount)))));
        Right.setProperty(x, y, isLeftHander);

        x = (float) ((radius - ((float)iRow + (float)1) * radiusStep - UnusedLength) * Math.cos((Math.PI / (float)180)*((float)(iCol) * ((float)90/(float)(dividCount)))));
        y = (float) ((radius - ((float)iRow + (float)1) * radiusStep - UnusedLength) * Math.sin((Math.PI / (float)180)*((float)(iCol) * ((float)90/(float)(dividCount)))));
        Down.setProperty(x, y, isLeftHander);

    }
    private  int getDived(int i) {
        if (i == 0) return 11;
        if (i == 1) return 10;
        if (i == 2) return 9;
        if (i == 3) return 8;
        if (i == 4) return 4;
        if (i == 5) return 1;
        return 11;
    }
    public void setArcZoneInfo(ArcZoneInfo arcZoneInfo) {
        marcZoneInfo = arcZoneInfo;
    }
    public ArcZoneInfo marcZoneInfo = null;

    public class Position{
    	float x = 0;
    	float y = 0;

        public Position(){}

        public void setProperty(float _x, float _y, boolean isLeftHander) {
            y = _y; // from right =0
            x = _x; // from down = 0
            if(isLeftHander)
            {
                x = mkeyBoardWidth - _x;
                y = _y;
            }
        }
        public float getX() {
            return mkeyBoardWidth - x;
        }

        public float getY() {
            return mkeyBoardHeigh - y;
        }

        public String toString()
        {
        	return "r(" + Math.sqrt(x * x + y * y) +")=sqrt(X:" + x + "," + "Y:" + y + ")";
        }
    }

    Position Left  = null;
    Position Up    = null;
    Position Right = null;
    Position Down  = null;

    public String keyboardValue;

    public KeyZone.Position getDown() {
        return Down;
    }

    public KeyZone.Position getLeft() {
        return Left;
    }

    public KeyZone.Position getRight() {
        return Right;
    }

    public KeyZone.Position getUp() {
        return Up;
    }

    public boolean contains(float x, float y, float keyBoardWidth, float keyBoardHeigh, boolean isLeftHander, int iradius, int j){

        float sinVar = (float) ((y) / (Math.sqrt(x * x + y * y)));
        float asinVar = (float) Math.toDegrees(Math.asin(sinVar));

        float angle = (((float) 90) / ((float) iradius)) * ((float) j);
        return asinVar < angle;
    }
    
    public String toString()
    {
    	return Left.toString();
    }
}