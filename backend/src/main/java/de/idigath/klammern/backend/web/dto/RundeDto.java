package de.idigath.klammern.backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Das DTO repräsentiert die aktuelle Runde. Das Objekt beinhaltet nur die Informationen für die Anzeige in Web,
 * restliche Informationen bleiben im Backend unveröffentlicht.
 */
@Getter
@Setter
public class RundeDto {

    private String trumpf;
    private KarteDto trumpfKarte;
    private List<KarteDto> spielerKarten;
    private List<KarteDto> gegnerKarten;
    private String beginner;

}
