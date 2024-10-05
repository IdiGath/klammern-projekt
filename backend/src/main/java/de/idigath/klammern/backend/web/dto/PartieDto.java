package de.idigath.klammern.backend.web.dto;

/**
 * Das DTO repräsentiert eine Partie in dem sie den Stand und die aktuelle Runde beinhaltet.
 */
public record PartieDto(StandDto stand, RundeDto runde) {
}
