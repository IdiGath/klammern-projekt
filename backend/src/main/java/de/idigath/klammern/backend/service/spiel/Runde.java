package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.model.Zug;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
public class Runde {
    private final Stand punkte;
    private Phase phase;

    /**
     * Ein Test-Konstruktor, um die Vorgabe der Phase mit den Testdaten zu ermöglichen.
     *
     * @param phase vorgegebene Phase
     */
    Runde(Phase phase, Stand punktenStand) {
        this.phase = phase;
        punkte = punktenStand;
    }


    public Runde(Spieler beginner) {
        phase = new Ansage(beginner);
        punkte = new Stand();
    }

    public Spieler getBeginner() {
        return phase.getBeginner();
    }

    public Integer getSpielerPunkte() {
        return punkte.getSpielerPunkte();
    }

    public Integer getGegnerPunkte() {
        return punkte.getGegnerPunkte();
    }

    public Karte getTrumpfKarte() {
        return phase.getTrumpfKarte();
    }

    public List<Karte> getSpielerKarten() {
        return phase.getKarten(Spieler.SPIELER);
    }

    public List<Karte> getGegnerKarten() {
        return phase.getKarten(Spieler.GEGNER);
    }

    public void spieleZug(Zug zug) {
        phase.spieleZug(zug);
        phase = phase.getNext();
    }

    public boolean isSpielbar() {
        return Objects.isNull(phase) || phase instanceof Ende;
    }
}
