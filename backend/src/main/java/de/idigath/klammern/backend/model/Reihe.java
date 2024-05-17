package de.idigath.klammern.backend.model;

import java.util.Set;

/**
 * Die Klasse repräsentiert eine Sammlung der Karten, welche z.B. zum Vergleich gezogen wird oder
 * bei dem Spieler in der Hand ist.
 *
 * @see Deck
 */
public class Reihe extends Deck {
    protected Reihe(Set<Karte> karten) {
        super(karten);
    }

    @Override
    public void addSpielkarte(Karte spielkarte) {
        karten.add(spielkarte);
    }
}
