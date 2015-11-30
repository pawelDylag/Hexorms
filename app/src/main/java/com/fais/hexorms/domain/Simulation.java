package com.fais.hexorms.domain;

import android.util.Log;

import com.fais.hexorms.data.Constants;
import com.fais.hexorms.data.Hex;
import com.fais.hexorms.data.HexBoard;
import com.fais.hexorms.data.Worm;

import java.util.Random;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Simulation {

    @SuppressWarnings("unused")
    private static final String TAG = Simulation.class.getSimpleName();

    private double bacteriaFactor;

    private WormsManager wormsManager;
    private HexBoard board;
    private BoardRefreshListener boardRefreshListener;

    public interface BoardRefreshListener {
        void onBoardRefresh(Hex[][] board);
    }

    public Simulation(int wormsCount, int boardHeight, int boardWidth, double bacteriaFactor) {
        this.bacteriaFactor = bacteriaFactor;
        board = new HexBoard(boardWidth, boardHeight);
        this.wormsManager = new WormsManager(wormsCount, this);
    }

    public void setBoardRefreshListener(BoardRefreshListener l){
        this.boardRefreshListener = l;
    }

    /**
     * Startuje symulacje:
     * 1. losuje bakterie
     * 2. ustawia wormsy na losowych miejscach
     * 3. robi ruchy az do smierci ostatniego wormsa
     */
    public void start() {
        Log.d(TAG, "Start!");
        setupBacteriaaaaaaaaaas();
        setupWooooormmmmssssss();
        boardRefreshListener.onBoardRefresh(board.getBoard());
        // dzialamy do momentu az nie zdechna wszystkie hexormsy!
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int turnCounter = 0;
                    while (wormsManager.hasWorms()) {
                        turnCounter++;
                        Log.d(TAG, "Turn " + turnCounter);
                        wormsManager.makeMoves();
                        boardRefreshListener.onBoardRefresh(board.getBoard());
                        Thread.sleep(Constants.TURN_DELAY_MILLIS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Ustawia bakterie na planszy.
     * Prawdopodobienstwo wystapienia bakterii na danym polu
     * jest okreslone przez bacteriaFactor
     */
    private void setupBacteriaaaaaaaaaas() {
        Random r = new Random();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (Double.compare(r.nextDouble(), bacteriaFactor) < 0) {
                    board.add(i, j, Constants.BACTERIA_HEX);
                }
            }
        }
    }

    /**
     * Ustawia wormsy na losowych miejscach
     */
    private void setupWooooormmmmssssss() {
        for (Worm worm: wormsManager.getWormList()) {
            Random r = new Random();
            int x, y;
            x = r.nextInt(board.getWidth());
            y = r.nextInt(board.getHeight());
            while (!board.isEmptyHex(x,y) && !board.isBacteriaHex(x,y)) {
                x = r.nextInt(board.getWidth());
                y = r.nextInt(board.getHeight());
            }
            worm.setHex(board.add(x, y, worm.getId()));
        }
    }

    public HexBoard getBoard() {
        return board;
    }
}
