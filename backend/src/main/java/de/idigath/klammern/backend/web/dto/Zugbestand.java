package de.idigath.klammern.backend.web.dto;

import de.idigath.klammern.backend.model.Karte;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Zugbestand {

    String spieler;
    List<Karte> inhalt;

}
