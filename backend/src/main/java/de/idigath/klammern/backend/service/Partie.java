package de.idigath.klammern.backend.service;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import java.util.List;

/**
 * Jede Partie besteht aus der gespielten Runde, einen Stand in Augen sowie seine Historie in
 * Punkten. Die Partie steuert die beinhaltete Elemente und ermöglicht einen Zugriff auf ihren
 * Inhalt.
 */
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
   * Gibt aktuellen Decker des Zuges zurück.
   *
   * @return Decker
   */
  Spieler getDecker();

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

  /** Der Spieler gibt die jeweilige Partie auf. */
  void aufgeben();

  /** Beginnt die Parte von neu. */
  void neuBeginnen();

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
  boolean isFertig();

  /**
   * Gib zurück den gewinner der Partie. Bis es Entschieden ist, kommt der Wert vom Spieler.NIEMAND
   * zurück.
   *
   * @return Gewinner der Partie
   * @see de.idigath.klammern.backend.model.Spieler
   */
  Spieler getGewinner();
}
