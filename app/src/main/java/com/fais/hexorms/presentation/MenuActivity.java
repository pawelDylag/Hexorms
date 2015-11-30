package com.fais.hexorms.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.fais.hexorms.R;
import com.fais.hexorms.data.Constants;
import com.gc.materialdesign.views.ButtonRectangle;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;


public class MenuActivity extends Activity {

    @Bind(R.id.seekbar_width)
    SeekBar widthPicker;
    @Bind(R.id.mySeekBar)
    VerticalSeekBar heightPicker;
    @Bind(R.id.seekbar_worms_count)
    SeekBar wormsPicker;
    @Bind(R.id.seekbar_bacteria_factor)
    SeekBar bacteriaFactorPicker;

    @Bind(R.id.boardSize)
    TextView boardSizeTextView;
    @Bind(R.id.worms_number)
    TextView wormsCountTextView;
    @Bind(R.id.bacteria_factor)
    TextView bacteriaFactorTextView;
    @Bind(R.id.start_simulation_button)
    ButtonRectangle startButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private int boardWidth = 3;
    private int boardHeight = 3;
    private int wormsNumber = 1;
    private int bacteriaFactorNumber = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.activity_menu);
        // bind this view
        ButterKnife.bind(this);
        // setup Toolbar
        setActionBar(toolbar);
        if (getActionBar() != null) {
            getActionBar().setIcon(R.mipmap.worm);
            getActionBar().setTitle(R.string.title_activity_menu);
        }

        /*TextView buttonTextView = startButton.getTextView();
        buttonTextView.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_titles));*/
        setButtonText();
        initPickers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        startActivity(new Intent(MenuActivity.this, Pop.class));
        // All clicks on menu items needs to be here
        return super.onOptionsItemSelected(item);
    }




    @OnClick(R.id.start_simulation_button)
    public void onClick(View view) {
        Intent intent = SimulationActivity.buildIntent(this, boardWidth, boardHeight, wormsNumber, bacteriaFactorNumber);
        startActivity(intent);
    }

    private void initPickers() {
        boardSizeTextView.setText(boardWidth + " x " + boardHeight);
        wormsCountTextView.setText("" + wormsNumber);
        bacteriaFactorTextView.setText("" + bacteriaFactorNumber + "%");


        widthPicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // add minimal size
                boardWidth = 3 + progress;
                boardSizeTextView.setText(boardWidth + " x " + boardHeight);
                wormsPicker.setMax((boardWidth * boardHeight) / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        heightPicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // add minimal size
                boardHeight = 3 + progress;
                boardSizeTextView.setText(boardWidth + " x " + boardHeight);
                wormsPicker.setMax((boardWidth * boardHeight) / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        wormsPicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                wormsNumber = 1 + progress;
                wormsCountTextView.setText("" + wormsNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        bacteriaFactorPicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                bacteriaFactorNumber = progress;
                bacteriaFactorTextView.setText("" + bacteriaFactorNumber + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setButtonText(){
        try {
            Field textButton = startButton.getClass().getDeclaredField("textButton");
            textButton.setAccessible(true);
            TextView textView = (TextView) textButton.get(startButton);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_titles));
            textView.setGravity(Gravity.CENTER);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}