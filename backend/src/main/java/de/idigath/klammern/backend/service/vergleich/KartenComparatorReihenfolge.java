package de.idigath.klammern.backend.service.vergleich;

import de.idigath.klammern.backend.model.Wert;

import java.util.Comparator;

/**
 * Die untergeordnete Klasse für Kartenvergleich in einem Klammernspiel. Die Klasse vergleicht
 * Karten anhand dessen Reihenfolge.
 */
class KartenComparatorReihenfolge implements Comparator<Wert> {

    @Override
    public int compare(Wert ersteKarte, Wert zweiteKarte) {
        return Integer.compare(ersteKarte.reihenfolge, zweiteKarte.reihenfolge);
    }
}
