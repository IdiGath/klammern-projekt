package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.Partie;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

/**
 * Standardimplementierung der Partie. Jede Partie besteht aus der gespielten Runde, einen Stand in
 * Augen sowie seine Historie in Punkten. Solange das Spiel nur für einen Spieler geeignet ist, wird
 * die Klasse als ein Bean zur Verfügung gestellt, weil mehrere Partien nicht unterstützt werden
 * müssen.
 */
@Service
@EqualsAndHashCode
public class PartieImpl implements Partie {
  private static final int ZIEL_AUGEN = 51;
  private final Random random = new Random();
  private List<Stand> historie;
  private Stand augen;
  private Runde runde;
  private Spieler gewinner;

  /** Erstellt eine neue Instanz der Partie. */
  public PartieImpl() {
    initPartie();
  }

  private void initPartie() {
    runde = new Runde(ermittleBeginner());
    augen = new Stand();
    historie = new LinkedList<>();
    gewinner = Spieler.NIEMAND;
  }

  private Spieler ermittleBeginner() {
    return random.nextBoolean() ? Spieler.SPIELER : Spieler.GEGNER;
  }

  /**
   * Gibt den Augenstand des Spielers zurück.
   *
   * @return Augen
   */
  @Override
  public Integer getSpielerAugen() {
    return augen.getPunkte(Spieler.SPIELER);
  }

  /**
   * Gibt den Augenstand des Gegners zurück.
   *
   * @return Augen
   */
  @Override
  public Integer getGegnerAugen() {
    return augen.getPunkte(Spieler.GEGNER);
  }

  /**
   * Gibt den Punktestand des Spielers in der aktuellen Runde zurück.
   *
   * @return Punkte
   */
  @Override
  public Integer getSpielerPunkte() {
    return runde.getSpielerPunkte();
  }

  /**
   * Gibt den Punktestand des Gegners in der aktuellen Runde zurück.
   *
   * @return Punkte
   */
  @Override
  public Integer getGegnerPunkte() {
    return runde.getGegnerPunkte();
  }

  /**
   * Gibt eine neue Kopie des Standhistorie zurück.
   *
   * @return Historie in Form einer Liste
   */
  @Override
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
  @Override
  public Spieler getBeginner() {
    return runde.getBeginner();
  }

  /**
   * Gibt aktuelle Trumpfkarte zurück.
   *
   * @return Trumpfkarte
   */
  @Override
  public Karte getTrumpfKarte() {
    return runde.getTrumpfKarte();
  }

  /**
   * Gibt Karten des Spielers zurück.
   *
   * @return Kartenliste
   */
  @Override
  public List<Karte> getSpielerKarten() {
    return runde.getSpielerKarten();
  }

  /**
   * Gibt Karten des Gegners zurück.
   *
   * @return Kartenliste
   */
  @Override
  public List<Karte> getGegnerKarten() {
    return runde.getGegnerKarten();
  }

  /** Der Spieler gibt die jeweilige Partie aus. */
  @Override
  public void aufgeben() {
    aktualisiereStand();
    gewinner = Spieler.GEGNER;
  }

  /** Beginnt die Parte von neu. */
  @Override
  public void neuBeginnen() {
    if (gewinner.equals(Spieler.NIEMAND)) {
      throw new IllegalStateException(
          "Die Partie ist noch nicht beendet um sie neu beginnen zu können");
    }
    initPartie();
  }

  /**
   * Spielt der Zug für die aktuelle Runde. Wenn nach dem Zug die Runde nicht mehr spielbar ist,
   * wird sie beendet und geprüft, ob die Partie ggf. ihr Ende auch erreicht hat.
   *
   * @param zug Spielzug
   */
  @Override
  public void spieleZug(Zug zug) {
    runde.spieleZug(zug);
    if (!runde.isSpielbar()) {
      beendeRunde();
      if (isFertig()) {
        beendePartie();
      }
    }
  }

  /**
   * Gibt die Information zurück, ob die aktuelle Partie beendet ist.
   *
   * @return true wenn Partie beendet ist
   */
  @Override
  public boolean isFertig() {
    return !gewinner.equals(Spieler.NIEMAND)
        || getGegnerAugen() >= ZIEL_AUGEN
        || getSpielerAugen() >= ZIEL_AUGEN;
  }

  /**
   * Gib zurück den gewinner der Partie. Bis es Entschieden ist, kommt der Wert vom Spieler.NIEMAND
   * zurück.
   *
   * @return Gewinner der Partie
   * @see de.idigath.klammern.backend.model.Spieler
   */
  @Override
  public Spieler getGewinner() {
    return gewinner;
  }

  private void beendeRunde() {
    aktualisiereStand();
    runde = new Runde(runde.getBeginner());
  }

  private void aktualisiereStand() {
    Stand stand = new Stand();
    stand.addPunkte(Spieler.GEGNER, runde.getGegnerPunkte());
    stand.addPunkte(Spieler.SPIELER, runde.getSpielerPunkte());
    historie.add(stand);
    augen.addPunkte(Spieler.SPIELER, convertPunkteInAugen(runde.getSpielerPunkte()));
    augen.addPunkte(Spieler.GEGNER, convertPunkteInAugen(runde.getGegnerPunkte()));
  }

  private int convertPunkteInAugen(int punkte) {
    return Math.round((float) punkte / 10);
  }

  private void beendePartie() {
    aktualisiereStand();
    gewinner = ermittleGewinner();
  }

  private Spieler ermittleGewinner() {
    if (augen.getPunkte(Spieler.GEGNER) < ZIEL_AUGEN
        || augen.getPunkte(Spieler.SPIELER) < ZIEL_AUGEN) {
      return Spieler.NIEMAND;
    }

    if (augen.getPunkte(Spieler.GEGNER).equals(augen.getPunkte(Spieler.SPIELER))) {
      return Spieler.NIEMAND;
    }

    return augen.getPunkte(Spieler.GEGNER) > augen.getPunkte(Spieler.SPIELER)
        ? Spieler.GEGNER
        : Spieler.SPIELER;
  }
}
