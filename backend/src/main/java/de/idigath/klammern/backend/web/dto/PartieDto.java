package de.idigath.klammern.backend.web.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Das DTO repräsentiert eine Partie in dem sie den Stand und die aktuelle Runde beinhaltet.
 */
@Getter
@Setter
public class PartieDto {

    StandDto stand;
    RundeDto runde;

}
