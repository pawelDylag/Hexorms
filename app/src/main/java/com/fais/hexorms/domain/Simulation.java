package com.fais.hexorms.domain;

import com.fais.hexorms.data.Constants;
import com.fais.hexorms.data.Hex;
import com.fais.hexorms.data.HexBoard;
import com.fais.hexorms.data.Worm;

import java.util.Random;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Simulation {

    private double bacteriaFactor;

    private WormsManager wormsManager;
    private HexBoard board;

    public Simulation(int wormsCount, int boardHeight, int boardWidth, double bacteriaFactor) {
        this.bacteriaFactor = bacteriaFactor;
        board = new HexBoard(boardWidth, boardHeight);
        this.wormsManager = new WormsManager(wormsCount, this);
    }

    /**
     * Startuje symulacje:
     * 1. losuje bakterie
     * 2. ustawia wormsy na losowych miejscach
     * 3. robi ruchy az do smierci ostatniego wormsa
     */
    public void start() {
        setupBacteriaaaaaaaaaas();
        setupWooooormmmmssssss();
        // dzialamy do momentu az nie zdechna wszystkie hexormsy!
        while (wormsManager.hasWorms()) {
            wormsManager.makeMoves();
            // TODO: po zrobieniu ruchow przekazac nowa tablice do outputa na ekran
        }
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
                if (r.nextDouble() < bacteriaFactor) {
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
            while (!board.isEmptyHex(x,y) || !board.isBacteriaHex(x,y)) {
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
