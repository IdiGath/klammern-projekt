package de.idigath.klammern.backend.service.spiel.phase;

import static de.idigath.klammern.backend.model.DeckTyp.SPIELDECK;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.DeckTyp;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WahlTest {

  private PhasenInfo createDummyPhasenInfo() {
    Stand stand = new Stand();
    Deck deck = DeckFactory.createDeck(SPIELDECK);
    Map<Spieler, Deck> reihen = new EnumMap<>(Spieler.class);
    reihen.put(Spieler.SPIELER, DeckFactory.createDeck(DeckTyp.REIHE));
    reihen.put(Spieler.GEGNER, DeckFactory.createDeck(DeckTyp.REIHE));

    return new PhasenInfo(Spieler.SPIELER, stand, deck, reihen, null, true);
  }

  @Test
  void spieleZug_nullZug_throwsIllegalArgumentException() {
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Wahl(phaseninfo.beginner(), phaseninfo.stand(), phaseninfo.reihen());

    assertThatThrownBy(() -> phase.spieleZug(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Beginner-Zug muss gefüllt sein");
  }

  @Test
  void spieleZug_falscherBeginner_throwsIllegalArgumentException() {
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Wahl(phaseninfo.beginner(), phaseninfo.stand(), phaseninfo.reihen());

    var zug = new Zug();
    zug.setBeginner(Spieler.GEGNER);
    zug.setDecker(Spieler.SPIELER);

    assertThatThrownBy(() -> phase.spieleZug(zug))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Beginner ist der andere Spieler");
  }

  @Test
  void spieleZug_vierAbsagen_ok() {
    var phaseninfo = createDummyPhasenInfo();
    var phase = new Wahl(phaseninfo.beginner(), phaseninfo.stand(), phaseninfo.reihen());

    var spielerzug = new Zug();
    spielerzug.setBeginner(Spieler.SPIELER);
    spielerzug.setDecker(Spieler.GEGNER);
    phase.spieleZug(spielerzug);

    var deckerzug = new Zug();
    deckerzug.setBeginner(Spieler.GEGNER);
    deckerzug.setDecker(Spieler.SPIELER);
    phase.spieleZug(deckerzug);

    phase.spieleZug(spielerzug);
    phase.spieleZug(deckerzug);

    assertThatNoException().isThrownBy(() -> phase.spieleZug(spielerzug));
  }
}
