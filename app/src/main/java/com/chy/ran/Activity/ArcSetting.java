package com.chy.ran.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chy.ran.R;

/**
 * Created by chhy on 2017/4/9.
 */

public class ArcSetting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcsetting);

        Toast.makeText(this, "begin ArcSetting", Toast.LENGTH_LONG).show();
    }
}
