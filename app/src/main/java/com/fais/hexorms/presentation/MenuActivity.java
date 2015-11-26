package com.fais.hexorms.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

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
    @Bind(R.id.start_game_button)
    Button startButton;
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
            getActionBar().setTitle(R.string.title_activity_menu);
        }

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




    @OnClick(R.id.start_game_button)
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SimulationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INTENT_BOARD_WIDTH, boardWidth);
        bundle.putInt(Constants.INTENT_BOARD_HEIGHT, boardHeight);
        bundle.putInt(Constants.INTENT_WORMS_COUNT, wormsNumber);
        bundle.putInt(Constants.INTENT_BACTERIA_FACTOR, bacteriaFactorNumber);

        intent.putExtras(bundle);
        Toast.makeText(this, "width: " + boardWidth + "\nheight: " + boardHeight + "\nworms: " + wormsNumber + "\nbacteria: "+ bacteriaFactorNumber, Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(MenuActivity.this, Pop.class));

        //startActivity(intent);
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

}