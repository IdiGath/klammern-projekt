package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Kombination;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.vergleich.KartenComparator;
import de.idigath.klammern.backend.service.vergleich.VergleichsTyp;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static de.idigath.klammern.backend.model.DeckTyp.REIHE;

public class Ansage extends AbstractPhase implements Phase {
    private final boolean nativeTrumpf;
    private boolean ansageFertig;

    public Ansage(final PhasenInfo phasenInfo) {
        this.stand = phasenInfo.stand();
        this.beginner = phasenInfo.beginner();
        this.nativeTrumpf = phasenInfo.nativeTrump();
        this.reihen = phasenInfo.reihen();
        this.spielDeck = phasenInfo.spielDeck();
        this.trumpfKarte = phasenInfo.trumpfKarte();
    }

    @Override
    public void spieleZug(final Zug zug) {
        validateZug(zug);
        if (isTrumpfkarteWechsel(zug)) {
            trumpfWechseln(zug);
        } else if (isBelle(zug)) {
            stand.addPunkte(zug.getBeginner(), Kombination.BELLE.getPunkte());
        } else {
            var beginnerKombi = ermittleKombinationen(zug.getBeginnerKarten());
            var deckerKombi = ermittleKombinationen(zug.getDeckerKarten());
            verarbeiteAnsage(zug.getBeginner(), beginnerKombi, deckerKombi);
            ansageFertig = true;
        }
    }

    private void trumpfWechseln(final Zug zug) {
        var reihe = reihen.get(zug.getBeginner());
        reihe.addSpielkarte(trumpfKarte);
        trumpfKarte = zug.getBeginnerKarten().getFirst();
    }

    private boolean isBelle(final Zug zug) {
        return zug.getBeginnerKarten().size() == 2
                && zug.getDeckerKarten().isEmpty()
                && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.DAME))
                && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.KOENIG));
    }

    private Map<Karte, Kombination> ermittleKombinationen(final List<Karte> karten) {
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
        return definiereKombinationen(kombinationen);
    }

    private Map<Karte, Kombination> definiereKombinationen(final Map<Farbe, Deck> kombinationen) {
        Map<Karte, Kombination> result = new HashMap<>();

        List<Deck> kartenDecks = kombinationen.keySet().stream().map(kombinationen::get).toList();

        for (Deck deck : kartenDecks) {
            validateEinzelkarte(deck);
            validateZuVieleKarten(deck);
            if (isKomplexeKombination(deck)) {
                addKomplexenEintrag(result, deck);
            } else {
                setzeEintrag(result, deck);
            }
        }
        return result;
    }

    private void addKomplexenEintrag(final Map<Karte, Kombination> result,
                                     final Deck deck) {
        var kartenList = deck.getSpielkartenList();
        // ToDo: Kartenwerte je Farbe sortieren
        Map<Farbe, List<Wert>> farbenMap = new EnumMap<>(Farbe.class);
        farbenMap.put(Farbe.HERZ, new LinkedList<>());
        farbenMap.put(Farbe.KARO, new LinkedList<>());
        farbenMap.put(Farbe.KREUZ, new LinkedList<>());
        farbenMap.put(Farbe.PIK, new LinkedList<>());
        kartenList.forEach(
                karte -> farbenMap.get(karte.farbe()).add(karte.wert()));
        // ToDo: Kartenwerte in jeder Farbe sortieren
        var comparator = KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE);
        farbenMap.forEach((farbe, wertList) -> wertList.sort(comparator));
        // ToDo: Kartenwerte jeder Farbe auf Kombinationen aufteilen
        for (var entry : farbenMap.entrySet()) {
            List<List<Wert>> aufgeteilteKombinationen = new LinkedList<>();
            List<Wert> kartenWertList = entry.getValue();
            // ToDo: Kombination wie einfache Kombination verarbeiten
            Integer untergeordneteReihenfolge = null;
            for (Wert kartenwert : kartenWertList) {
                if (Objects.isNull(aufgeteilteKombinationen.getLast())) {
                    aufgeteilteKombinationen.add(new LinkedList<>());
                }

                if (Objects.isNull(untergeordneteReihenfolge)) {
                    untergeordneteReihenfolge = kartenWertList.getFirst().getReihenfolge() - 1;
                }
                int aktuelleReihenfolge = kartenwert.getReihenfolge();

                if (aktuelleReihenfolge - untergeordneteReihenfolge != 1) {
                    aufgeteilteKombinationen.add(new LinkedList<>());
                }
                aufgeteilteKombinationen.getLast().add(kartenwert);
                untergeordneteReihenfolge = aktuelleReihenfolge;
            }

            for (List<Wert> einzelnKombination : aufgeteilteKombinationen) {
                Deck reihe = DeckFactory.createDeck(REIHE);
                for (var wert : einzelnKombination) {
                    reihe.addSpielkarte(new Karte(entry.getKey(), wert));
                }
                setzeEintrag(result, reihe);
            }
        }
    }

    private void setzeEintrag(Map<Karte, Kombination> result, Deck deck) {
        var kartenList = deck.getSpielkartenList();
        List<Wert> kartenWertList =
                kartenList.stream().map(Karte::wert).toList().stream()
                        .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                        .toList();
        Farbe farbe = kartenList.getFirst().farbe();
        Karte kombinationsHoehe = new Karte(farbe, kartenWertList.getLast());

        if (isFuenfziger(kartenWertList)) {
            result.put(kombinationsHoehe, Kombination.FUENFZIGER);
        } else if (isTerz(kartenWertList)) {
            result.put(kombinationsHoehe, Kombination.TERZ);
        } else {
            throw new IllegalArgumentException(
                    "Übergebene Karten stellen keine gültige Kombination dar!");
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

    private void verarbeiteAnsage(Spieler beginner, Map<Karte, Kombination> beginnerKombi,
                                  Map<Karte, Kombination> deckerKombi) {
        //ToDo: Ansagenlogik verarbeiten
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
            PhasenInfo phasenInfo =
                    new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte, nativeTrumpf);
            return new Aktion(phasenInfo);
        }
        return this;
    }
}
