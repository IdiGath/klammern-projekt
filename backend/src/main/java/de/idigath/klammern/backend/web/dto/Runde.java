package de.idigath.klammern.backend.web.dto;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Runde {

    String reihenfolge;
    Farbe trumpf;
    Map<String, Set<Karte>> karten;
    Karte trumpfKarte;

}
