package de.idigath.klammern.backend.web;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.web.dto.PartieDto;
import de.idigath.klammern.backend.web.dto.RundeDto;
import de.idigath.klammern.backend.web.dto.StandDto;
import de.idigath.klammern.backend.web.dto.ZugDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * RestController um das Klammern-Spiel zu steuern.
 */

@RestController
@RequestMapping("api/klammern")
public class KlammernController {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    /**
     * Aufruf der Methode startet eine neue Partie, falls für die Session bereits eine Partie gestartet ist, wird
     * aktuelle Partie zurückgegeben.
     *
     * @return aktuelle Partie
     */
    @PostMapping(value = "/start")
    public PartieDto partieStarten() {
        //ToDo: Implementieren
        return getDummyPartie();
    }

    /**
     * Liefert den aktuellen Stand der Partie anhand der Session.
     *
     * @return aktuell gestartete Partie
     */
    @GetMapping(value = "/partie")
    public PartieDto getPartie() {
        LOG.info("Die Methode getPartie wurde aufgerufen");
        return getDummyPartie();
    }

    /**
     * Beendet laufende Partie. Die erfolgreiche Verarbeitung wird mit dem Status 200 zurückgegeben.
     */
    @PostMapping(value = "/stop")
    @ResponseStatus(HttpStatus.OK)
    public void partieAufgeben() {
        //ToDo: Implementieren
    }

    /**
     * Die Methode verarbeitet den übergebenen Zug im Spiel. Abhängig von der Phase und logischem Ergebnis wird der
     * neue Stand der Partie zurückgegeben.
     *
     * @param zugDto Spielzug
     * @return neuer Stand der Partie
     */
    @PostMapping(value = "/zug", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PartieDto zugSpielen(@RequestBody ZugDto zugDto) {
        //ToDo: Implementieren
        return getDummyPartie();
    }

    private PartieDto getDummyPartie() {
        final var partie = new PartieDto();
        final var runde = new RundeDto();
        Map<Spieler, Set<Karte>> karten = new HashMap<>();
        karten.put(Spieler.SPIELER, getDummyKartenSpieler());
        karten.put(Spieler.GEGNER, getDummyKartenGegner());
        runde.setKarten(karten);
        runde.setTrumpf(new Karte(Farbe.KREUZ, Wert.KOENIG));
        runde.setBeginner(Spieler.GEGNER);
        partie.setRunde(runde);
        final var stand = new StandDto(Spieler.SPIELER, Spieler.GEGNER);
        partie.setStand(stand);

        return partie;
    }


    private Set<Karte> getDummyKartenSpieler() {
        Set<Karte> kartenSpieler = new HashSet<>();
        kartenSpieler.add(new Karte(Farbe.HERZ, Wert.SIEBEN));
        kartenSpieler.add(new Karte(Farbe.KARO, Wert.SIEBEN));
        kartenSpieler.add(new Karte(Farbe.PIK, Wert.SIEBEN));
        kartenSpieler.add(new Karte(Farbe.KREUZ, Wert.SIEBEN));
        kartenSpieler.add(new Karte(Farbe.KREUZ, Wert.ACHT));
        kartenSpieler.add(new Karte(Farbe.KREUZ, Wert.NEUN));
        kartenSpieler.add(new Karte(Farbe.KREUZ, Wert.ZEHN));
        kartenSpieler.add(new Karte(Farbe.KREUZ, Wert.BUBE));
        return kartenSpieler;
    }

    private Set<Karte> getDummyKartenGegner() {
        Set<Karte> katenGegner = new HashSet<>();
        katenGegner.add(new Karte(Farbe.HERZ, Wert.ASS));
        katenGegner.add(new Karte(Farbe.KARO, Wert.ASS));
        katenGegner.add(new Karte(Farbe.PIK, Wert.ASS));
        katenGegner.add(new Karte(Farbe.KREUZ, Wert.ASS));
        katenGegner.add(new Karte(Farbe.HERZ, Wert.ZEHN));
        katenGegner.add(new Karte(Farbe.HERZ, Wert.BUBE));
        katenGegner.add(new Karte(Farbe.HERZ, Wert.DAME));
        katenGegner.add(new Karte(Farbe.HERZ, Wert.KOENIG));
        return katenGegner;
    }
}
