package de.idigath.klammern.backend.model;

import lombok.Getter;

/**
 * Enum für Beschreibung der Spielkarten. Jede Karte hat ihre Wertigkeit (gezählt in Punkten) und
 * zwei Reihenfolgen. Die Wertigkeit wird beim Zählen der Punkte jeweiligem Spieler gutgeschrieben.
 * Die Trumpfreihenfolge unterscheidet sich vom Standard, beide Werte werden beim Comparator
 * verwendet um die "stärkere" Karte zu ermitteln.
 */
@Getter
public enum Wert {
  UNDEFINED(0, 0, 0, 0, "UNDEFINED", false),
  SIEBEN(0, 1, 0, 0, "SIEBEN", true),
  ACHT(0, 2, 1, 1, "ACHT", true),
  NEUN(0, 3, 2, 6, "NEUN", true),
  ZEHN(10, 4, 6, 4, "ZEHN", true),
  BUBE(2, 5, 3, 7, "BUBE", true),
  DAME(3, 6, 4, 2, "DAME", true),
  KOENIG(4, 7, 5, 3, "KOENIG", true),
  ASS(11, 8, 7, 5, "ASS", true);

  private final int punkteAnzahl;
  private final int reihenfolge;
  private final int standardWert;
  private final int trumpfWert;
  private final String name;
  private final boolean spielbar;

  Wert(
      int punkteAnzahl,
      int reihenfolge,
      int standardWert,
      int trumpfWert,
      String name,
      boolean spielbar) {
    this.punkteAnzahl = punkteAnzahl;
    this.reihenfolge = reihenfolge;
    this.standardWert = standardWert;
    this.trumpfWert = trumpfWert;
    this.name = name;
    this.spielbar = spielbar;
  }
}
