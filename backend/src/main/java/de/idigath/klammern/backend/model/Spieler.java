package de.idigath.klammern.backend.model;


import lombok.Getter;

/**
 * Die Klasse beinhaltet Enums für jeden möglichen Spieler im Klammern-Spiel.
 */
@Getter
public enum Spieler {
    SPIELER("SPIELER"),
    GEGNER("GEGNER"),
    NIEMAND("NIEMAND");

    private final String name;

    Spieler(String name) {
        this.name = name;
    }
}
