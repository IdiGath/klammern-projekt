package de.idigath.klammern.backend.service.vergleich;

import de.idigath.klammern.backend.model.Wert;
import java.util.Comparator;
import java.util.Objects;

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

    Comparator<Wert> comparator = ermittleComparator(vergleichModus);

    return new KartenComparator(comparator);
  }

  private static Comparator<Wert> ermittleComparator(VergleichsTyp vergleichModus) {
    return switch (vergleichModus) {
      case REIHENFOLGE -> Comparator.comparingInt(Wert::getReihenfolge);
      case STANDARD -> Comparator.comparingInt(Wert::getStandardWert);
      case TRUMPF -> Comparator.comparingInt(Wert::getTrumpfWert);
    };
  }

  @Override
  public int compare(Wert ersteKarte, Wert zweiteKarte) {
    Objects.requireNonNull(ersteKarte, "Erste Karte darf nicht null sein");
    Objects.requireNonNull(zweiteKarte, "Zweite Karte darf nicht null sein");

    return comparator.compare(ersteKarte, zweiteKarte);
  }
}
