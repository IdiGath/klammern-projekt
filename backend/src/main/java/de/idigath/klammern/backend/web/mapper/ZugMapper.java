package de.idigath.klammern.backend.web.mapper;

import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.web.dto.ZugDto;

// ToDo: ZugMapper neu schreiben wenn die Partie fertig ist
public class ZugMapper {

  private ZugMapper() {
    throw new AssertionError(
        "Unterdrückung vom standard Konstruktor um die Instanziierung zu verbieten");
  }

  public static Zug mapDtoToZug(ZugDto zugDto) {
    Spieler beginner = Spieler.valueOf(zugDto.getBeginner());
    Spieler decker = Spieler.valueOf(zugDto.getDecker());
    Zug zug = new Zug();
    zug.setBeginner(beginner);
    zug.setDecker(decker);
    zugDto.getBeginnerKarten().stream()
        .map(KarteMapper::mapDtoToKarte)
        .forEach(e -> zug.addKarte(beginner, e));
    zugDto.getDeckerKarten().stream()
        .map(KarteMapper::mapDtoToKarte)
        .forEach(e -> zug.addKarte(decker, e));
    return zug;
  }
}
