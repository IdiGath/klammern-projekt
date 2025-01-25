package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.config.PostgresContainer;
import de.idigath.klammern.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumMap;
import java.util.Map;

import static de.idigath.klammern.backend.model.DeckTyp.SPIELDECK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AktionTest extends PostgresContainer {

    private PhasenInfo createPhasenInfo() {
        Deck deck = DeckFactory.createDeck(SPIELDECK);
        Map<Spieler, Deck> reihen = new EnumMap<>(Spieler.class);
        reihen.put(Spieler.SPIELER, DeckFactory.createDeck(DeckTyp.REIHE));
        reihen.put(Spieler.GEGNER, DeckFactory.createDeck(DeckTyp.REIHE));

        reihen.get(Spieler.SPIELER).addSpielkarte(deck.giveSpielkarte(Farbe.HERZ, Wert.DAME));
        reihen.get(Spieler.SPIELER).addSpielkarte(deck.giveSpielkarte(Farbe.KREUZ, Wert.ACHT));
        reihen.get(Spieler.SPIELER).addSpielkarte(deck.giveSpielkarte(Farbe.KREUZ, Wert.NEUN));
        reihen.get(Spieler.SPIELER).addSpielkarte(deck.giveSpielkarte(Farbe.PIK, Wert.NEUN));

        reihen.get(Spieler.GEGNER).addSpielkarte(deck.giveSpielkarte(Farbe.HERZ, Wert.KOENIG));
        reihen.get(Spieler.GEGNER).addSpielkarte(deck.giveSpielkarte(Farbe.HERZ, Wert.ZEHN));
        reihen.get(Spieler.GEGNER).addSpielkarte(deck.giveSpielkarte(Farbe.KREUZ, Wert.BUBE));
        reihen.get(Spieler.GEGNER).addSpielkarte(deck.giveSpielkarte(Farbe.KARO, Wert.ACHT));

        var trumpfKarte = deck.giveSpielkarte(Farbe.KREUZ, Wert.DAME);

        Stand stand = new Stand();
        return new PhasenInfo(Spieler.SPIELER, stand, deck, reihen, trumpfKarte, true);
    }

    @Test
    void getNext_spieleDiePartieDurch_ok() {
        var phasenInfo = createPhasenInfo();

        var ersterZug = new Zug();
        ersterZug.setBeginner(Spieler.SPIELER);
        ersterZug.setDecker(Spieler.GEGNER);
        ersterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.DAME));
        ersterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.HERZ, Wert.KOENIG));

        var phase = new Aktion(phasenInfo);
        phase.spieleZug(ersterZug);
        assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7);

        var zweiterZug = new Zug();
        zweiterZug.setBeginner(phase.getBeginner());
        zweiterZug.setDecker(phase.getDecker());
        zweiterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.HERZ, Wert.ZEHN));
        zweiterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ACHT));
        phase.spieleZug(zweiterZug);
        assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
        assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7);

        var dritterZug = new Zug();
        dritterZug.setBeginner(phase.getBeginner());
        dritterZug.setDecker(phase.getDecker());
        dritterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.NEUN));
        dritterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.KREUZ, Wert.BUBE));

        phase.spieleZug(dritterZug);
        assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
        assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7 + 36);

        var letzterZug = new Zug();
        letzterZug.setBeginner(phase.getBeginner());
        letzterZug.setDecker(phase.getDecker());
        letzterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.KARO, Wert.ACHT));
        letzterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.PIK, Wert.NEUN));

        phase.spieleZug(letzterZug);

        assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
        assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7 + 36 + 10);
        assertThat(phase.getNext()).isInstanceOf(Ruhe.class);
    }
}
