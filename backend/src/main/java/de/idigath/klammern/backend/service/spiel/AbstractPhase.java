package de.idigath.klammern.backend.service.spiel;


import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;

import java.util.List;
import java.util.Map;

/**
 * Abstrakte Klassen für alle Phasen. Jede Phase hat die gleichen Kernparameter, sowie die Grundfunktionalität für
 * die Auskunft.
 */
public abstract class AbstractPhase implements Phase {
    Stand stand;
    Spieler beginner;
    Map<Spieler, Deck> reihen;
    Deck spielDeck;
    Karte trumpfKarte;

    /**
     * {@inheritDoc}
     */
    @Override
    public Spieler getBeginner() {
        return beginner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Karte getTrumpfKarte() {
        return trumpfKarte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Karte> getKarten(Spieler spieler) {
        return reihen.get(spieler).getSpielkartenList();
    }


}
