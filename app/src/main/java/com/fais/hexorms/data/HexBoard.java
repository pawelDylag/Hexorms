package com.fais.hexorms.data;

import java.util.ArrayList;

/**
 * Created by paweldylag on 24/11/15.
 */
public class HexBoard {

    private final int width;
    private final int height;

    private final Hex[][] mBoard;

    public HexBoard(int width, int height) {
        // init new board with hex items
        this.width = width;
        this.height = height;
        mBoard = buildNewBoard(width,height);
    }

    private Hex[][] buildNewBoard(int width, int height) {
        Hex[][] newBoard = new Hex[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newBoard[i][j] = new Hex(i, j, Constants.EMPTY_HEX);
            }
        }
        return newBoard;
    }

    public Hex getAdjectantHex(Hex hex, int direction) {
        // TODO: IMPLEMENT THIS
        // move tylko zwraca Hexa dla kierunku -> robak sobie go potem modyfikuje
        return null;
    }

    /**
     * Rusza obiektem na nowy hex i zeruje stary
     * @param from
     * @param to
     */
    public void move(Hex from, Hex to) {
        to.setContent(from.getContent());
        from.setContent(Constants.EMPTY_HEX);
    }

    public Hex add(int x, int y, int id) {
        Hex hex = mBoard[x][y];
        hex.setContent(id);
        return hex;
    }

    public void clearHex(Hex hex) {
        hex.setContent(Constants.EMPTY_HEX);
    }

    public boolean isEmptyHex(int x, int y) {
        return mBoard[x][y].getContent() == Constants.EMPTY_HEX;
    }

    public boolean isBacteriaHex(int x, int y) {
        return mBoard[x][y].getContent() == Constants.BACTERIA_HEX;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Hex[][] getBoard() {
        return this.mBoard;
    }



}
