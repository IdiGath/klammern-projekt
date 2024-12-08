package de.idigath.klammern.backend.service.spiel.phase;

import static de.idigath.klammern.backend.model.DeckTyp.SPIELDECK;
import static org.assertj.core.api.Assertions.assertThat;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.DeckTyp;
import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Kombination;
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
class AnsageTest {

  private PhasenInfo createDummyPhasenInfo() {
    Stand stand = new Stand();
    Deck deck = DeckFactory.createDeck(SPIELDECK);
    Map<Spieler, Deck> reihen = new EnumMap<>(Spieler.class);

    reihen.put(Spieler.SPIELER, DeckFactory.createDeck(DeckTyp.REIHE));
    reihen.put(Spieler.GEGNER, DeckFactory.createDeck(DeckTyp.REIHE));
    var trumpfKarte = new Karte(Farbe.KREUZ, Wert.ASS);

    return new PhasenInfo(Spieler.SPIELER, stand, deck, reihen, trumpfKarte, true);
  }

  @Test
  void spieleZug_fuenfzigerUeberTerz_fuenfzigerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.SIEBEN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.SIEBEN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ACHT));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ZEHN));

    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER))
        .isEqualTo(Kombination.FUENFZIGER.getPunkte());
  }

  @Test
  void getNext_keineAenderung_ok() {
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    assertThat(phase.getNext()).isEqualTo(phase);
  }
}
