package de.idigath.klammern.backend.service.spiel;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.web.dto.Zug;

public class Runde {

    private Phase phase;

    Runde(Phase phase) {
        this.phase = phase;
    }

    public Farbe getTrumpf() {
        return phase.getTrumpf();
    }

    public void spieleZug(Zug zug) {
        phase.spieleZug(zug);
    }

    public Runde getInstance() {
        return new Runde(new Ansage());
    }


}
