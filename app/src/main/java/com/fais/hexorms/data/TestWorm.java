package com.fais.hexorms.data;

import java.util.Random;

/**
 * Created by paweldylag on 30/11/15.
 */
public class TestWorm extends Worm {

    public TestWorm(int id) {
        super(id);
        this.health = 5;
        this.maxHealth = 15;
    }

    @Override
    public int rotate(boolean[] b) {
        boolean properDirection = false;
        int guess = Constants.NO_DIRECTION;
        int guessCounter = 0;
        while (!properDirection || guessCounter == 10) {
            guessCounter++;
            guess = new Random().nextInt(6) + 1;
            if (b[guess - 1]) {
                properDirection = true;
            }
        }
        return guess;
    }

    @Override
    public Worm makeChild(int childId) {
        return new TestWorm(childId);
    }

    @Override
    public void loseSomeHealth() {
        this.health--;
    }

    @Override
    public void passGenes(int [] genes) {}



}
