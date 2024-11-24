package de.idigath.klammern.backend.web.dto;

/**
 * Das Record ist ein Transferobjekt für die Spielkarte.
 *
 * <p>Die Klasse verwendet String-Werte, um die Abhängigkeiten von Model-Package zu vermeiden.
 *
 * @see de.idigath.klammern.backend.model.Karte
 * @see de.idigath.klammern.backend.model.Farbe
 * @see de.idigath.klammern.backend.model.Wert
 */
public record KarteDto(String farbe, String wert) {}
