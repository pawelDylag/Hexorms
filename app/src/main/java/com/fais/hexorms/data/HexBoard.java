package com.fais.hexorms.data;


import android.util.Log;

/**
 * Created by paweldylag on 24/11/15.
 */
public class HexBoard {

    private final int boardSize;

    private final Hex[][] mBoard;

    public HexBoard(int boardSize) {
        // init new board with hex items
        this.boardSize = boardSize;
        mBoard = buildNewBoard(boardSize);
    }

    private Hex[][] buildNewBoard(int boardSize) {
        Hex[][] newBoard = new Hex[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                newBoard[j][i] = new Hex(j, i, Constants.EMPTY_HEX, Constants.DIRECTION_SE);
            }
        }
        return newBoard;
    }

    /**
     * @param hex       Current hex the worm is on
     * @param direction Counted direction worm moves to
     * @return Hex where worm should move next
     */

    public Hex getAdjacentHex (Hex hex, int direction) {
        int x = hex.getX();
        int y = hex.getY();
        int last_x = boardSize - 1;
        int last_y = boardSize - 1;
        Log.d("HexBoard", "getAdjecentHex(): " + x + "," + y + "  direction=" + direction);
        switch (direction) {
            case Constants.DIRECTION_N: {
                if (y == 0) {
                    y = last_y;
                    break;
                } else {
                    y--;
                    break;
                }
            }

            case Constants.DIRECTION_S: {
                if (y == last_y) {
                    y = 0;
                    break;
                } else {
                    y++;
                    break;
                }
            }

            case Constants.DIRECTION_NE: {
                // ostatni z prawej
                if (x == last_x) {
                    x = 0;
                    break;
                }
                // parzyste x
                else if (x % 2 == 0) {
                    if (y == 0) {
                        x++;
                        y = last_y;
                        break;
                    } else {
                        x++;
                        y--;
                        break;
                    }
                }
                // nieparzyste x
                else {
                    x++ ;
                    break;
                }
            }

            case Constants.DIRECTION_NW: {
                // ostatni z lewej
                if (x == 0) {
                    x = last_x;
                    break;
                }
                // parzyste x
                else if (x % 2 == 0) {
                    if (y == 0) {
                        x --;
                        y = last_y;
                        break;
                    } else {
                        x--;
                        y--;
                        break;
                    }
                }
                // nieparzyste x
                else {
                    x--;
                    break;
                }
            }

            case Constants.DIRECTION_SE: {
                // ostatni z lewej
                if (x == last_x) {
                    x = 0;
                    break;
                }
                // parzyste x
                else if (x % 2 != 0) {
                    if (y == last_y) {
                        x++;
                        y = 0;
                        break;
                    } else {
                        x++;
                        y++;
                        break;
                    }
                }
                // nieparzyste x
                else {
                    x++;
                    break;
                }
            }

            case Constants.DIRECTION_SW: {
                // ostatni z lewej
                if (x == 0) {
                    x = last_x;
                    break;
                }
                // nieparzyste x
                else if (x % 2 != 0) {
                    if (y == last_y) {
                        x--;
                        y = 0;
                        break;
                    } else {
                        x--;
                        y++;
                        break;
                    }
                }
                // parzyste x
                else {
                    x--;
                    break;
                }
            }

        }
        Log.d("HexBoard", "getAdjecentHex(): result -> " + x + "," + y );
        return mBoard[x][y];
    }
    /**
     * Rusza obiektem na nowy hex i zeruje stary
     *
     * @param from
     * @param to
     */

    public void move(Hex from, Hex to) {
        to.setContent(from.getContent());
        to.setContentDirection(from.getContentDirection());
        from.setContent(Constants.EMPTY_HEX);
    }

    public Hex add(int x, int y, int id, int direction) {
        Hex hex = mBoard[x][y];
        hex.setContent(id);
        hex.setContentDirection(direction);
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



    public int getBoardSize() {
        return boardSize;
    }

    public Hex[][] getBoard() {
        return this.mBoard;
    }

}
