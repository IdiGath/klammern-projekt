package de.idigath.klammern.backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Das DTO repräsentiert den aktuellen Stand in der Partie. Das Objekt beinhaltet sowohl den aktuellen Stand der
 * Runde, als auch der Stand der Partie inkl. Historie.
 */
@Getter
@Setter
public class Stand {

    Map<String, Integer> punkte;
    Map<String, Integer> augen;
    List<Map<String, Integer>> historie;

    public Stand(String spielerName, String gegnerName) {
        punkte = new HashMap<>();
        punkte.put(spielerName, 0);
        punkte.put(gegnerName, 0);
        augen = new HashMap<>();
        augen.put(spielerName, 0);
        augen.put(gegnerName, 0);
        historie = new ArrayList<>();
    }

}
