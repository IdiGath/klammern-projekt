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
    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER)).isZero();
  }

  @Test
  void spieleZug_fuenfzigerUeber2Terz_fuenfzigerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.SIEBEN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.NEUN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.SIEBEN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.SIEBEN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ACHT));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ZEHN));

    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER))
        .isEqualTo(Kombination.FUENFZIGER.getPunkte());
    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER)).isZero();
  }

  @Test
  void spieleZug_fuenfzigerUeber2Fuenfziger_zweiFuenfzigerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.SIEBEN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.NEUN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.KREUZ, Wert.ZEHN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.SIEBEN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.NEUN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.ZEHN));

    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ACHT));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ZEHN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.BUBE));

    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER))
        .isEqualTo(Kombination.FUENFZIGER.getPunkte() * 2);
    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER)).isZero();
  }

  @Test
  void spieleZug_terzUeberTerz_beginnerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.ACHT));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.NEUN));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.HERZ, Wert.ZEHN));

    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ACHT));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.NEUN));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ZEHN));

    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER))
        .isEqualTo(Kombination.TERZ.getPunkte());
    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER)).isZero();
  }

  @Test
  void spieleZug_leererZug_keinUnterschied() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);

    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER)).isZero();
    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER)).isZero();
  }

  @Test
  void spieleZug_beginnerLeererZug_deckerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.DAME));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.BUBE));
    zug.addKarte(Spieler.GEGNER, new Karte(Farbe.PIK, Wert.ZEHN));
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER)).isZero();
    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER))
        .isEqualTo(Kombination.TERZ.getPunkte());
  }

  @Test
  void spieleZug_gegnerLeererZug_spielerGewinnt() {
    var zug = new Zug();
    zug.setBeginner(Spieler.SPIELER);
    zug.setDecker(Spieler.GEGNER);
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.PIK, Wert.DAME));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.PIK, Wert.BUBE));
    zug.addKarte(Spieler.SPIELER, new Karte(Farbe.PIK, Wert.ZEHN));
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    phase.spieleZug(zug);

    assertThat(phaseninfo.stand().getPunkte(Spieler.GEGNER)).isZero();
    assertThat(phaseninfo.stand().getPunkte(Spieler.SPIELER))
        .isEqualTo(Kombination.TERZ.getPunkte());
  }

  @Test
  void getNext_keineAenderung_ok() {
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Ansage(phaseninfo);

    assertThat(phase.getNext()).isEqualTo(phase);
  }
}
