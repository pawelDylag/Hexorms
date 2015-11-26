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
        mBoard = buildNewBoard(width, height);
    }

    private Hex[][] buildNewBoard(int width, int height) {
        Hex[][] newBoard = new Hex[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newBoard[j][i] = new Hex(j, i, Constants.EMPTY_HEX);
            }
        }
        return newBoard;
    }

    public Hex getAdjacentHex(Hex hex, int direction) {
        // TODO: IMPLEMENT THIS
        // move tylko zwraca Hexa dla kierunku -> robak sobie go potem modyfikuje
        int x = hex.getX();
        int y = hex.getY();
        int last_x = width - 1;
        int last_y = height - 1;

        switch (direction) {

            case Constants.DIRECTION_NORTH_EAST: {
                if (y == 0) {
                    if (x == last_x) {
                        x = 0;
                        y = last_y;
                        break;
                    } else {
                        y = last_y;
                        x++;
                        break;
                    }
                } else if (y % 2 == 0) {
                    y--;
                    break;
                } else {
                    if (x == last_x) {
                        x = 0;
                        y--;
                        break;
                    } else {
                        x++;
                        y--;
                        break;
                    }
                }
            }

            case Constants.DIRECTION_EAST: {
                if (x == last_x) {
                    x = 0;
                    break;
                } else {
                    x++;
                    break;
                }
            }

            case Constants.DIRECTION_SOUTH_EAST: {
                if (y == last_y) {
                    if (x == last_x) {
                        y = 0;
                        x = 0;
                        break;
                    } else {
                        y = 0;
                        x++;
                        break;
                    }
                } else if (y % 2 == 0) {
                    y++;
                    break;
                } else {
                    if (x == last_x) {
                        x = 0;
                        y++;
                        break;
                    } else {
                        x++;
                        y++;
                        break;
                    }
                }
            }

            case Constants.DIRECTION_SOUTH_WEST: {

                if (y == last_y) {
                    if (x == 0) {
                        x = last_x;
                        y = 0;
                        break;
                    } else {
                        x--;
                        y = 0;
                        break;
                    }
                } else if (y % 2 == 0) {
                    if (x == 0) {
                        x = last_x;
                        y++;
                        break;
                    } else {
                        x--;
                        y++;
                        break;
                    }
                } else {
                    y++;
                    break;
                }
            }

            case Constants.DIRECTION_WEST: {
                if (x == 0) {
                    x = last_x;
                    break;
                } else {
                    x--;
                    break;
                }
            }
            case Constants.DIRECTION_NORTH_WEST: {
                if (y == 0) {
                    if (x == 0) {
                        x = last_x;
                        y = last_y;
                        break;
                    } else {
                        x--;
                        y = last_y;
                        break;
                    }
                } else if (y % 2 == 0) {
                    if (x == 0) {
                        x = last_x;
                        y--;
                        break;
                    } else {
                        x--;
                        y--;
                        break;
                    }
                } else {
                    y--;
                    break;
                }
            }
            default:
                return null;
        }

        return new Hex(x, y, hex.getContent());
    }

    /**
     * Rusza obiektem na nowy hex i zeruje stary
     *
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
