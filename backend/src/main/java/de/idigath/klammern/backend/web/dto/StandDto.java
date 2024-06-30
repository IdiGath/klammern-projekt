package de.idigath.klammern.backend.web.dto;

import de.idigath.klammern.backend.model.Spieler;
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
public class StandDto {

    Map<Spieler, Integer> punkte;
    Map<Spieler, Integer> augen;
    List<Map<Spieler, Integer>> historie = new ArrayList<>();

    public StandDto(Spieler spieler, Spieler gegner) {
        punkte = new HashMap<>();
        punkte.put(spieler, 0);
        punkte.put(gegner, 0);
        augen = new HashMap<>();
        augen.put(spieler, 0);
        augen.put(gegner, 0);
    }

}
