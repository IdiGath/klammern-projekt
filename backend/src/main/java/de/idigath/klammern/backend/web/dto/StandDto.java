package de.idigath.klammern.backend.web.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Das DTO repräsentiert den aktuellen Stand in der Partie. Das Objekt beinhaltet sowohl den aktuellen Stand der
 * Runde, als auch der Stand der Partie ink. Historie.
 * <p>
 * Die Klasse setzt auf String-Werte, um die Abhängigkeiten von Model-Package zu vermeiden.
 */
public class StandDto {

    Map<String, Integer> punkteMap = new HashMap<>();
    Map<String, Integer> augenMap = new HashMap<>();
    List<Map<String, Integer>> historie = new LinkedList<>();
    String spieler;
    String gegner;

    /**
     * Erzeugt ein Objekt anhand der Stringname von beiden spielern.
     *
     * @param spieler Spieler
     * @param gegner  Gegner
     */
    public StandDto(String spieler, String gegner) {
        this.spieler = spieler;
        this.gegner = gegner;
    }

    /**
     * Setzt einen Eintrag in die Punkte-Map.
     *
     * @param spieler Spieler
     * @param punkte  Punkte
     */
    public void setPunkte(String spieler, int punkte) {
        validateMap(punkteMap, spieler);
        validateSpieler(spieler);
        punkteMap.put(spieler, punkte);
    }

    /**
     * Setzt einen Eintrag in die Augen-Map.
     *
     * @param spieler Spieler
     * @param augen   Punkte
     */
    public void setAugen(String spieler, int augen) {
        validateMap(augenMap, spieler);
        validateSpieler(spieler);
        augenMap.put(spieler, augen);
    }

    private void validateMap(Map<String, Integer> map, String spieler) {
        if (map.containsKey(spieler)) {
            throw new IllegalStateException("Der Spieler hat bereits angelegte Punkte");
        }
    }

    private void validateSpieler(String spieler) {
        if (this.spieler.equals(spieler) || gegner.equals(spieler)) {
            throw new IllegalStateException("Der Spieler ist in dem Stand-Objekt nicht berücksichtigt");
        }
    }

    /**
     * Fügt eine Map der Historie hinzu.
     * Der Map-Eintag soll gleiche Keys wie das StandDto-Objekt beinhalten.
     *
     * @param map Historie-Eintrag
     */
    public void addMapZurHistorie(Map<String, Integer> map) {
        validateHistorieEintrag(map);
        historie.add(map);
    }

    private void validateHistorieEintrag(Map<String, Integer> map) {
        if (map.size() != 2) {
            throw new IllegalStateException("Die Map beinhaltet unpassende Anzahl der Schlüsseln");
        }

        if (!map.containsKey(spieler) || !map.containsKey(gegner)) {
            throw new IllegalStateException("Die Map beinhaltet unpassende Schlüssel");
        }
    }

}
