package com.chy.ran.Util;

import android.content.Context;

/**
 * Created by chhy on 2018/3/11.
 */

public class RanRanContext
{
    private static Context context = null;
    public static void init(Context value)
    {
        context = value;
    }
    static Context getContext()
    {
        return context;
    }
}
