package com.fais.hexorms.domain;

import android.util.Log;

import com.fais.hexorms.data.Constants;
import com.fais.hexorms.data.Hex;
import com.fais.hexorms.data.MrWorm;
import com.fais.hexorms.data.TestWorm;
import com.fais.hexorms.data.Worm;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by paweldylag on 24/11/15.
 */
public class WormsManager {

    private ArrayList<Worm> wormList;
    private Simulation simulation;
    private int wormIdCounter = 2;

    public WormsManager(int wormsCount, Simulation simulation) {
        wormList = buildNewWormList(wormsCount);
        this.simulation = simulation;
    }

    /**
     * Buduje liste nowych wormsow (jeszcze nie sa ustawione na planszy)
     * @param wormsCount
     * @return
     */
    private ArrayList<Worm> buildNewWormList(int wormsCount) {
        ArrayList<Worm> newWorms = new ArrayList<>(wormsCount);
        for (int i = 0; i < wormsCount; i++) {
            newWorms.add(new TestWorm(wormIdCounter++));
        }
        return newWorms;
    }

    public ArrayList<Worm> getWormList() {
        return this.wormList;
    }

    public boolean hasWorms() {
        return !wormList.isEmpty();
    }


    /**
     * Ta metodka bierze kierunek worma, sprawdza czy hex w tym kierunku jest wolny i go zwraca
     * jesli nie jest wolny, to strzela do skutku
     * TODO: tutaj moze wystapic zakleszczenie, moze dodac jakis counter i po jego przekroczeniu sie nie ruszac?
     * @param worm
     * @return
     */
    private int getNewDirectionForWorm(Worm worm){
        boolean[] emptyDirections = new boolean[6];
        // pass all surrounding directions that are possible to move
        for (int i = 0; i< emptyDirections.length; i++ ) {
            emptyDirections[i] = simulation.getBoard().getAdjacentHex(worm.getHex(), (i + 1)).isMovePossible();
        }
        return worm.rotate();
    }


    public void makeRotations() {
        // dla kazdego worma
        Iterator it = wormList.iterator();
        while (it.hasNext()) {
            Worm worm = (Worm) it.next();
            if (!worm.isAlive()) {
                // jesli jego health spadl do 0, to usuwamy go z listy wormsow;
                it.remove();
                simulation.getBoard().clearHex(worm.getHex());
            } else {
                // obracamy robaka
                int dir = getNewDirectionForWorm(worm);
                worm.setDirection(dir);
            }
        }
    }

    /**
     * Glowna petla robiaca ruchy dla kazdego wormsa
     * @return
     */
    public void makeMoves() {
        ArrayList<Worm> childs = new ArrayList<>();
        // dla kazdego worma
        Iterator it = wormList.iterator();
        while (it.hasNext()) {
            Worm worm = (Worm) it.next();
            // ruszamy robaka
            int direction =  worm.getDirection();
            if (direction != Constants.NO_DIRECTION) {
                Hex newHex = simulation.getBoard().getAdjacentHex(worm.getHex(), direction);
                Hex oldHex = worm.getHex();
                // ustawiamy robakowi nowego hexa
                worm.moveToHex(newHex);
                // rysujemy na planszy w nowym miejscu
                simulation.getBoard().move(oldHex, newHex);
            }
            // sprawdzamy, czy moze sie podzielic
            if (worm.canSplit()) {
                for (int i = 0; i < 2; i++) {
                    Worm child = worm.makeChild(wormIdCounter++);
                    int childDirection = getNewDirectionForWorm(worm);
                    if (direction != Constants.NO_DIRECTION) {
                        Hex childHex = simulation.getBoard().getAdjacentHex(worm.getHex(), childDirection);
                        child.setHex(childHex);
                        simulation.getBoard().add(childHex.getX(), childHex.getY(), child.getId(), childDirection);
                        childs.add(child);
                    } else {
                        // child is dead :<
                        child.kill();
                    }
                }
                worm.kill();
                it.remove();
                simulation.getBoard().clearHex(worm.getHex());
            }
        }
        // dodajemy dzieciaki tak, zeby nie ruszyly sie w tej turze, i losujemy im startowe pozycje
        for (Worm w: childs){
            wormList.add(w);
        }
    }


}
