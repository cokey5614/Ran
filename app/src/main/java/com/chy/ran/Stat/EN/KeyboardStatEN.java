package com.chy.ran.Stat.EN;

import com.chy.ran.Stat.KeyboardStat;

/**
 * Created by chhy on 2018/2/25.
 */

public class KeyboardStatEN extends KeyboardStat {
    public KeyboardStatEN()
    {
        this.keyValue = new String[][]{//[5][10]
                {BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK},
                {"Q",   "W",    "E",   "R",   "T",    "Y",   "U",   "I",    "O",   "P"},
                {"A",   "S",    "D",   "F",   "G",    "H",   "J",   "K",    "L",   CROSS},
                {"Z",   "X",    "C",   "V",   "B",    "N",   "M",   ",",    CROSS, CROSS},//"###"是占位符
                {SPACE, "←",   "?",   ".",  CROSS, CROSS, CROSS, CROSS, CROSS, CROSS},
                {ENTER, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS}};

        this.SetOneKeyboardStatHandler(new KeyboardStatENHandler());
    }
}
