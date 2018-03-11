package com.chy.ran.Stat.Num;

import com.chy.ran.Stat.KeyboardStat;

/**
 * Created by chhy on 2018/2/25.
 */

public class KeyboardStatNum extends KeyboardStat {
    public KeyboardStatNum()
    {
        this.keyValue = new String[][]{//[5][10]
                {"~", "=", "`", "_", "|", "\\", BLANK, BLANK, BLANK, BLANK, BLANK},
                {"1",   "2",    "3",   "4",   "5",    "6",   "7",   "8",    "9",   "0"},
                {"+",   "-",    "*",   "/",   "<",    ">",   "{",   "}",    ";",   CROSS},
                {"!",   "@",    "$",   "%",   "&",    "(",   ")",   ",",    CROSS, CROSS},//"###"是占位符
                {SPACE, "←",   "?",   ".",  CROSS, CROSS, CROSS, CROSS, CROSS, CROSS},
                {ENTER, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS, CROSS}};

        this.SetOneKeyboardStatHandler(new KeyboardStatNumHandler());

    }
}
