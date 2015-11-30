package com.fais.hexorms.data;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Hex {

    private final int x;
    private final int y;
    private int content;
    private int contentDirection;

    public Hex(int x, int y, int content, int contentDirection) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.contentDirection = contentDirection;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public boolean isMovePossible() {
        return content == Constants.EMPTY_HEX || content == Constants.BACTERIA_HEX;
    }

    public int getContentDirection() {
        return contentDirection;
    }

    public void setContentDirection(int contentDirection) {
        this.contentDirection = contentDirection;
    }
}