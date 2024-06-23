package de.idigath.klammern.backend.service.vergleich;

import de.idigath.klammern.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class KombinationComparatorTest {

    @Test
    void compare_zweiTerzenUndZweiFuenfziger_ok() {
        Deck kleinerTerz = DeckFactory.createDeck(DeckTyp.REIHE);
        kleinerTerz.addSpielkarte(new Karte(Farbe.HERZ, Wert.ACHT));
        kleinerTerz.addSpielkarte(new Karte(Farbe.HERZ, Wert.NEUN));
        kleinerTerz.addSpielkarte(new Karte(Farbe.HERZ, Wert.ZEHN));
        Deck kleineFuenfziger = DeckFactory.createDeck(DeckTyp.REIHE);
        kleineFuenfziger.addSpielkarte(new Karte(Farbe.KARO, Wert.ZEHN));
        kleineFuenfziger.addSpielkarte(new Karte(Farbe.KARO, Wert.BUBE));
        kleineFuenfziger.addSpielkarte(new Karte(Farbe.KARO, Wert.DAME));
        kleineFuenfziger.addSpielkarte(new Karte(Farbe.KARO, Wert.KOENIG));
        Deck grosserTerz = DeckFactory.createDeck(DeckTyp.REIHE);
        grosserTerz.addSpielkarte(new Karte(Farbe.PIK, Wert.KOENIG));
        grosserTerz.addSpielkarte(new Karte(Farbe.PIK, Wert.DAME));
        grosserTerz.addSpielkarte(new Karte(Farbe.PIK, Wert.ASS));
        Deck grosseFuenfziger = DeckFactory.createDeck(DeckTyp.REIHE);
        grosseFuenfziger.addSpielkarte(new Karte(Farbe.KREUZ, Wert.ASS));
        grosseFuenfziger.addSpielkarte(new Karte(Farbe.KREUZ, Wert.BUBE));
        grosseFuenfziger.addSpielkarte(new Karte(Farbe.KREUZ, Wert.DAME));
        grosseFuenfziger.addSpielkarte(new Karte(Farbe.KREUZ, Wert.KOENIG));
        List<Deck> kombinationListe = new ArrayList<>();
        kombinationListe.add(kleineFuenfziger);
        kombinationListe.add(grosseFuenfziger);
        kombinationListe.add(grosserTerz);
        kombinationListe.add(kleinerTerz);

        kombinationListe.sort(new KombinationComparator());

        assertThat(kombinationListe).hasSize(4);
        assertThat(kombinationListe.get(3)).isEqualTo(grosseFuenfziger);
        assertThat(kombinationListe.get(2)).isEqualTo(kleineFuenfziger);
        assertThat(kombinationListe.get(1)).isEqualTo(grosserTerz);
        assertThat(kombinationListe.get(0)).isEqualTo(kleinerTerz);
    }

    @Test
    void compare_zweiNulls_throwsNullPointerException() {
        List<Deck> kombinationListe = new ArrayList<>();
        kombinationListe.add(null);
        kombinationListe.add(null);

        KombinationComparator comparator = new KombinationComparator();
        assertThatThrownBy(() -> kombinationListe.sort(comparator))
                .isInstanceOf(NullPointerException.class)
                .hasMessageStartingWith("Der übergebene Parameter is null");
    }

    @Test
    void compare_zweiNichtKombinationenWegenKartenAnzahl_throwsIllegalArgumentException() {
        Deck ersteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.ACHT));
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.NEUN));
        Deck zweiteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.ZEHN));
        List<Deck> kombinationListe = new ArrayList<>();
        kombinationListe.add(ersteReihe);
        kombinationListe.add(zweiteReihe);

        KombinationComparator comparator = new KombinationComparator();
        assertThatThrownBy(() -> kombinationListe.sort(comparator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Die übergebene Kartenreihe ist keine gültige Kombination");
    }

    @Test
    void compare_zweiNichtKombinationenWegenFarben_throwsIllegalArgumentException() {
        Deck ersteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.ACHT));
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.NEUN));
        ersteReihe.addSpielkarte(new Karte(Farbe.PIK, Wert.ZEHN));
        Deck zweiteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.ZEHN));
        zweiteReihe.addSpielkarte(new Karte(Farbe.KREUZ, Wert.BUBE));
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.DAME));
        List<Deck> kombinationListe = new ArrayList<>();
        kombinationListe.add(ersteReihe);
        kombinationListe.add(zweiteReihe);

        KombinationComparator comparator = new KombinationComparator();
        assertThatThrownBy(() -> kombinationListe.sort(comparator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Mehrere Kartenfarben in der Reihenkombination:");
    }

    @Test
    void compare_zweiNichtKombinationenWegenReihenfolge_throwsIllegalArgumentException() {
        Deck ersteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.ACHT));
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.NEUN));
        ersteReihe.addSpielkarte(new Karte(Farbe.HERZ, Wert.DAME));
        Deck zweiteReihe = DeckFactory.createDeck(DeckTyp.REIHE);
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.ZEHN));
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.BUBE));
        zweiteReihe.addSpielkarte(new Karte(Farbe.KARO, Wert.KOENIG));
        List<Deck> kombinationListe = new ArrayList<>();
        kombinationListe.add(ersteReihe);
        kombinationListe.add(zweiteReihe);

        KombinationComparator comparator = new KombinationComparator();
        assertThatThrownBy(() -> kombinationListe.sort(comparator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Ungültige Reihenfolge in der Reihenkombination");
    }
}
