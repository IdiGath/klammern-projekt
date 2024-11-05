package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Die Ruhe-Phase ist ein nicht spielbarer Stand einer Runde. Entweder ist die Runde erst gestartet und soll in die
 * neue Phase wechseln, oder die Runde hat ihr Ende erreicht und soll von der Partie beendet werden.
 */
public class Ruhe implements Phase {
    private final Stand stand;
    private final Map<Spieler, Deck> reihen;
    private final Spieler beginner;
    private Deck spielDeck;
    private Karte trumpfKarte;

    /**
     * Dieser Konstruktor wird ausschließlich beim Beginn einer neuen Runde verwendet. Die Runde übergibt einen
     * leeren Stand, sowie den ermittelten Beginner. Solcher Konstruktor existiert ausschließlich in der Ruhe-Phase,
     * weil nur sie eine Runde eröffnen kann.
     *
     * @param beginner ermittelter Beginner der Runde
     * @param stand    neuer Spielstand
     */
    Ruhe(Spieler beginner, Stand stand) {
        this.stand = stand;
        this.beginner = beginner;
        reihen = new EnumMap<>(Spieler.class);
        reihen.put(Spieler.SPIELER, DeckFactory.createDeck(DeckTyp.REIHE));
        reihen.put(Spieler.GEGNER, DeckFactory.createDeck(DeckTyp.REIHE));
    }

    /**
     * Dieser Konstruktor wird beim Ende einer runde benutzt. In dem Parameter wird die ganze Information aus der
     * vorherigen Phase geliefert.
     *
     * @param phasenInfo Information der vorherigen Phase.
     */
    Ruhe(PhasenInfo phasenInfo) {
        beginner = phasenInfo.beginner();
        stand = phasenInfo.stand();
        spielDeck = phasenInfo.spielDeck();
        reihen = phasenInfo.reihen();
        trumpfKarte = phasenInfo.trumpfKarte();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spieleZug(Zug zug) {
        if (Objects.nonNull(trumpfKarte))
            throw new IllegalStateException("Die Runde ist bereits beendet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Phase getNext() {
        var phasenInfo = new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte);
        return new Wahl(phasenInfo);
    }

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
