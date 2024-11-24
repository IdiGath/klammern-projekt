package de.idigath.klammern.backend.model;

import lombok.Getter;

/**
 * In Klammern, wie in den allen Kartenspielen des französischen Blattes, existieren vier
 * Kartenfarben: Herz, Karo, Pik und Kreuz.
 */
@Getter
public enum Farbe {
  UNDEFINED("UNDEFINED", false),
  HERZ("HERZ", true),
  KARO("KARO", true),
  PIK("PIK", true),
  KREUZ("KREUZ", true);

  private final String name;
  private final boolean spielbar;

  Farbe(String name, boolean spielbar) {
    this.name = name;
    this.spielbar = spielbar;
  }
}
