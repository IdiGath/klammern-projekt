package de.idigath.klammern.backend.model;


/**
 * Das Record für die Spielkarte in der Runde. Diese Klasse ist die Brücke zwischen Wertigkeit der
 * Karte außerhalb des Spiels und der Decke, was in der Runde tätig ist. Nur mit Betracht des Wertes
 * und der jeweiligen Farbe können die übergeordnete Klassen die Karte in der Runde richtig
 * interpretieren.
 */
public record Karte(Farbe farbe, Wert kartenWert) {
}
