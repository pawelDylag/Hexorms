package com.fais.hexorms.data;

/**
 * Created by paweldylag on 24/11/15.
 */
public class Worm {

    private int id;
    private int health;
    // zycie przy ktorym robak sie rozdwaja
    private int maxHealth;
    private Hex hex;

    public Worm(int id) {
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
    public int rotate() {
        // TODO: IMPLEMENT THIS
        // tu trzeba zrobic obrot robaka w zaleznosci od jego genomu
        // jak okaze sie, ze pojscie w tym kierunku jest niemozliwe
        // (np jest juz zajete przez innego robaka)
        // to ta metoda jest wykonywana jeszcze raz do skutku
        return 0;
    }

    /**
     * Tworzy nowe dziecko o podanym od gory ID
     * @param childId
     * @return
     */
    public Worm makeChild(int childId){
        // TODO: IMPLEMENT THIS
        //ROBIMY JEDNO DZIECKO! Tutaj musimy przekazac dzieciakowi geny
        // ustawic mu nowe id
        // ta metoda zostanie wywolana dwa razy, wiec uspokoj sie... nie tak szybko!
        return new Worm(childId);
    }

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
    private void loseSomeHealth() {
        // TODO: IMPLEMENT THIS
        // robal powinien tracic tyle zycia co krok,
        // na ile pozwala mu jego genom. Czyli tutaj beda rozne wyniki dla roznych robali.
        this.health--;
    }

    /**
     * Dodaje zycie po zjedzeniu bakterii
     */
    private void gainSomeHealth() {
        this.health += Constants.BACTERIA_BONUS;
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
