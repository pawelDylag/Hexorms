package com.fais.hexorms.data;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Constants {

    public static final int EMPTY_HEX = 0;
    public static final int BACTERIA_HEX = 1;

    public static final int BACTERIA_BONUS = 5;

    public static final int DIRECTION_N = 1;
    public static final int DIRECTION_NE = 2;
    public static final int DIRECTION_SE = 3;
    public static final int DIRECTION_S = 4;
    public static final int DIRECTION_SW = 5;
    public static final int DIRECTION_NW = 6;

    public static final int TURN_DELAY_MILLIS = 500;

    // ile razy robak ponowi obrot zanim system zatrzyma go w miejscu na 1 ture
    public static final int WORM_ROTATE_LIMIT = 5;

    // intent names
    public static final String INTENT_BOARD_SIZE = "intentBoardSize";
    public static final String INTENT_WORMS_COUNT = "intentWormsCount";
    public static final String INTENT_BACTERIA_FACTOR = "intentBacteriaFactor";

}
