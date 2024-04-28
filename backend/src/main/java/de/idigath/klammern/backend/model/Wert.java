package de.idigath.klammern.backend.model;

/**
 * Enum für Beschreibung der Spielkarten. Jede Karte hat ihre Wertigkeit (gezählt in Punkten) und
 * zwei Reihenfolgen. Die Wertigkeit wird beim Zusammenzählen der Punkte jeweiligem Spieler
 * gutgeschrieben. Die Trumpfreihenfolge unterscheidet sich vom Standard, beide Werte werden beim
 * Comparator verwendet um die "stärkere" Karte zu ermitteln.
 */
public enum Wert {
    SIEBEN(0, 1, 0, 0),
    ACHT(0, 2, 1, 1),
    NEUN(0, 3, 2, 6),
    ZEHN(10, 4, 6, 4),
    BUBE(2, 5, 3, 7),
    DAME(3, 6, 4, 2),
    KOENIG(4, 7, 5, 3),
    ASS(11, 8, 7, 5);

    public final int punktenAnzahl;
    public final int reihenfolge;
    public final int standardWert;
    public final int trumpfWert;

    Wert(int punktenAnzahl, int reihenfolge, int standardWert, int trumpfWert) {
        this.punktenAnzahl = punktenAnzahl;
        this.reihenfolge = reihenfolge;
        this.standardWert = standardWert;
        this.trumpfWert = trumpfWert;
    }
}
