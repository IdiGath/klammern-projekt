package de.idigath.klammern.backend.model;

import lombok.ToString;

import java.util.EnumMap;
import java.util.Map;

@ToString
public class Stand {
    private final Map<Spieler, Integer> standMap = new EnumMap<>(Spieler.class);

    public Stand() {
        standMap.put(Spieler.SPIELER, 0);
        standMap.put(Spieler.GEGNER, 0);
    }

    public Integer getSpielerPunkte() {
        return standMap.get(Spieler.SPIELER);
    }

    public Integer getGegnerPunkte() {
        return standMap.get(Spieler.GEGNER);
    }

    public void addSpielerPunkte(Integer punkte) {
        addPunkte(Spieler.SPIELER, punkte);
    }

    public void addGegnerPunkte(Integer punkte) {
        addPunkte(Spieler.GEGNER, punkte);
    }

    private void addPunkte(Spieler spieler, Integer punkte) {
        Integer altePunkte = standMap.get(spieler);
        Integer newPunkte = punkte + altePunkte;
        standMap.put(spieler, newPunkte);
    }

}
