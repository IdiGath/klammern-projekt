package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Abstrakte Klassen für alle Phasen. Jede Phase hat die gleichen Kernparameter, sowie die
 * Grundfunktionalität für die Auskunft.
 */
public abstract class AbstractPhase implements Phase {
  Stand stand;
  Spieler beginner;
  Map<Spieler, Deck> reihen;
  Deck spielDeck;
  Karte trumpfKarte;

  void validateZug(Zug zug) {
    if (Objects.isNull(zug) || !zug.isVollstaendig()) {
      throw new IllegalArgumentException("Beginner-Zug muss gefüllt sein");
    }

    if (!beginner.equals(zug.getBeginner())) {
      throw new IllegalArgumentException("Beginner ist der andere Spieler");
    }
  }

  @Override
  public Spieler getBeginner() {
    return beginner;
  }

  @Override
  public Spieler getDecker() {
    return beginner.equals(Spieler.SPIELER) ? Spieler.GEGNER : Spieler.SPIELER;
  }

  @Override
  public Karte getTrumpfKarte() {
    return trumpfKarte;
  }

  @Override
  public List<Karte> getKarten(Spieler spieler) {
    return reihen.get(spieler).getSpielkartenList();
  }
}
