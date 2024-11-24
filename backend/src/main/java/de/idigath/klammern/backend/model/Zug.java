package de.idigath.klammern.backend.model;

import java.util.*;
import lombok.Getter;
import lombok.Setter;

/** Ein Zug, mit dem Inhalt der Karten des Beginners und des Deckers. */
public class Zug {

  private final Map<Spieler, List<Karte>> inhalt = new EnumMap<>(Spieler.class);

  @Getter @Setter private Spieler beginner;
  @Getter @Setter private Spieler decker;

  /** Erzeugt ein Objekt und befüllt dabei den Inhalt des Zuges. */
  public Zug() {
    inhalt.put(Spieler.SPIELER, new ArrayList<>());
    inhalt.put(Spieler.GEGNER, new ArrayList<>());
  }

  /**
   * Fügt eine Karte jeweiligem Spieler hinzu.
   *
   * @param spieler betroffener Spieler
   * @param karte neue Karte
   */
  public void addKarte(Spieler spieler, Karte karte) {
    inhalt.get(spieler).add(karte);
  }

  public List<Karte> getBeginnerKarten() {
    return inhalt.get(beginner);
  }

  public List<Karte> getDeckerKarten() {
    return inhalt.get(decker);
  }

  /**
   * Gibt die Information zurück, ob der Zug vollständig ist. In dem geprüft wird, ob die
   * Zug-Inhalte nicht null sind.
   *
   * @return true, wenn keine nulls im Zug dabei sind
   */
  public boolean isVollstaendig() {
    return Objects.nonNull(beginner)
        && Objects.nonNull(getBeginnerKarten())
        && Objects.nonNull(beginner)
        && Objects.nonNull(getDeckerKarten());
  }
}
