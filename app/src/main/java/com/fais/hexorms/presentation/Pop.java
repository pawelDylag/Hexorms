package com.fais.hexorms.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.fais.hexorms.R;

/**
 * Created by Kuba on 26.11.2015.
 */
public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.75));
    }
}
