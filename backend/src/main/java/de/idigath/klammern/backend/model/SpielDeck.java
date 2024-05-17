package de.idigath.klammern.backend.model;

import java.util.Set;

/**
 * Die Klasse repräsentiert ein Spieldeck, die Unterklasse von Deck. Die Klasse wird mit einem
 * französischen Blatt erzeugt und untersagt das Hinzufügen von den neuen Karten.
 *
 * @see Deck
 */
public class SpielDeck extends Deck {

    protected SpielDeck(Set<Karte> karten) {
        super(karten);
    }

    @Override
    public void addSpielkarte(Karte karte) {
        throw new IllegalStateException(
                "In ein SpielDeck können keine weitere Karten hinzugefügt werden");
    }
}
