package de.idigath.klammern.backend.web.dto;

/**
 * Das Record ist ein Transferobjekt für die Spielkarte.
 *
 * @see de.idigath.klammern.backend.model.Karte
 */
public record KarteDto(String farbe, String wert) {
}
