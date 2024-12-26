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

  /** {@inheritDoc} */
  @Override
  public Integer getSpielerAugen() {
    return augen.getPunkte(Spieler.SPIELER);
  }

  /** {@inheritDoc} */
  @Override
  public Integer getGegnerAugen() {
    return augen.getPunkte(Spieler.GEGNER);
  }

  /** {@inheritDoc} */
  @Override
  public Integer getSpielerPunkte() {
    return runde.getSpielerPunkte();
  }

  /** {@inheritDoc} */
  @Override
  public Integer getGegnerPunkte() {
    return runde.getGegnerPunkte();
  }

  /** {@inheritDoc} */
  @Override
  public List<Stand> getHistorie() {
    List<Stand> result = new LinkedList<>();
    for (Stand element : historie) {
      result.add(new Stand(element));
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public Spieler getBeginner() {
    return runde.getBeginner();
  }

  /** {@inheritDoc} */
  @Override
  public Spieler getDecker() {
    return runde.getDecker();
  }

  /** {@inheritDoc} */
  @Override
  public Karte getTrumpfKarte() {
    return runde.getTrumpfKarte();
  }

  /** {@inheritDoc} */
  @Override
  public List<Karte> getSpielerKarten() {
    return runde.getSpielerKarten();
  }

  /** {@inheritDoc} */
  @Override
  public List<Karte> getGegnerKarten() {
    return runde.getGegnerKarten();
  }

  /** {@inheritDoc} */
  @Override
  public void aufgeben() {
    aktualisiereStand();
    gewinner = Spieler.GEGNER;
  }

  /** {@inheritDoc} */
  @Override
  public void neuBeginnen() {
    if (gewinner.equals(Spieler.NIEMAND)) {
      throw new IllegalStateException(
          "Die Partie ist noch nicht beendet um sie neu beginnen zu können");
    }
    initPartie();
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public boolean isFertig() {
    return !gewinner.equals(Spieler.NIEMAND)
        || getGegnerAugen() >= ZIEL_AUGEN
        || getSpielerAugen() >= ZIEL_AUGEN;
  }

  /** {@inheritDoc} */
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
