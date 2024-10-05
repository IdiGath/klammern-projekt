package de.idigath.klammern.backend.model;

import lombok.Getter;

/**
 * In Klammern, wie in den allen Kartenspielen des französischen Blattes, existieren vier
 * Kartenfarben: Herz, Karo, Pik und Kreuz
 */
@Getter
public enum Farbe {
    UNDEFINED("UNDEFINED"),
    HERZ("HERZ"),
    KARO("KARO"),
    PIK("PIK"),
    KREUZ("KREUZ");

    private final String name;

    Farbe(String name) {
        this.name = name;
    }


}
