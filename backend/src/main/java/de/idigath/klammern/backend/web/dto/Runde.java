package de.idigath.klammern.backend.web.dto;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;

import java.util.Map;
import java.util.Set;

public class Runde {

    String reihenfolge;
    Farbe trumpf;
    Map<String, Set<Karte>> karten;
    Karte trumpfKarte;

}
