package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static de.idigath.klammern.backend.model.DeckTyp.REIHE;


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
            ermittleKombinationen(zug);
            verarbeiteAnsage();
            ansageFertig = true;
        }
    }

    private void trumpfWechseln(Zug zug) {
        var reihe = reihen.get(zug.getBeginner());
        reihe.addSpielkarte(trumpfKarte);
        trumpfKarte = zug.getBeginnerKarten().getFirst();
    }

    private void ermittleKombinationen(Zug zug) {
        List<Karte> karten = zug.getDeckerKarten();
        Map<Farbe, Deck> kombinationen = new EnumMap<>(Farbe.class);

        for (Karte karte : karten) {
            if (kombinationen.containsKey(karte.farbe())) {
                kombinationen.get(karte.farbe()).addSpielkarte(karte);
            } else {
                Deck reihe = DeckFactory.createDeck(REIHE);
                reihe.addSpielkarte(karte);
                kombinationen.put(karte.farbe(), reihe);
            }


            isSonderbehandlungNotwendig();

        }

        Map<Kombination, Reihe> result = new EnumMap<>(Kombination.class);
    }

    private void isSonderbehandlungNotwendig() {
    }

    private void verarbeiteAnsage() {
      /*  Die Reihenfolge ist also: 2×50er>50er>2x Terz>Terz<br>
        Wenn ein Spieler einen Terz bis Dame und ein anderer einen bis König, gewinnt der Spieler mit dem Terz bis
        zum König.
<br>
                Wenn zwei Spieler einen gleichwertigen Terz haben, also zum Beispiel beide einen Terz bis Dame(10,
                Bube, Dame), gewinnt
        die Trumpffarbe bzw. die Erstmeldung.<br>*/
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
