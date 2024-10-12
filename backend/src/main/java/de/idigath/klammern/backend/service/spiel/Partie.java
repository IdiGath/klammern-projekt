package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class Partie {
    private final Random random = new Random();
    private List<Stand> historie;
    private Stand augen;
    private Runde runde;

    /**
     * Erstellt eine neue Instanz der Partie
     */
    public Partie() {
        initPartie();
    }

    private Spieler ermittleBeginner() {
        return random.nextBoolean() ? Spieler.SPIELER : Spieler.GEGNER;
    }

    /**
     * Gibt den Augenstand des Spielers zurück.
     *
     * @return Augen
     */
    public Integer getSpielerAugen() {
        return augen.getSpielerPunkte();
    }

    /**
     * Gibt den Augenstand des Gegners zurück.
     *
     * @return Augen
     */
    public Integer getGegnerAugen() {
        return augen.getGegnerPunkte();
    }

    /**
     * Gibt den Punktestand des Spielers in der aktuellen Runde zurück.
     *
     * @return Punkte
     */
    public Integer getSpielerPunkte() {
        return runde.getSpielerPunkte();
    }

    /**
     * Gibt den Punktestand des Gegners in der aktuellen Runde zurück.
     *
     * @return Punkte
     */
    public Integer getGegnerPunkte() {
        return runde.getGegnerPunkte();
    }

    /**
     * Gibt eine neue Kopie des Standhistorie zurück.
     *
     * @return Historie in Form einer Liste
     */
    public List<Stand> getHistorie() {
        List<Stand> result = new LinkedList<>();
        for (Stand element : historie) {
            result.add(new Stand(element));
        }
        return result;
    }

    /**
     * Gibt aktuellen Beginner des Zuges zurück.
     *
     * @return Beginner
     */
    public Spieler getBeginner() {
        return runde.getBeginner();
    }

    /**
     * Gibt aktuelle Trumpfkarte zurück.
     *
     * @return Trumpfkarte
     */
    public Karte getTrumpfKarte() {
        return runde.getTrumpfKarte();
    }

    /**
     * Gibt Karten des Spielers zurück.
     *
     * @return Kartenliste
     */
    public List<Karte> getSpielerKarten() {
        return runde.getSpielerKarten();
    }

    /**
     * Gibt Karten des Gegners zurück.
     *
     * @return Kartenliste
     */
    public List<Karte> getGegnerKarten() {
        return runde.getGegnerKarten();
    }

    /**
     * Der Spieler gibt die jeweilige Partie aus.
     */
    public void aufgeben() {
        initPartie();
    }

    private void initPartie() {
        runde = new Runde(ermittleBeginner());
        augen = new Stand();
        historie = new LinkedList<>();
    }

    /**
     * Spielt der Zug für die aktuelle Runde.
     *
     * @param zug Spielzug
     */
    public void spieleZug(Zug zug) {
        runde.spieleZug(zug);

        if (!runde.isSpielbar()) {
            beendePartie();
        }
    }

    /**
     * Gibt die Information zurück ob die aktuelle Partie beendet ist.
     *
     * @return true wenn Partie beendet ist
     */
    public boolean isBeendet() {
        return false;
    }

    private void beendePartie() {

    }
}
