package com.chy.ran.Manager;

import com.chy.ran.Stat.CH.KeyboardStatCH;
import com.chy.ran.Stat.EN.KeyboardStatEN;
import com.chy.ran.Stat.KeyboardStat;
import com.chy.ran.Stat.Num.KeyboardStatNum;
import com.chy.ran.Stat.eng.KeyboardStatEng;

/**
 * Created by chhy on 2018/2/25.
 */

public class ArcStatManger {
    public final static KeyboardStat[] keyboardStats = new KeyboardStat[]{
        new KeyboardStatCH(),
        new KeyboardStatEN(),
        new KeyboardStatEng(),
        new KeyboardStatNum()};
}
