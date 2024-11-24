package de.idigath.klammern.backend.web.mapper;

import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.service.Partie;
import de.idigath.klammern.backend.web.dto.PartieDto;
import de.idigath.klammern.backend.web.dto.RundeDto;
import de.idigath.klammern.backend.web.dto.StandDto;

import static de.idigath.klammern.backend.web.mapper.KarteMapper.mapKarteToDto;

public class PartieMapper {

  private PartieMapper() {
    throw new AssertionError(
        "Unterdrückung vom standard Konstruktor um die Instanziierung zu verbieten");
  }

  public static PartieDto mapPartieToDto(Partie partie) {
    final var runde = new RundeDto();
    runde.setBeginner(partie.getBeginner().getName());
    runde.setTrumpfKarte(mapKarteToDto(partie.getTrumpfKarte()));
    runde.setSpielerKarten(
        partie.getSpielerKarten().stream().map(KarteMapper::mapKarteToDto).toList());
    runde.setGegnerKarten(
        partie.getGegnerKarten().stream().map(KarteMapper::mapKarteToDto).toList());
    var stand = mapPartieToStandDto(partie);
    return new PartieDto(stand, runde, partie.isFertig(), partie.getGewinner().getName());
  }

  private static StandDto mapPartieToStandDto(Partie partie) {
    final var stand = new StandDto(Spieler.SPIELER.getName(), Spieler.GEGNER.getName());
    stand.setPunkte(Spieler.SPIELER.getName(), partie.getSpielerPunkte());
    stand.setPunkte(Spieler.GEGNER.getName(), partie.getGegnerPunkte());
    stand.setAugen(Spieler.SPIELER.getName(), partie.getSpielerAugen());
    stand.setAugen(Spieler.GEGNER.getName(), partie.getGegnerAugen());

    for (Stand element : partie.getHistorie()) {
      stand.addMapZurHistorie(element.getStandAsMap());
    }
    return stand;
  }
}
