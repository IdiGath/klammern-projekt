package de.idigath.klammern.backend.web;

import de.idigath.klammern.backend.model.*;
import de.idigath.klammern.backend.service.spiel.Partie;
import de.idigath.klammern.backend.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

/**
 * RestController um das Klammern-Spiel zu steuern. Die Klasse beinhaltet u.a. Methoden um die Model-Klassen zu Mappen.
 */

@RestController
@RequestMapping("api/klammern")
public class KlammernController {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final Partie partie;

    @Autowired
    public KlammernController(Partie partie) {
        this.partie = partie;
    }

    /**
     * Die Methode verarbeitet den übergebenen Zug im Spiel. Abhängig von der Phase und logischem Ergebnis wird der
     * neue Stand der Partie zurückgegeben.
     *
     * @param zugDto Spielzug
     * @return neuer Stand der Partie
     */
    @PostMapping(value = "/zug", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PartieDto spieleZug(@RequestBody ZugDto zugDto) {
        var zug = mapDtoToZug(zugDto);
        partie.spieleZug(zug);
        return mapAktuellePartie();
    }


    private Zug mapDtoToZug(ZugDto zugDto) {
        Spieler beginner = Spieler.valueOf(zugDto.getBeginner());
        Spieler decker = Spieler.valueOf(zugDto.getDecker());
        Zug zug = new Zug();
        zug.setBeginner(beginner);
        zug.setDecker(decker);
        zugDto.getBeginnerKarten().stream()
                .map(this::mapDtoToKarte)
                .forEach(e -> zug.addKarte(beginner, e));
        zugDto.getDeckerKarten().stream()
                .map(this::mapDtoToKarte)
                .forEach(e -> zug.addKarte(decker, e));
        return zug;
    }

    private Karte mapDtoToKarte(KarteDto karteDto) {
        return new Karte(Farbe.valueOf(karteDto.farbe()), Wert.valueOf(karteDto.wert()));
    }

    /**
     * Liefert den aktuellen Stand der Partie anhand der Session zurück.
     *
     * @return aktuell gestartete Partie
     */
    @GetMapping(value = "/partie")
    public PartieDto getPartie() {
        LOG.info("Die Methode getPartie wurde aufgerufen");
        return mapAktuellePartie();
    }

    private PartieDto mapAktuellePartie() {
        final var runde = new RundeDto();
        runde.setBeginner(partie.getBeginner().getName());
        runde.setTrumpfKarte(mapKarteToDto(partie.getTrumpfKarte()));
        runde.setSpielerKarten(partie.getSpielerKarten().stream().map(this::mapKarteToDto).toList());
        runde.setGegnerKarten(partie.getGegnerKarten().stream().map(this::mapKarteToDto).toList());
        var stand = mapStandToDto();
        return new PartieDto(stand, runde, partie.isBeendet());
    }

    private KarteDto mapKarteToDto(Karte karte) {
        return new KarteDto(karte.farbe().getName(), karte.wert().getName());
    }

    private StandDto mapStandToDto() {
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

    /**
     * Beendet laufende Partie. Die erfolgreiche Verarbeitung wird mit dem Status 200 zurückgegeben.
     */
    @PostMapping(value = "/stop")
    @ResponseStatus(HttpStatus.OK)
    public void partieAufgeben() {
        partie.aufgeben();
    }
}
