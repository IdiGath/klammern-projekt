package de.idigath.klammern.backend.web.dto;

import de.idigath.klammern.backend.model.Karte;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

/**
 * Das DTO repräsentiert die aktuelle Runde. Das Objekt beinhaltet nur die Informationen für die Anzeige in Web,
 * restliche Informationen bleiben im Backend unveröffentlicht.
 */
@Getter
@Setter
public class Runde {

    String reihenfolge;
    Karte trumpf;
    Map<String, Set<Karte>> karten;

}
