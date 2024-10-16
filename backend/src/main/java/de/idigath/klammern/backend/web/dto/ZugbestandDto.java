package de.idigath.klammern.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Das DTO repräsentiert gespielte Karten vom jeweiligen Spieler.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ZugbestandDto {


    String spieler;
    List<KarteDto> inhalt;

}
