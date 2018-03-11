package com.chy.ran.Bean;

/**
 * Created by chhy on 2017/4/19.
 */

public class ArcZoneInfo {
    public int arcOutKeyBoarddIndex = 0;
    private float arcOutKeyBoardRadio = 0;
    private float arcInnerKeyBoardRadio = 0;
    public int arcOutDivdCount = 0;

    public ArcZoneInfo(int arcOutKeyBoarddIndex, int arcOutDivdCount, float arcOutKeyBoardRadio,float arcInnerKeyBoardRadio)
    {
        this.arcOutKeyBoarddIndex = arcOutKeyBoarddIndex;
        this.arcOutKeyBoardRadio = arcOutKeyBoardRadio;
        this.arcInnerKeyBoardRadio = arcInnerKeyBoardRadio;
        this.arcOutDivdCount = arcOutDivdCount;
    }
}
