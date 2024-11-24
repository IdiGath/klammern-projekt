package de.idigath.klammern.backend.web;

import static de.idigath.klammern.backend.web.mapper.PartieMapper.mapPartieToDto;
import static de.idigath.klammern.backend.web.mapper.ZugMapper.mapDtoToZug;

import de.idigath.klammern.backend.service.spiel.PartieImpl;
import de.idigath.klammern.backend.web.dto.PartieDto;
import de.idigath.klammern.backend.web.dto.ZugDto;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController um das Klammern-Spiel zu steuern. Die Klasse beinhaltet u.a. Methoden um die
 * Model-Klassen zu Mappen.
 */
@RestController
@RequestMapping("api/klammern")
public class KlammernController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  private final PartieImpl partie;

  /** Konstruktor für den Insert von allen Spring Beans, welche im Kontroller benötigt werden. */
  @Autowired
  public KlammernController(PartieImpl partie) {
    this.partie = partie;
  }

  /**
   * Die Methode verarbeitet den übergebenen Zug im Spiel. Abhängig von der Phase und logischem
   * Ergebnis wird der neue Stand der Partie zurückgegeben.
   *
   * @param zugDto Spielzug
   * @return neuer Stand der Partie
   */
  @PostMapping(value = "/zug", consumes = MediaType.APPLICATION_JSON_VALUE)
  public PartieDto spieleZug(@RequestBody ZugDto zugDto) {
    var zug = mapDtoToZug(zugDto);
    partie.spieleZug(zug);
    return mapPartieToDto(partie);
  }

  /**
   * Liefert den aktuellen Stand der Partie anhand der Session zurück.
   *
   * @return aktuell gestartete Partie
   */
  @GetMapping(value = "/partie")
  public PartieDto getPartie() {
    LOG.info("Die Methode getPartie wurde aufgerufen");
    return mapPartieToDto(partie);
  }

  /**
   * Beginnt die bestehende Partie zum Neustart, wenn die notwendigen Herausforderungen erfüllt
   * sind. Die Partie bestehende Partie muss einen Gewinner haben, um eine neue Partie starten zu
   * können.
   *
   * @return neue Partie
   */
  @PostMapping(value = "/partie")
  public PartieDto partieNeuBeginnen() {
    partie.neuBeginnen();
    return mapPartieToDto(partie);
  }

  /**
   * Beendet laufende Partie. Die erfolgreiche Verarbeitung wird mit dem Status 200 zurückgegeben.
   */
  @PostMapping(value = "/stop")
  @ResponseStatus(HttpStatus.OK)
  public void partieAufgeben() {
    partie.aufgeben();
  }
}
