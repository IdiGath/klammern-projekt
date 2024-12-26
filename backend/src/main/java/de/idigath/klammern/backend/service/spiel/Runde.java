package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.spiel.phase.Phase;
import de.idigath.klammern.backend.service.spiel.phase.Ruhe;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;

/**
 * Die Klasse, welche die Runde einer Partie darstellt. Jede Runde besteht aus mehreren Phasen. Die
 * jeweilige Phase wird mit dem Interfache Phase abgebildet. Die Phasenlogik soll sich im Laufe des
 * Spiels ändern, ohne, dass Logikanpassungen in dieser Klasse. Da eine Partie in mehreren Runden
 * gespielt wird, sollen sie immer neu erzeugt werden. Als Folge soll diese Klasse niemals als
 * {@code @Service} dargestellt werden.
 *
 * @see de.idigath.klammern.backend.service.Partie
 * @see Phase
 */
@EqualsAndHashCode
public class Runde {
  private final Stand punkte;
  private Phase phase;

  /**
   * Erzeugt eine neue Instanz der Runde. Die Methode übernimmt den jeweiligen Spieler, wer als
   * Beginner der Runde diesen soll. Der Beginner wird nach der Programmlogik außerhalb der Runde
   * ermittelt.
   *
   * @param beginner Spieler wer die Runde beginnt.
   */
  public Runde(Spieler beginner) {
    punkte = new Stand();
    phase = new Ruhe(beginner, punkte);
  }

  /**
   * Gibt den aktuellen Beginner des Zuges zurück.
   *
   * @return Beginner der Runde
   */
  public Spieler getBeginner() {
    return phase.getBeginner();
  }

  /**
   * Gibt den aktuellen Decker des Zuges zurück.
   *
   * @return Decker der Runde
   */
  public Spieler getDecker() {
    return phase.getDecker();
  }

  /**
   * Gibt den aktuellen Punktestand des Spielers zurück.
   *
   * @return Punktestand des Spielers
   */
  public Integer getSpielerPunkte() {
    return punkte.getPunkte(Spieler.SPIELER);
  }

  /**
   * Gibt den aktuellen Punktestand des Gegners zurück.
   *
   * @return Punktestand des Gegners
   */
  public Integer getGegnerPunkte() {
    return punkte.getPunkte(Spieler.GEGNER);
  }

  /**
   * Gibt die aktuelle Trumpfkarte zurück. Diese Karte dient auch für die Bestimmung der Trumpffarbe
   * im Laufe des Spieles.
   *
   * @return Trumpfkarte
   */
  public Karte getTrumpfKarte() {
    return phase.getTrumpfKarte();
  }

  /**
   * Gibt alle Karten eines Spielers in Form einer Liste zurück.
   *
   * @return Liste mit Karten
   */
  public List<Karte> getSpielerKarten() {
    return phase.getKarten(Spieler.SPIELER);
  }

  /**
   * Gibt alle Karten eines Spielers in Form einer Liste zurück.
   *
   * @return Liste mit Karten
   */
  public List<Karte> getGegnerKarten() {
    return phase.getKarten(Spieler.GEGNER);
  }

  /**
   * Nimmt einen Zug entgegen und verarbeitet ihn gemäß der Phasenlogik. Nach jedem Zug kommt eine
   * Abfrage zu der nächsten Phase für die zukünftigen Züge.
   *
   * @param zug Zuginhalt für die Verarbeitung
   * @see Phase
   */
  public void spieleZug(Zug zug) {
    phase.spieleZug(zug);
    phase = phase.getNext();
  }

  /**
   * Gibt true zurück, wenn die aktuelle Phase in der Runde spielbar ist.
   *
   * @return true, wenn Züge möglich sind
   */
  public boolean isSpielbar() {
    return Objects.nonNull(phase) && !(phase instanceof Ruhe);
  }
}
