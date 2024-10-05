package de.idigath.klammern.backend.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse besteht aus einer Map, welche den Stand von jeweiligem Spieler führt. Die Klasse kommt zum Einsatz
 * sowohl in der Runde, als auch in der gesamten Parte statt.
 */
@ToString
@EqualsAndHashCode
public class Stand {
    private final Map<Spieler, Integer> standMap = new EnumMap<>(Spieler.class);

    /**
     * Erstellt ein neues Objekt mit der Anlage von beiden spielern mit jeweils 0 Punkten.
     */
    public Stand() {
        standMap.put(Spieler.SPIELER, 0);
        standMap.put(Spieler.GEGNER, 0);
    }

    /**
     * Ein Copy-Konstruktor, womit eine neue Instanz mit dem gleichen Inhalt erzeugt wird.
     *
     * @param stand Stand zu kopieren
     */
    public Stand(Stand stand) {
        standMap.put(Spieler.SPIELER, stand.getSpielerPunkte());
        standMap.put(Spieler.GEGNER, stand.getGegnerPunkte());
    }

    /**
     * Liefert die Punkte vom Spieler zurück.
     *
     * @return Punkte des Spielers
     */
    public Integer getSpielerPunkte() {
        return standMap.get(Spieler.SPIELER);
    }

    /**
     * Liefert die Punkte vom Gegner zurück.
     *
     * @return Punkte des Gegners
     */
    public Integer getGegnerPunkte() {
        return standMap.get(Spieler.GEGNER);
    }

    /**
     * Fügt dem Spieler neue Punkte hinzu.
     *
     * @param punkte neue Punkte
     */
    public void addSpielerPunkte(Integer punkte) {
        addPunkte(Spieler.SPIELER, punkte);
    }

    /**
     * Fügt dem Gegner neue Punkte hinzu.
     *
     * @param punkte neue Punkte
     */
    public void addGegnerPunkte(Integer punkte) {
        addPunkte(Spieler.GEGNER, punkte);
    }

    private void addPunkte(Spieler spieler, Integer punkte) {
        Integer altePunkte = standMap.get(spieler);
        Integer newPunkte = punkte + altePunkte;
        standMap.put(spieler, newPunkte);
    }

    /**
     * Liefert den aktuellen Stand als Map ohne weiteren Dependencies zurück.
     *
     * @return Stand
     */
    public Map<String, Integer> getStandAsMap() {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<Spieler, Integer> entry : standMap.entrySet()) {
            result.put(entry.getKey().getName(), entry.getValue());
        }
        return result;
    }

}
