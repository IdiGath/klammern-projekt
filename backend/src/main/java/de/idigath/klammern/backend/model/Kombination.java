package de.idigath.klammern.backend.model;

import lombok.Getter;

/**
 * Enum für die Kombinationen. Jede Kombination hat ihre Wertigkeit (gezählt in Punkten), sowie die
 * Information zu der notwendigen Anzahl der Karten. Die Wertigkeit wird beim Anmelden der
 * Kombination dem spieler gutgeschrieben.
 */
@Getter
public enum Kombination {
  BELLE(20, 2),
  TERZ(20, 3),
  FUENFZIGER(50, 4);

  private final int punkte;

  private final int kartenAnzahl;

  Kombination(final int punkte, final int kartenAnzahl) {
    this.punkte = punkte;
    this.kartenAnzahl = kartenAnzahl;
  }
}
