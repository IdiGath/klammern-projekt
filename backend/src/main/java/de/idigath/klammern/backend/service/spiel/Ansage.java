package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.*;
import de.idigath.klammern.backend.service.vergleich.KartenComparator;
import de.idigath.klammern.backend.service.vergleich.VergleichsTyp;

import java.util.EnumMap;
import java.util.HashMap;
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
        }

        Map<Karte, Kombination> result = definiereKombinationen(kombinationen);
    }

    private Map<Karte, Kombination> definiereKombinationen(Map<Farbe, Deck> kombinationen) {
        Map<Karte, Kombination> result = new HashMap<>();

        List<Deck> kartenDecks = kombinationen.keySet().stream().map(kombinationen::get).toList();

        for (Deck deck : kartenDecks) {
            validateEinzelkarte(deck);
            validateZuVieleKarten(deck);
            if (isKomplexeKombination(deck)) {

            } else {
                setzeEintrag(result, deck);
            }

        }
        return result;
    }

    private void setzeEintrag(Map<Karte, Kombination> result, Deck deck) {
        var kartenList = deck.getSpielkartenList();
        Farbe farbe = kartenList.getFirst().farbe();
        List<Wert> kartenWertList =
                kartenList.stream()
                        .map(Karte::wert)
                        .toList()
                        .stream()
                        .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                        .toList();
        Karte kombinationHoehe = new Karte(farbe, kartenWertList.getLast());

        if (isFuenfziger(kartenWertList)) {
            result.put(kombinationHoehe, Kombination.FUENFZIGER);
        } else if (isTerz(kartenWertList)) {
            result.put(kombinationHoehe, Kombination.TERZ);
        } else if (isBelle(kartenWertList, farbe)) {
            result.put(kombinationHoehe, Kombination.BELLE);
        } else {
            throw new IllegalArgumentException("Übergebene Karten stellen keine gültige Kombination dar!");
        }

    }

    private boolean isBelle(List<Wert> kartenWertList, Farbe farbe) {
        return kartenWertList.size() == 2
                && kartenWertList.getFirst().equals(Wert.DAME)
                && kartenWertList.getLast().equals(Wert.KOENIG)
                && trumpfKarte.farbe().equals(farbe);
    }

    private boolean isTerz(List<Wert> kartenWertList) {

        //ToDo: Implementieren
        return kartenWertList.size() == 3;
    }

    private boolean isFuenfziger(List<Wert> kartenWertList) {
        //ToDo: Implementieren
        return false;
    }

    private void validateZuVieleKarten(Deck deck) {
        if (deck.countSpielkarten() > 8) {
            throw new IllegalArgumentException("Ein Spieler darf nicht mehr als 8 Karten haben!");
        }
    }

    private boolean isKomplexeKombination(Deck deck) {
        return deck.countSpielkarten() > 4 && deck.countSpielkarten() < 9;
    }

    private void validateEinzelkarte(Deck deck) {
        if (deck.countSpielkarten() == 1) {
            throw new IllegalArgumentException("Einzelkarte ist keine Kombination!");
        }
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
