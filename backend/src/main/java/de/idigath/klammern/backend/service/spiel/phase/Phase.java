package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Zug;
import java.util.List;

/**
 * Ein Interface für die Abbildung aller möglichen Phasen innerhalb einer Spielrunde. Innerhalb
 * jeder Runde erfolgt ein Phasenwechsel in der folgenden Reihenfolge: Ruhe → Ansage → Aktion →
 * Ruhe. Die Ruhe-Phase am Beginn und am Ende unterscheidet sich dadurch, dass der Spieler am Ende
 * keine Karten mehr besitzt. Damit wird die Phasenlogik unauffällig für den Benutzer je Phase
 * geändert.
 */
public interface Phase {

  /**
   * Nimmt einen Zug entgegen und verarbeitet ihn gemäß der Phasenlogik. Nach jedem Zug kommt eine
   * Abfrage zu der nächsten Phase für die zukünftigen Züge.
   *
   * @param zug Zuginhalt für die Verarbeitung
   */
  void spieleZug(Zug zug);

  /**
   * Wechselt die aktuelle Phase zu ihrem Nachfolger. Wenn die Phase noch nicht beendet ist, wird
   * die gleiche Phase zurückgegeben.
   *
   * @return Phase für den nächsten Zug
   */
  Phase getNext();

  /**
   * Aktueller Beginner im Zug.
   *
   * @return Beginner
   */
  Spieler getBeginner();

  /**
   * Gibt die Trumpfkarte zurück.
   *
   * @return Trumpfkarte
   */
  Karte getTrumpfKarte();

  /**
   * Gibt die Karten des jeweiligen Spielers zurück.
   *
   * @param spieler betroffener Spieler
   * @return Karten in From einer Liste
   */
  List<Karte> getKarten(Spieler spieler);
}
