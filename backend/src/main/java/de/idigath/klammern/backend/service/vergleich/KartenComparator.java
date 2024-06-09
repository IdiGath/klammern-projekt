package de.idigath.klammern.backend.service.vergleich;

import de.idigath.klammern.backend.model.Wert;

import java.util.Comparator;

/**
 * Die Klasse dient dem Kartenvergleich in einer Klammern-Runde. Abhängig von dem Trumpfparameter
 * wird die passende Vergleichsklasse verwendet, welche auf einen direkten Weg nicht zur Verfügung
 * stehen soll.
 */
public class KartenComparator implements Comparator<Wert> {

    private final Comparator<Wert> comparator;

    private KartenComparator(Comparator<Wert> comparator) {
        this.comparator = comparator;
    }

    /**
     * Erzeugt eine neue Instanz für den Kartenvergleich (Reihenfolge, Standard- oder Trumpf-Wert).
     *
     * @param vergleichModus Hinweis nach welchem Wert die Karten verglichen werden soll.
     * @return neue Instanz
     */
    public static KartenComparator createKartenWertComparator(VergleichsTyp vergleichModus) {

        return switch (vergleichModus) {
            case REIHENFOLGE -> new KartenComparator(new KartenComparatorReihenfolge());
            case STANDARD -> new KartenComparator(new KartenComparatorStandard());
            case TRUMPF -> new KartenComparator(new KartenComparatorTrumpf());
        };
    }

    @Override
    public int compare(Wert ersteKarte, Wert zweiteKarte) {
        if (ersteKarte == null || zweiteKarte == null) {
            throw new NullPointerException("Kartenwerte können nicht verglichen werden");
        }

        return comparator.compare(ersteKarte, zweiteKarte);
    }
}
