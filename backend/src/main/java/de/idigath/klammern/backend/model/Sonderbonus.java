package de.idigath.klammern.backend.model;

/**
 * Enum für die Sonderboni. Jeder Bonus hat seine Wertigkeit (gezählt in Punkten). Die Wertigkeit
 * wird jeweiligem Spieler gutgeschrieben. Die Enum wird in keinem Compiler verwendet, weil die
 * Vergleichslogik sehr stark von dem Stand der jeweiligen Runde abhängt.
 */
public enum Sonderbonus {
  MIE(14),
  JAPPA(20),
  LETZTER_STICH(10);

  public final int punkte;

  Sonderbonus(int punkte) {
    this.punkte = punkte;
  }
}
