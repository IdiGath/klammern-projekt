package de.idigath.klammern.backend.web.dto;

import lombok.Getter;

import java.util.List;

/**
 * Das DTO repräsentiert gespielte Karten vom jeweiligen Spieler.
 */

@Getter
public class ZugbestandDto {

    String spieler;
    List<KarteDto> inhalt;

}
