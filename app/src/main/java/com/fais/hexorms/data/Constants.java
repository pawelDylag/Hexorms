package com.fais.hexorms.data;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Constants {

    public static final int EMPTY_HEX = 0;
    public static final int BACTERIA_HEX = 1;

    public static final int BACTERIA_BONUS = 5;

    public static final int DIRECTION_NORTH_EAST = 1;
    public static final int DIRECTION_EAST = 2;
    public static final int DIRECTION_SOUTH_EAST = 3;
    public static final int DIRECTION_SOUTH_WEST = 4;
    public static final int DIRECTION_WEST = 5;
    public static final int DIRECTION_NORTH_WEST = 6;

    public static final int TURN_DELAY_MILLIS = 800;

    // intent names
    public static final String INTENT_BOARD_WIDTH = "intentBoardWidth";
    public static final String INTENT_BOARD_HEIGHT = "intentBoardHeight";
    public static final String INTENT_WORMS_COUNT = "intentWormsCount";
    public static final String INTENT_BACTERIA_FACTOR = "intentBacteriaFactor";

}
