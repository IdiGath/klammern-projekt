package de.idigath.klammern.backend.service.vergleich;

import static org.assertj.core.api.Assertions.assertThat;

import de.idigath.klammern.backend.model.Wert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KartenComparatorTest {

  @Test
  void compare_trumpfWertComparator_ok() {
    List<Wert> zielListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.SIEBEN,
                Wert.ACHT,
                Wert.DAME,
                Wert.KOENIG,
                Wert.ZEHN,
                Wert.ASS,
                Wert.NEUN,
                Wert.BUBE));
    List<Wert> unsortierteListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.NEUN,
                Wert.ACHT,
                Wert.KOENIG,
                Wert.ZEHN,
                Wert.SIEBEN,
                Wert.BUBE,
                Wert.DAME,
                Wert.ASS));

    Comparator<Wert> comparator = KartenComparator.createKartenWertComparator(VergleichsTyp.TRUMPF);
    unsortierteListe.sort(comparator);

    assertThat(zielListe).isEqualTo(unsortierteListe);
  }

  @Test
  void compare_standardWertComparator_ok() {
    List<Wert> zielListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.SIEBEN,
                Wert.ACHT,
                Wert.NEUN,
                Wert.BUBE,
                Wert.DAME,
                Wert.KOENIG,
                Wert.ZEHN,
                Wert.ASS));
    List<Wert> unsortierteListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.NEUN,
                Wert.KOENIG,
                Wert.SIEBEN,
                Wert.BUBE,
                Wert.ZEHN,
                Wert.DAME,
                Wert.ACHT,
                Wert.ASS));

    Comparator<Wert> comparator =
        KartenComparator.createKartenWertComparator(VergleichsTyp.STANDARD);
    unsortierteListe.sort(comparator);

    assertThat(zielListe).isEqualTo(unsortierteListe);
  }

  @Test
  void compare_reihenfolgeComparator_ok() {
    List<Wert> zielListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.SIEBEN,
                Wert.ACHT,
                Wert.NEUN,
                Wert.ZEHN,
                Wert.BUBE,
                Wert.DAME,
                Wert.KOENIG,
                Wert.ASS));
    List<Wert> unsortierteListe =
        new ArrayList<>(
            Arrays.asList(
                Wert.NEUN,
                Wert.KOENIG,
                Wert.SIEBEN,
                Wert.BUBE,
                Wert.ZEHN,
                Wert.DAME,
                Wert.ACHT,
                Wert.ASS));

    Comparator<Wert> comparator =
        KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE);
    unsortierteListe.sort(comparator);

    assertThat(zielListe).isEqualTo(unsortierteListe);
  }

  @Test
  void compare_vergleichMitNull_throwsNullPointerException() {

    Comparator<Wert> comparator =
        KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE);

    Assertions.assertThrows(
        NullPointerException.class,
        () -> comparator.compare(Wert.SIEBEN, null),
        "Kartenwerte können nicht verglichen werden");
  }
}
