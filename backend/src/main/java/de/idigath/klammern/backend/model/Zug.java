package de.idigath.klammern.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Zug {

    private final Map<Spieler, List<Karte>> inhalt = new EnumMap<>(Spieler.class);

    @Getter
    @Setter
    private Spieler beginner;
    @Getter
    @Setter
    private Spieler decker;


    public Zug() {
        inhalt.put(Spieler.SPIELER, new ArrayList<>());
        inhalt.put(Spieler.GEGNER, new ArrayList<>());
    }

    public void addKarte(Spieler spieler, Karte karte) {
        getKarten(spieler).add(karte);
    }

    private List<Karte> getKarten(Spieler spieler) {
        return inhalt.get(spieler);
    }

}
