package de.idigath.klammern.backend.web.mapper;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.web.dto.KarteDto;

public class KarteMapper {
  private KarteMapper() {
    throw new AssertionError(
        "Unterdrückung vom standard Konstruktor um die Instanziierung zu verbieten");
  }

  public static KarteDto mapKarteToDto(Karte karte) {
    return new KarteDto(karte.farbe().getName(), karte.wert().getName());
  }

  public static Karte mapDtoToKarte(KarteDto karteDto) {
    return new Karte(Farbe.valueOf(karteDto.farbe()), Wert.valueOf(karteDto.wert()));
  }
}
