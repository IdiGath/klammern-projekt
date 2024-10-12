package de.idigath.klammern.backend.service;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;

import java.util.List;

public interface Partie {
    /**
     * Gibt den Augenstand des Spielers zurück.
     *
     * @return Augen
     */
    Integer getSpielerAugen();

    /**
     * Gibt den Augenstand des Gegners zurück.
     *
     * @return Augen
     */
    Integer getGegnerAugen();

    /**
     * Gibt den Punktestand des Spielers in der aktuellen Runde zurück.
     *
     * @return Punkte
     */
    Integer getSpielerPunkte();

    /**
     * Gibt den Punktestand des Gegners in der aktuellen Runde zurück.
     *
     * @return Punkte
     */
    Integer getGegnerPunkte();

    /**
     * Gibt eine neue Kopie des Standhistorie zurück.
     *
     * @return Historie in Form einer Liste
     */
    List<Stand> getHistorie();

    /**
     * Gibt aktuellen Beginner des Zuges zurück.
     *
     * @return Beginner
     */
    Spieler getBeginner();

    /**
     * Gibt aktuelle Trumpfkarte zurück.
     *
     * @return Trumpfkarte
     */
    Karte getTrumpfKarte();

    /**
     * Gibt Karten des Spielers zurück.
     *
     * @return Kartenliste
     */
    List<Karte> getSpielerKarten();

    /**
     * Gibt Karten des Gegners zurück.
     *
     * @return Kartenliste
     */
    List<Karte> getGegnerKarten();

    /**
     * Der Spieler gibt die jeweilige Partie aus.
     */
    void aufgeben();

    /**
     * Spielt der Zug für die aktuelle Runde.
     *
     * @param zug Spielzug
     */
    void spieleZug(Zug zug);

    /**
     * Gibt die Information zurück, ob die aktuelle Partie beendet ist.
     *
     * @return true wenn Partie beendet ist
     */
    boolean isBeendet();
}