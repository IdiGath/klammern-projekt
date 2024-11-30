package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Sonderbonus;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.vergleich.KartenComparator;
import de.idigath.klammern.backend.service.vergleich.VergleichsTyp;

/**
 * Während dieser Phase werden die Karten einzeln ausgespielt bis sie bei den beiden Spielern
 * ausgehen. Nach jedem Zug bekommt der Gewinner des Zuges die Punkte seiner Karte und der von dem
 * Gegner. Nach dieser Phase kommt wieder die Ruhe-Phase zum Stande, was das Ende der gespielten
 * Runde bedeutet.
 */
public class Aktion extends AbstractPhase implements Phase {
  private final boolean nativeTrumpf;
  private boolean kartenAusgespielt = false;

  /**
   * Dieser Konstruktor wird beim Ende einer runde benutzt. In dem Parameter wird die ganze
   * Information aus der vorherigen Phase geliefert.
   *
   * @param phasenInfo Information der vorherigen Phase.
   */
  Aktion(PhasenInfo phasenInfo) {
    this.stand = phasenInfo.stand();
    this.beginner = phasenInfo.beginner();
    this.reihen = phasenInfo.reihen();
    this.spielDeck = phasenInfo.spielDeck();
    this.trumpfKarte = phasenInfo.trumpfKarte();
    this.nativeTrumpf = phasenInfo.nativeTrump();
  }

  @Override
  public void spieleZug(Zug zug) {
    validateZug(zug);

    var beginnerKarte =
        reihen
            .get(zug.getBeginner())
            .giveSpielkarte(
                zug.getBeginnerKarten().getFirst().farbe(),
                zug.getBeginnerKarten().getFirst().wert());
    var deckerKarte =
        reihen
            .get(zug.getDecker())
            .giveSpielkarte(
                zug.getDeckerKarten().getFirst().farbe(), zug.getDeckerKarten().getFirst().wert());

    Spieler gewinner = zug.getBeginner();

    if (beginnerKarte.farbe().equals(deckerKarte.farbe())) {
      VergleichsTyp vergleichsTyp =
          isTrumpf(beginnerKarte.farbe()) ? VergleichsTyp.TRUMPF : VergleichsTyp.STANDARD;
      KartenComparator comparator = KartenComparator.createKartenWertComparator(vergleichsTyp);
      int compareResult = comparator.compare(beginnerKarte.wert(), deckerKarte.wert());
      gewinner = compareResult < 0 ? zug.getDecker() : zug.getBeginner();
    } else if (isTrumpf(deckerKarte.farbe())) {
      gewinner = zug.getDecker();
    }
    int punkte =
        zaehleKartenpunkte(isTrumpf(beginnerKarte.farbe()), beginnerKarte.wert())
            + zaehleKartenpunkte(isTrumpf(deckerKarte.farbe()), deckerKarte.wert())
            + zaehleSonderpunkte(isTrumpf(beginnerKarte.farbe()), beginnerKarte.wert())
            + zaehleSonderpunkte(isTrumpf(deckerKarte.farbe()), deckerKarte.wert());
    kartenAusgespielt = reihen.get(Spieler.SPIELER).countSpielkarten() == 0;

    if (kartenAusgespielt) {
      punkte = punkte + Sonderbonus.LETZTER_STICH.punkte;
    }
    beginner = zug.getDecker();
    stand.addPunkte(gewinner, punkte);
  }

  @Override
  public Phase getNext() {
    if (kartenAusgespielt) {
      PhasenInfo phasenInfo =
          new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte, nativeTrumpf);
      return new Ruhe(phasenInfo);
    }
    return this;
  }

  private boolean isTrumpf(Farbe farbe) {
    return trumpfKarte.farbe().equals(farbe);
  }

  private int zaehleSonderpunkte(boolean isTrumpf, Wert wert) {
    if (isTrumpf && wert.equals(Wert.BUBE)) {
      return Sonderbonus.JAPPA.punkte;
    } else if (isTrumpf && wert.equals(Wert.NEUN)) {
      return Sonderbonus.MIE.punkte;
    } else {
      return 0;
    }
  }

  private int zaehleKartenpunkte(boolean isTrumpf, Wert wert) {
    return isTrumpf ? wert.getTrumpfWert() : wert.getStandardWert();
  }
}
