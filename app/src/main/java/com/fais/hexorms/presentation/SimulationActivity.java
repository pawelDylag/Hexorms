package com.fais.hexorms.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fais.hexorms.R;
import com.fais.hexorms.data.Constants;
import com.fais.hexorms.data.Hex;
import com.fais.hexorms.domain.Simulation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SimulationActivity extends AppCompatActivity implements Simulation.BoardRefreshListener {

    @SuppressWarnings("unused")
    private static final String TAG = SimulationActivity.class.getSimpleName();

    @Bind(R.id.activity_simulation_hexagonal_layout)
    protected HexagonalLayout mHexagonalLayout;

    private int mBoardWidth, mBoardHeight, mWormCount, mBacteriaFactor;
    private Simulation simulation;

    public static Intent buildIntent(Context context, int width, int height, int wormCount, int bacteriaFactor) {
        Intent intent = new Intent(context, SimulationActivity.class);
        intent.putExtra(Constants.INTENT_BOARD_WIDTH, width);
        intent.putExtra(Constants.INTENT_BOARD_HEIGHT, height);
        intent.putExtra(Constants.INTENT_WORMS_COUNT, wormCount);
        intent.putExtra(Constants.INTENT_BACTERIA_FACTOR, bacteriaFactor);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        ButterKnife.bind(this);

        // read intent and setup fields
        readIntent();

        // setup simulation
        Log.d(TAG, "Setting simulationm: w=" + mBoardWidth + ", h="+ mBoardHeight + ", worm=" + mWormCount +", bacteria=" + mBacteriaFactor + "%");
        double bacteriaPercentage = mBacteriaFactor/100d;
        simulation = new Simulation(mWormCount,mBoardHeight, mBoardWidth, bacteriaPercentage);
        simulation.setBoardRefreshListener(this);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // setup hex layout
        mHexagonalLayout.setMaxColumns(mBoardWidth);
        mHexagonalLayout.setMaxRows(mBoardWidth);

        // setup board with views
        Log.d(TAG, "Setting board view");
        setupBoard();

    }

    private void readIntent() {
        Intent i = getIntent();
        this.mBoardWidth = i.getIntExtra(Constants.INTENT_BOARD_WIDTH,1);
        this.mBoardHeight = i.getIntExtra(Constants.INTENT_BOARD_HEIGHT,1);
        this.mWormCount = i.getIntExtra(Constants.INTENT_WORMS_COUNT,1);
        this.mBacteriaFactor = i.getIntExtra(Constants.INTENT_BACTERIA_FACTOR, 50);
    }

    private void setupBoard() {
        for (int y = 0 ; y < mBoardHeight; y++) {
            for (int x = 0; x < mBoardWidth; x++){
                final FrameLayout r = new FrameLayout(this);
                final TextView t = new TextView(this);
                t.setGravity(Gravity.CENTER);
                r.addView(t);
                final ImageView i = new ImageView(this);
                i.setImageResource(R.drawable.test_hex);
                r.addView(i);
                mHexagonalLayout.addView(r);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //start simulation
        simulation.start();
    }

    @Override
    public void onBoardRefresh(final Hex[][] board) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int y = 0 ; y < mBoardHeight; y++) {
                    for (int x = 0; x < mBoardWidth; x++){
                        FrameLayout r = (FrameLayout) mHexagonalLayout.getChildAt(x + mBoardHeight*y);
                        TextView t = (TextView) r.getChildAt(0);
                        if (board[y][x].getContent() != Constants.EMPTY_HEX) {
                            t.setText(board[y][x].getContent() + "");
                        } else {
                            t.setText("");
                        }
                    }
                }
            }
        });
    }
}
