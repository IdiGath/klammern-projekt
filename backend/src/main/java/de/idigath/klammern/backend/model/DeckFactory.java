package de.idigath.klammern.backend.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Fabrik-Klasse um verschiedene Instanzen des Decks zu erzeugen
 */
public class DeckFactory {

    private DeckFactory() {
        throw new AssertionError(
                "Unterdrückung vom standard Konstruktor um die Instanziierung zu verbieten");
    }

    /**
     * Erzeugt eine neue Instanz des Decks. Der geforderter Typ wird anhand des übergebenen Typs
     * festgelegt.
     *
     * @param typ Hinweis welches Deck erzeugt werden soll.
     * @return neue Instanz
     * @see DeckTyp
     */
    public static Deck createDeck(DeckTyp typ) {
        return switch (typ) {
            case SPIELDECK -> getSpielDeck();
            case REIHE -> getReihe();
        };
    }

    private static Reihe getReihe() {
        return new Reihe(new LinkedHashSet<>());
    }

    private static SpielDeck getSpielDeck() {
        Set<Karte> karten = new LinkedHashSet<>();
        for (Farbe farbe : Farbe.values()) {
            for (Wert wert : Wert.values()) {
                Karte karte = new Karte(farbe, wert);
                karten.add(karte);
            }
        }
        return new SpielDeck(karten);
    }
}
