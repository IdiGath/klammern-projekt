package de.idigath.klammern.backend.model;

/**
 * Enum für die Kombinationen. Jede Kombination hat ihre Wertigkeit (gezählt in Punkten), sowie die
 * Information zu der notwendigen Anzahl der Karten. Die Wertigkeit wird beim Anmelden der
 * Kombination dem spieler gutgeschrieben.
 */
public enum Kombination {
    BELLE(20, 2),
    TERZ(20, 3),
    FUENFZIGER(50, 4);

    public final int punkte;
    public final int kartenAnzahl;

    Kombination(int punkte, int kartenAnzahl) {
        this.punkte = punkte;
        this.kartenAnzahl = kartenAnzahl;
    }
}