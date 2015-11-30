package com.fais.hexorms.data;

import android.util.Log;

/**
 * Created by paweldylag on 24/11/15.
 */
public abstract class Worm {

    private int id;
    protected int health;
    // zycie przy ktorym robak sie rozdwaja
    protected int maxHealth;
    protected Hex hex;
    protected int direction;

    public Worm(int id)
    {
        this.id = id;
    }


    /**
     * Sprawdza, czy nasz robal jeszcze zyje!
     * @return
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Zwraca kierunek w jakim chce poruszyc sie robal.
     * @return
     */
    public abstract int rotate();

    /**
     * Tworzy nowe dziecko o podanym od gory ID
     * @param childId
     * @return
     */
    public abstract Worm makeChild(int childId);

    /**
     * ta metodka podmienia hexa na nowego.
     * Jesli na nwoym miejscu jest bakteria, to ja zjadamy
     * @param newHex
     */
    public void moveToHex(Hex newHex) {
        // tracimy troche zycia
        this.loseSomeHealth();
        // sprawdzamy, czy jest bakteria na nowym polu
        if (newHex.getContent() == Constants.BACTERIA_HEX) {
            // zjadamy bakterie i zdobywamy zycie!!
            gainSomeHealth();
        }
        this.hex = newHex;
    }

    /**
     * Sprawdza warunek do podzialu
     * @return
     */
    public boolean canSplit() {
        return this.health >= this.maxHealth;
    }

    /**
     * Odejmuje porcje zycia robalowi.
     */
    public abstract void loseSomeHealth();

    public abstract void passGenes(int [] genes);

    public void kill() {
        this.health = 0;
    }

    /**
     * Dodaje zycie po zjedzeniu bakterii
     */
    private void gainSomeHealth() {
        Log.d("WORM", "OMNOMNOMNOMNOMN BACTERIA!!!!!");
        this.health += Constants.BACTERIA_BONUS;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        this.hex.setContentDirection(direction);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hex getHex() {
        return hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }


}
