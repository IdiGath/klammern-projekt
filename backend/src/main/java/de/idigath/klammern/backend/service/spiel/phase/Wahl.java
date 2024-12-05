package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.DeckTyp;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import java.util.Map;

/**
 * Während dieser Phase findet eine Auswahl der Trumpffarbe statt. Zum Beginn der Phase bekommt
 * jeder Spieler 5 karten. Danach findet die Auswahl statt. In ersten zwei Zügen wird entschieden,
 * ob die vorgeschlagene Farbe gespielt wird. Danach kann jeder Spieler seinen Wunsch spielen. Wenn
 * nach 4 Versuchen der Trumpf doch nicht ausgewählt wird, werden die Karten neu verteilt und die
 * Runde beginnt von vorne.
 */
public class Wahl extends AbstractPhase implements Phase {
  private static final int ANZAHL_KARTEN = 5;
  private int anzTrumpfwahlAbsagen;
  private boolean nativeTrumpf;
  private boolean trumpfAusgewaehlt;

  /**
   * Konstruktor für die Wahlphase.
   *
   * @param beginner Beginner der Phase
   * @param stand übergebener Stand
   * @param reihen initialisierte Reihen
   */
  public Wahl(Spieler beginner, Stand stand, Map<Spieler, Deck> reihen) {
    this.beginner = beginner;
    this.stand = stand;
    this.reihen = reihen;
    phaseNeuBeginnen();
  }

  private void phaseNeuBeginnen() {
    kartenAusteilen();
    anzTrumpfwahlAbsagen = 0;
    nativeTrumpf = true;
    trumpfAusgewaehlt = false;
  }

  private void kartenAusteilen() {
    spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);
    trumpfKarte = spielDeck.giveSpielkarte();
    nativeTrumpf = true;
    for (int i = 0; i < ANZAHL_KARTEN; i++) {
      reihen.get(Spieler.SPIELER).addSpielkarte(spielDeck.giveSpielkarte());
      reihen.get(Spieler.GEGNER).addSpielkarte(spielDeck.giveSpielkarte());
    }
  }

  @Override
  public void spieleZug(Zug zug) {
    validateZug(zug);
    if (isTrumpfWahlAbsage(zug)) {
      absageVerarbeiten(zug);
    } else {
      reingangVerarbeiten(zug);
    }
  }

  @Override
  public Phase getNext() {
    if (trumpfAusgewaehlt) {
      PhasenInfo phasenInfo =
          new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte, nativeTrumpf);
      return new Ansage(phasenInfo);
    }
    return this;
  }

  private boolean isTrumpfWahlAbsage(Zug zug) {
    return zug.getBeginnerKarten().isEmpty();
  }

  private void absageVerarbeiten(Zug zug) {
    beginner = zug.getDecker();
    if (isNeustartErforderlich()) {
      phaseNeuBeginnen();
    }
    anzTrumpfwahlAbsagen++;
  }

  private void reingangVerarbeiten(Zug zug) {
    Karte trumpfAuswahl = zug.getBeginnerKarten().getFirst();
    validateTrumpfAuswahl(trumpfAuswahl);
    if (isNotNativeFarbe(trumpfAuswahl)) {
      nativeTrumpf = false;
      trumpfKarte = new Karte(trumpfAuswahl.farbe(), Wert.UNDEFINED);
    }
    trumpfAusgewaehlt = true;
  }

  private void validateTrumpfAuswahl(Karte trumpfAuswahl) {
    int nativeTrumpVersuche = 2;
    if (anzTrumpfwahlAbsagen < nativeTrumpVersuche && isNotNativeFarbe(trumpfAuswahl)) {
      throw new IllegalArgumentException(
          "Erste zwei Versuche entscheiden über den vorgeschlagenen Trumpf");
    }
  }

  private boolean isNotNativeFarbe(Karte karte) {
    return !trumpfKarte.farbe().equals(karte.farbe());
  }

  private boolean isNeustartErforderlich() {
    int maximaleReingangVersuche = 4;
    return anzTrumpfwahlAbsagen >= maximaleReingangVersuche && !trumpfAusgewaehlt;
  }
}
