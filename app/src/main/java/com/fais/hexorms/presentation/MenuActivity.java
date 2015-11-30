package com.fais.hexorms.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.fais.hexorms.R;
import com.gc.materialdesign.views.ButtonRectangle;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MenuActivity extends Activity {

    @Bind(R.id.seekbar_boardsize)
    SeekBar boardsizePicker;
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

    private int boardSize = 3;
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
        Intent intent = SimulationActivity.buildIntent(this, boardSize, wormsNumber, bacteriaFactorNumber);
        startActivity(intent);
    }

    private void initPickers() {
        boardSizeTextView.setText(boardSize + " x " + boardSize);
        wormsCountTextView.setText("" + wormsNumber);
        bacteriaFactorTextView.setText("" + bacteriaFactorNumber + "%");


        boardsizePicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // add minimal size
                boardSize = 3 + progress;
                boardSizeTextView.setText(boardSize + " x " + boardSize);
                wormsPicker.setMax((boardSize * boardSize) / 2);
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