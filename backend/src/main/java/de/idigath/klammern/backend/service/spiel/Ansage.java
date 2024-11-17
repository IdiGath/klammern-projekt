package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;


public class Ansage extends AbstractPhase implements Phase {
    private final boolean nativeTrumpf;
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
        if (isTrumpfkarteWechsel(zug)) {
            trumpfWechseln(zug);
        } else {
            validateAnsage();
            ansageFertig = true;
        }
    }

    private void validateAnsage() {
    }

    private void trumpfWechseln(Zug zug) {
        var reihe = reihen.get(zug.getBeginner());
        reihe.addSpielkarte(trumpfKarte);
        trumpfKarte = zug.getBeginnerKarten().getFirst();
    }

    private boolean isTrumpfkarteWechsel(Zug zug) {
        return zug.getBeginnerKarten().size() == 1
                && zug.getDeckerKarten().isEmpty()
                && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.SIEBEN))
                && nativeTrumpf;
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
