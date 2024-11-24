package de.idigath.klammern.backend.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Das DTO repräsentiert gespielte Karten vom jeweiligen Spieler. */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ZugbestandDto {

  String spieler;
  List<KarteDto> inhalt;
}
