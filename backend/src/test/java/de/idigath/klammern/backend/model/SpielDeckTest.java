package de.idigath.klammern.backend.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SpielDeckTest {

    @Test
    void giveSpielkarte_zufaelligeKarte_ok() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);

        spielDeck.giveSpielkarte();

        assertThat(spielDeck.countSpielkarten()).isEqualTo(31);
    }

    @Test
    void addSpielkarte_zufaelligeKarte_throwsIllegalStateException() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);

        Karte karte = spielDeck.giveSpielkarte();

        assertThatThrownBy(() -> spielDeck.addSpielkarte(karte))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("In ein SpielDeck können keine weitere Karten hinzugefügt werden");
    }

    @Test
    void giveSpielkarte_pikKarte33Mal_throwsIllegalStateException() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);

        assertThatThrownBy(
                () -> {
                    for (int i = 0; i < 33; i++) {
                        spielDeck.giveSpielkarte();
                    }
                })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Das Deck hat keine passende Karten");
    }

    @Test
    void giveSpielkarte_pikKarte9Mal_throwsIllegalStateException() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);

        assertThatThrownBy(
                () -> {
                    for (int i = 0; i < 9; i++) {
                        spielDeck.giveSpielkarte(Farbe.HERZ);
                    }
                })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Das Deck hat keine passende Karten in der Farbe: ");
    }

    @Test
    void giveSpielkarte_pikKarte_ok() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);
        Farbe testFarbe = Farbe.PIK;

        Karte karte = spielDeck.giveSpielkarte(testFarbe);

        assertThat(karte.farbe()).isEqualTo(testFarbe);
        assertThat(spielDeck.countSpielkarten()).isEqualTo(31);
    }

    @Test
    void giveSpielkarte_kreuzAss_ok() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);
        Karte testKarte = new Karte(Farbe.KREUZ, Wert.ASS);

        Karte karte = spielDeck.giveSpielkarte(testKarte.farbe(), testKarte.wert());

        assertThat(karte).isEqualTo(testKarte);
        assertThat(spielDeck.countSpielkarten()).isEqualTo(31);
    }

    @Test
    void giveSpielkarte_kreuzAssZweiMal_throwsIllegalStateException() {
        Deck spielDeck = DeckFactory.createDeck(DeckTyp.SPIELDECK);
        Farbe testFarbe = Farbe.KREUZ;
        Wert testWert = Wert.ASS;

        spielDeck.giveSpielkarte(testFarbe, testWert);

        assertThatThrownBy(() -> spielDeck.giveSpielkarte(testFarbe, testWert))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Das Deck hat keine passende Karte in der Farbe: ");
    }
}
