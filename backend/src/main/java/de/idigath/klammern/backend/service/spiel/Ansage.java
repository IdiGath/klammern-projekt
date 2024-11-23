package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.*;
import de.idigath.klammern.backend.service.vergleich.KartenComparator;
import de.idigath.klammern.backend.service.vergleich.VergleichsTyp;

import java.util.*;

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
        } else if (isBelle(zug)) {
            stand.addPunkte(zug.getBeginner(), Kombination.BELLE.getPunkte());
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

    private boolean isBelle(Zug zug) {
        return zug.getBeginnerKarten().size() == 2
                && zug.getDeckerKarten().isEmpty()
                && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.DAME))
                && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.KOENIG));
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
                setzeKomplexenEintrag(result, deck);
            } else {
                setzeEintrag(result, deck);
            }

        }
        return result;
    }

    private void setzeKomplexenEintrag(Map<Karte, Kombination> result, Deck deck) {
        var kartenList = deck.getSpielkartenList();

        //ToDo: Zuerst karten je Farbe sortieren


        List<Wert> kartenWertList =
                kartenList.stream()
                        .map(Karte::wert)
                        .toList()
                        .stream()
                        .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                        .toList();

        Map<List<Wert>> aufgeteilteKombinationen = new LinkedList<>();


        Integer untergeordneteReihenfolge = null;
        List<Wert> kartenkombination = null;
        for (Wert kartenwert : kartenWertList) {

            if (Objects.isNull(kartenkombination)) {
                kartenkombination = new LinkedList<>();
            }

            if (Objects.isNull(untergeordneteReihenfolge)) {
                untergeordneteReihenfolge = kartenWertList.getFirst().getReihenfolge() - 1;
            }
            int aktuelleReihenfolge = kartenwert.getReihenfolge();


            if (aktuelleReihenfolge - untergeordneteReihenfolge != 1) {
                kartenkombination.add(kartenwert);
                //ToDo: Das ist falsch
                aufgeteilteKombinationen.add(kartenkombination);
            } else {
                kartenkombination = new LinkedList<>();
                kartenkombination.add(kartenwert);

            }
            untergeordneteReihenfolge = aktuelleReihenfolge;
        }

        for (List<Wert> kombinationen : aufgeteilteKombinationen) {
            setzeEintrag(result, kombinationen);
        }


    }

    private void setzeEintrag(Map<Karte, Kombination> result, Deck deck) {
        var kartenList = deck.getSpielkartenList();
        List<Wert> kartenWertList =
                kartenList.stream()
                        .map(Karte::wert)
                        .toList()
                        .stream()
                        .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                        .toList();
        Farbe farbe = kartenList.getFirst().farbe();
        Karte kombinationsHoehe = new Karte(farbe, kartenWertList.getLast());

        if (isFuenfziger(kartenWertList)) {
            result.put(kombinationsHoehe, Kombination.FUENFZIGER);
        } else if (isTerz(kartenWertList)) {
            result.put(kombinationsHoehe, Kombination.TERZ);
        } else {
            throw new IllegalArgumentException("Übergebene Karten stellen keine gültige Kombination dar!");
        }
    }

    private boolean isTerz(List<Wert> kartenWertList) {
        return kartenWertList.size() == 3 && isKartenReihenfolge(kartenWertList);
    }

    private boolean isKartenReihenfolge(List<Wert> kartenWertList) {
        boolean result = false;
        int untergeordneteReihenfolge = kartenWertList.getFirst().getReihenfolge() - 1;
        for (Wert kartenwert : kartenWertList) {
            result = true;
            int aktuelleReihenfolge = kartenwert.getReihenfolge();
            if (aktuelleReihenfolge - untergeordneteReihenfolge != 1) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isFuenfziger(List<Wert> kartenWertList) {
        return kartenWertList.size() == 4 && isKartenReihenfolge(kartenWertList);
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
