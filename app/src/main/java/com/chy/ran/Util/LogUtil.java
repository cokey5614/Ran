package com.chy.ran.Util;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by chhy on 2018/3/10.
 */

public class LogUtil
{
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final int level            = ERROR;
    private static final boolean showToToast = true;

    public static void saveAndShow(String message, int lengthShort)
    {
        if(showToToast)Toast.makeText(RanRanContext.getContext(), message, lengthShort).show();
    }

    public static void v(String tag, String message)
    {
        if(level <= VERBOSE)
        {
            Log.v(System.currentTimeMillis() + ":" + tag, message);
            saveAndShow(message, Toast.LENGTH_SHORT);
        }
    }


    public static void d(String tag, String message)
    {
        if(level <= DEBUG)
        {
            Log.d(System.currentTimeMillis() + ":" + tag, message);
            saveAndShow(message, Toast.LENGTH_SHORT);
        }
    }

    public static void i(String tag, String message)
    {
        if(level <= INFO)
        {
            Log.i(System.currentTimeMillis() + ":" + tag, message);
            saveAndShow(message, Toast.LENGTH_SHORT);
        }
    }

    public static void w(String tag, String message)
    {
        if(level <= WARN)
        {
            Log.w(System.currentTimeMillis() + ":" + tag, message);
            saveAndShow(message, Toast.LENGTH_LONG);
        }
    }

    public static void e(String tag, String message)
    {
        if(level <= ERROR)
        {
            Log.e(System.currentTimeMillis() + ":" + tag, message);
            saveAndShow(message, Toast.LENGTH_LONG);
        }
    }

}
