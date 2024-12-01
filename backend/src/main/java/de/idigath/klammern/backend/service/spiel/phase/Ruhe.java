package de.idigath.klammern.backend.service.spiel.phase;

import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import java.util.Objects;

/**
 * Die Ruhe-Phase ist ein nicht spielbarer Stand einer Runde. Entweder ist die Runde erst gestartet
 * und soll in die neue Phase wechseln, oder die Runde hat ihr Ende erreicht und soll von der Partie
 * beendet werden.
 */
public class Ruhe extends AbstractPhase implements Phase {

  /**
   * Dieser Konstruktor wird ausschließlich beim Beginn einer neuen Runde verwendet. Die Runde
   * übergibt einen leeren Stand, sowie den ermittelten Beginner.
   *
   * @param beginner ermittelter Beginner der Runde
   * @param stand neuer Spielstand
   */
  public Ruhe(Spieler beginner, Stand stand) {
    this.stand = stand;
    this.beginner = beginner;
  }

  /**
   * Dieser Konstruktor wird beim Ende einer runde benutzt. In dem Parameter wird die ganze
   * Information aus der vorherigen Phase geliefert.
   *
   * @param phasenInfo Information der vorherigen Phase.
   */
  Ruhe(PhasenInfo phasenInfo) {
    beginner = phasenInfo.beginner();
    stand = phasenInfo.stand();
    spielDeck = phasenInfo.spielDeck();
    reihen = phasenInfo.reihen();
    trumpfKarte = phasenInfo.trumpfKarte();
  }

  @Override
  public void spieleZug(Zug zug) {
    if (Objects.nonNull(trumpfKarte)) {
      throw new IllegalStateException("Die Runde ist bereits beendet");
    }
  }

  @Override
  public Phase getNext() {
    return new Wahl(beginner, stand);
  }
}
