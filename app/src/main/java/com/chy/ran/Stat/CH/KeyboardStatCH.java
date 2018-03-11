package com.chy.ran.Stat.CH;

import com.chy.ran.Stat.KeyboardStat;

/**
 * Created by chhy on 2018/2/25.
 */

public class KeyboardStatCH extends KeyboardStat {

    public KeyboardStatCH()
    {
        this.keyValue = new String[][]{//[5][10]
                {BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK},
                {"q",   "w",    "e",   "r",   "t",    "y",   "u",   "i",    "o",   "p"},
                {"a",   "s",    "d",   "f",   "g",    "h",   "j",   "k",    "l",   CROSS},
                {"z",   "x",    "c",   "v",   "b",    "n",   "m",   ",",    CROSS, CROSS},//"###"是占位符
                {SPACE, "←",   "?",   "。",  CROSS, CROSS, CROSS, CROSS, CROSS, CROSS},
                {ENTER, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS}};

        this.SetOneKeyboardStatHandler(new KeyboardStatCHHandler());

    }

}
