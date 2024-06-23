package de.idigath.klammern.backend.service.vergleich;

import de.idigath.klammern.backend.model.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Die Klasse dient dem Basisvergleich der Kombinationen in einer Klammern-Runde. Der Vergleich ist
 * notwendig nur für Terzen und Fünfziger, weil nur ein Spieler die Pluspunkte bekommt. Der
 * Vergleich findet ohne äußere Faktoren wie die Anzahl der Kombinationen oder Trumpffarbe.
 */
public class KombinationComparator implements Comparator<Deck> {
    @Override
    public int compare(Deck ersteReihe, Deck zweiteReihe) {
        validateCombination(ersteReihe);
        validateCombination(zweiteReihe);
        int basicResult =
                Integer.compare(ersteReihe.countSpielkarten(), zweiteReihe.countSpielkarten());

        if (basicResult == 0) {
            Wert ersterWert =
                    ersteReihe.getSpielkartenList().stream()
                            .map(Karte::wert)
                            .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                            .iterator()
                            .next();

            Wert zweiterWert =
                    zweiteReihe.getSpielkartenList().stream()
                            .map(Karte::wert)
                            .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                            .iterator()
                            .next();
            return Integer.compare(ersterWert.reihenfolge, zweiterWert.reihenfolge);
        }

        return basicResult;
    }

    private void validateCombination(Deck reihe) {
        if (Objects.isNull(reihe)) {
            throw new NullPointerException("Der übergebene Parameter is null");
        }

        if (reihe.countSpielkarten() != Kombination.TERZ.kartenAnzahl
                && reihe.countSpielkarten() != Kombination.FUENFZIGER.kartenAnzahl) {
            throw new IllegalArgumentException(
                    "Die übergebene Kartenreihe ist keine gültige Kombination für den Vergleich");
        }

        Set<Farbe> farben =
                reihe.getSpielkartenList().stream().map(Karte::farbe).collect(Collectors.toSet());
        if (farben.size() != 1) {
            throw new IllegalArgumentException(
                    "Mehrere Kartenfarben in der Reihenkombination: " + reihe.getSpielkartenList());
        }

        Iterator<Wert> iterator =
                reihe.getSpielkartenList().stream()
                        .map(Karte::wert)
                        .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
                        .iterator();

        Wert ersteSpielkarte = iterator.next();
        while (iterator.hasNext()) {
            Wert folgendeSpielkarte = iterator.next();
            int differenz = folgendeSpielkarte.reihenfolge - ersteSpielkarte.reihenfolge;
            if (differenz != 1) {
                throw new IllegalArgumentException(
                        "Ungültige Reihenfolge in der Reihenkombination: " + reihe.getSpielkartenList());
            }
            ersteSpielkarte = folgendeSpielkarte;
        }
    }
}
