package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Zug;


public class Ansage extends AbstractPhase implements Phase {
    private boolean nativeTrumpf;
    private boolean ansageFertig;

    public Ansage(PhasenInfo phasenInfo) {
        this.stand = phasenInfo.stand();
        this.beginner = phasenInfo.beginner();
        this.nativeTrumpf = phasenInfo.nativeTrump();
        this.reihen = phasenInfo.reihen();
        this.spielDeck = phasenInfo.spielDeck();
        this.trumpfKarte = phasenInfo.trumpfKarte();

    }

    @Override
    public void spieleZug(Zug zug) {
        validateZug(zug);
        isTrumpfkarteWechsel();
    }

    private void isTrumpfkarteWechsel() {


    }

    @Override
    public Phase getNext() {
        if (ansageFertig) {
            PhasenInfo phasenInfo = new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte, nativeTrumpf);
            return new Aktion(phasenInfo);
        }
        return this;
    }
}
