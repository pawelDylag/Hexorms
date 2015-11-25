package com.fais.hexorms.domain;

import com.fais.hexorms.data.Hex;
import com.fais.hexorms.data.Worm;

import java.util.ArrayList;

/**
 * Created by paweldylag on 24/11/15.
 */
public class WormsManager {

    private ArrayList<Worm> wormList;
    private Simulation simulation;
    private int wormIdCounter = 0;

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
            newWorms.add(new Worm(wormIdCounter++));
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
     * Zabija worma - usuwa go z planszy i z listy wormow
     * @param worm
     */
    private void killWorm(Worm worm) {
        wormList.remove(worm);
        simulation.getBoard().clearHex(worm.getHex());
    }

    /**
     * Ta metodka bierze kierunek worma, sprawdza czy hex w tym kierunku jest wolny i go zwraca
     * jesli nie jest wolny, to strzela do skutku
     * TODO: tutaj moze wystapic zakleszczenie, moze dodac jakis counter i po jego przekroczeniu sie nie ruszac?
     * @param worm
     * @return
     */
    private Hex getPossibleMoveForWorm(Worm worm){
        boolean properDirection = false;
        Hex newHex = null;
        while (!properDirection) {
            newHex = simulation.getBoard().getAdjacentHex(worm.getHex(), worm.rotate());
            if (newHex.isMovePossible()) {
                properDirection = true;
            }
        }
        return newHex;
    }

    /**
     * Glowna petla robiaca ruchy dla kazdego wormsa
     * @return
     */
    public boolean makeMoves() {
        boolean isFinished = false;
        ArrayList<Worm> childs = new ArrayList<>();
        // dla kazdego worma
        for (Worm worm : wormList) {
            if (!worm.isAlive()) {
                // jesli jego health spadl do 0, to usuwamy go z listy wormsow;
                killWorm(worm);
            } else {
                // strzelamy ruch - jesli dane pole jest zajete, to strzelamy do skutku
                Hex newHex = getPossibleMoveForWorm(worm);
                // rysujemy na planszy w nowym miejscu
                simulation.getBoard().move(worm.getHex(), newHex);
                // ustawiamy robakowi nowego hexa
                worm.moveToHex(newHex);
                // sprawdzamy, czy moze sie podzielic
                if (worm.canSplit()) {
                    // 1. Rodzic robi nowe dzieciaki
                    // 2. ustawiamy je na nowych polach
                    // 2. zabijamy ich rodzica (MWAHAHAHAHAHAHAHAHA!)
                    for (int i = 0; i < 2; i++) {
                        Worm child = worm.makeChild(wormIdCounter++);
                        Hex childHex = getPossibleMoveForWorm(child);
                        child.setHex(childHex);
                        childs.add(child);
                        simulation.getBoard().add(childHex.getX(), childHex.getY(), child.getId());
                    }
                    killWorm(worm);
                }
            }
        }
        // dodajemy dzieciaki tak, zeby nie ruszyly sie w tej turze
        wormList.addAll(childs);

        return isFinished;
    }


}
