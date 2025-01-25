package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.config.PostgresContainer;
import de.idigath.klammern.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumMap;
import java.util.Map;

import static de.idigath.klammern.backend.model.DeckTyp.SPIELDECK;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RuheTest extends PostgresContainer {

    @Test
    void getKarten_keineKartenNachErstellung_ok() {
        Stand stand = new Stand();
        Phase ruhe = new Ruhe(Spieler.SPIELER, stand);

        assertThat(ruhe.getKarten(Spieler.SPIELER)).isEmpty();
        assertThat(ruhe.getKarten(Spieler.GEGNER)).isEmpty();
    }

    @Test
    void spieleZug_beginnDerRunde_ok() {
        Stand stand = new Stand();
        Phase ruhe = new Ruhe(Spieler.GEGNER, stand);
        Zug zug = new Zug();

        assertThatNoException().isThrownBy(() -> ruhe.spieleZug(zug));
    }

    @Test
    void spieleZug_endeDerRunde_ok() {

        Stand stand = new Stand();
        Deck deck = DeckFactory.createDeck(SPIELDECK);
        Map<Spieler, Deck> reihen = new EnumMap<>(Spieler.class);
        reihen.put(Spieler.SPIELER, DeckFactory.createDeck(DeckTyp.REIHE));
        reihen.put(Spieler.GEGNER, DeckFactory.createDeck(DeckTyp.REIHE));

        var phasenInfo =
                new PhasenInfo(
                        Spieler.SPIELER, stand, deck, reihen, new Karte(Farbe.HERZ, Wert.SIEBEN), true);

        Phase ruhe = new Ruhe(phasenInfo);
        Zug zug = new Zug();

        assertThatThrownBy(() -> ruhe.spieleZug(zug))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Die Runde ist bereits beendet");
    }

    @Test
    void getNext_phasenWechsel_ok() {
        Stand stand = new Stand();
        Phase ruhe = new Ruhe(Spieler.GEGNER, stand);

        assertThat(ruhe.getNext()).isInstanceOf(Wahl.class);
    }
}
