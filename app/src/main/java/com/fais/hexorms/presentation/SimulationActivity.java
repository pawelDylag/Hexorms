package com.fais.hexorms.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    private int mBoardSize, mWormCount, mBacteriaFactor;
    private Simulation simulation;

    public static Intent buildIntent(Context context, int width, int wormCount, int bacteriaFactor) {
        Intent intent = new Intent(context, SimulationActivity.class);
        intent.putExtra(Constants.INTENT_BOARD_SIZE, width);
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
        Log.d(TAG, "Setting simulationm: size=" + mBoardSize  + ", worm=" + mWormCount +", bacteria=" + mBacteriaFactor + "%");
        double bacteriaPercentage = mBacteriaFactor/100d;
        simulation = new Simulation(mWormCount, mBoardSize, bacteriaPercentage);
        simulation.setBoardRefreshListener(this);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // setup hex layout
        mHexagonalLayout.setMaxColumns(mBoardSize);
        mHexagonalLayout.setMaxRows(mBoardSize);

        // setup board with views
        Log.d(TAG, "Setting board view");
        setupBoard();

    }

    private void readIntent() {
        Intent i = getIntent();
        this.mBoardSize = i.getIntExtra(Constants.INTENT_BOARD_SIZE,1);
        this.mWormCount = i.getIntExtra(Constants.INTENT_WORMS_COUNT,1);
        this.mBacteriaFactor = i.getIntExtra(Constants.INTENT_BACTERIA_FACTOR, 50);
    }

    private void setupBoard() {
        for (int y = 0 ; y < mBoardSize; y++) {
            for (int x = 0; x < mBoardSize; x++){
                final FrameLayout r = new FrameLayout(this);
                final ImageView icon = new ImageView(this);
                r.addView(icon);
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
                for (int y = 0 ; y < mBoardSize; y++) {
                    for (int x = 0; x < mBoardSize; x++) {
                        FrameLayout r = (FrameLayout) mHexagonalLayout.getChildAt(x + mBoardSize * y);
                        ImageView t = (ImageView) r.getChildAt(0);
                        if (board[y][x].getContent() == Constants.EMPTY_HEX) {
                            t.setImageResource(R.drawable.test_hex);
                        } else if (board[y][x].getContent() == Constants.BACTERIA_HEX) {
                            t.setImageResource(R.drawable.bacteria_1);
                        } else {
                            switch (board[y][x].getContentDirection()) {
                                case Constants.DIRECTION_N:
                                    t.setImageResource(R.drawable.worm_6);
                                    break;
                                case Constants.DIRECTION_NE:
                                    t.setImageResource(R.drawable.worm_1);
                                    break;
                                case Constants.DIRECTION_SE:
                                    t.setImageResource(R.drawable.worm_2);
                                    break;
                                case Constants.DIRECTION_S:
                                    t.setImageResource(R.drawable.worm_3);
                                    break;
                                case Constants.DIRECTION_SW:
                                    t.setImageResource(R.drawable.worm_4);
                                    break;
                                case Constants.DIRECTION_NW:
                                    t.setImageResource(R.drawable.worm_5);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        });
    }
}
