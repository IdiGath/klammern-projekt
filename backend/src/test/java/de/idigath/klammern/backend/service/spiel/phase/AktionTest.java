package de.idigath.klammern.backend.service.spiel.phase;

import static de.idigath.klammern.backend.model.DeckTyp.SPIELDECK;
import static org.assertj.core.api.Assertions.assertThat;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.DeckTyp;
import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AktionTest {

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
    zweiterZug.setBeginner(Spieler.GEGNER);
    zweiterZug.setDecker(Spieler.SPIELER);
    zweiterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.HERZ, Wert.ZEHN));
    zweiterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ACHT));
    phase.spieleZug(zweiterZug);
    assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
    assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7);

    var dritterZug = new Zug();
    dritterZug.setBeginner(Spieler.SPIELER);
    dritterZug.setDecker(Spieler.GEGNER);
    dritterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.NEUN));
    dritterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.KREUZ, Wert.BUBE));

    phase.spieleZug(dritterZug);
    assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
    assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7 + 36);

    var letzterZug = new Zug();
    letzterZug.setBeginner(Spieler.GEGNER);
    letzterZug.setDecker(Spieler.SPIELER);
    letzterZug.addKarte(Spieler.GEGNER, new Karte(Farbe.KARO, Wert.ACHT));
    letzterZug.addKarte(Spieler.SPIELER, new Karte(Farbe.PIK, Wert.NEUN));

    phase.spieleZug(letzterZug);

    assertThat(phasenInfo.stand().getPunkte(Spieler.SPIELER)).isEqualTo(10);
    assertThat(phasenInfo.stand().getPunkte(Spieler.GEGNER)).isEqualTo(7 + 36 + 10);
    assertThat(phase.getNext()).isInstanceOf(Ruhe.class);
  }
}
