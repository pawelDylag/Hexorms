package com.fais.hexorms.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.fais.hexorms.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Pop extends Activity {

    @Bind(R.id.info_textview)
    TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);
        ButterKnife.bind(this);

        infoTextView.setText(Html.fromHtml(getString(R.string.info)));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.88),(int)(height*.75));
    }
}
