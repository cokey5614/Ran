package com.chy.ran.Service;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import com.chy.ran.Component.ArcKeyboardLayout;
import com.chy.ran.Component.ArcPinyinViewer;
import com.chy.ran.R;
import com.chy.ran.Util.LogUtil;

/**
 * Created by chhy on 2018/2/25.
 *
 //a.  当用户触发输入法显示的时候（客户端控件获得焦点），InputMethodService启动。首先调用onCreate() 函数，
 //    该函数在输入法第一次启动的时候调用，适合用来做一些初始化的设置，与其他service相同；
 //b.  调用onCreateInputView()函数，在该函数中创建KeyboardView并返回；
 //c.  调用onCreateCandidatesView()函数，在该函数中创建候选区实现并返回；
 //d.  调用onStartInputView()函数来开始输入内容，
 //e.  输入结束后调用onFinishInput()函数来结束当前的输入，
 //f.  如果移动到下一个输入框则重复调用onStartInputView和onFinishInput函数；
 //g.  在输入法关闭的时候调用onDestroy()函数。
 */
public class ArcServiceHYR extends InputMethodService
{
    protected static boolean InitArcServiceHYR       = false;
    protected static boolean InitArcInfo             = false;
    public ArcServiceHYR(){}

    public void onInitializeInterface() {
        super.onInitializeInterface();
    }

    // 创建主屏幕
    protected static LinearLayout mKeyBoardLayout = null;
    public static LinearLayout getKeyboardLayout(){return mKeyBoardLayout;}
    public View onCreateInputView()
    {
        //Toast.makeText(this, "onCreateInputView", Toast.LENGTH_LONG).showToToast();

        if (InitArcServiceHYR)
        {
            doWhenInit();
        }
        else
        {
            doWhenNotInit();
        }
        LogUtil.d(this.getClass().getName(), "begin onCreateInputView");
        // keyboard zone
        mKeyBoardLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.arckeyboardview, null);
        ViewTreeObserver vto = mKeyBoardLayout.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                doInitArcInfo();
            }
        });


        LogUtil.d(this.getClass().getName(), "end onCreateInputView");

        return mKeyBoardLayout;
    }

    private void doInitArcInfo()
    {
        if (!InitArcInfo) {
            adpt.InitArcInfo(mKeyBoardLayout.getWidth(), mKeyBoardLayout.getHeight());
            // ArcCandboardLayout lyArccandizoneview = (ArcCandboardLayout) getLayoutInflater().inflate(R.layout.arccandizoneview, null);
            ArcKeyboardLayout lyArczoneview = (ArcKeyboardLayout) getLayoutInflater().inflate(R.layout.arczoneview, null);
            // mKeyBoardLayout.addView(mPinYinLayout);
            mKeyBoardLayout.addView(lyArczoneview, mKeyBoardLayout.getWidth(), mKeyBoardLayout.getHeight());
            InitArcInfo = true;
        }
    }
    private void doWhenInit()
    {
        LogUtil.d(this.getClass().getName(), "begin doWhenInit");
        LogUtil.d(this.getClass().getName(), "end doWhenInit");
    }

    private static ArcServiceHYRHandler adpt      = null;
    private void doWhenNotInit()
    {
        adpt = new ArcServiceHYRHandler(this);
        InitArcServiceHYR = true;
        LogUtil.d(this.getClass().getName(), "end doWhenNotInit");
    }

    public void setInputView(View view)
    {
        LogUtil.d(this.getClass().getName(), "begin setInputView");
        super.setInputView(view);
        LogUtil.d(this.getClass().getName(), "end setInputView");
    }

    protected static LinearLayout mPinYinLayout = null;
    public static LinearLayout getPinyinLayout(){return mPinYinLayout;}
    protected static ArcPinyinViewer arcPinYinViewer = null;
    public static ArcPinyinViewer getArcPinYinViewer(){return arcPinYinViewer;}
    public View onCreateCandidatesView()
    {
        LogUtil.d(this.getClass().getName(), "begin onCreateCandidatesView");


        mPinYinLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.arcpinyinview, null);
        arcPinYinViewer = (ArcPinyinViewer) mPinYinLayout.findViewById(R.id.pinyin);

        adpt.InitAftCreateCandidatesView();

        LogUtil.d(this.getClass().getName(), "end onCreateCandidatesView:" + mPinYinLayout.toString());

        return mPinYinLayout;
    }

    public void onStartInputView(EditorInfo info, boolean restarting)
    {
        LogUtil.d(this.getClass().getName(), "begin onStartInputView");

        LogUtil.d(this.getClass().getName(), "end onStartInputView");
    }

    public void onFinishInput()
    {
        if (null == adpt){doWhenNotInit();return;}

        LogUtil.d(this.getClass().getName(), "begin onFinishInput");

        InputConnection ic = getCurrentInputConnection();
        adpt.onFinishInput(ic);

        LogUtil.d(this.getClass().getName(), "end onFinishInput");
    }

    public void onDestroy()
    {
        LogUtil.d(this.getClass().getName(), "begin onDestroy");
        LogUtil.d(this.getClass().getName(), "end onDestroy");
    }

}
