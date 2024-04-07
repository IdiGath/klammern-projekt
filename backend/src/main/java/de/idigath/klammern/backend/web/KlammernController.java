package de.idigath.klammern.backend.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * RestController um das Klammern-Spiel zu steuern.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("api/klammern/**")
public class KlammernController {

    /**
     * Aufruf der Methode startet eine neue Partie, falls für die Session bereits eine Partie gestartet ist, wird der
     * Stand aktueller Partie zurückgegeben.
     *
     * @return aktuelle Partie
     */
    @PostMapping(value = "/start")
    public Object partieStarten() {
        //ToDo: Implementieren
        return null;
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
     * Die Methode verarbeitet den übergebenen Zug im Spiel. Abhängig von der Phase und logischem Ergebnis, wird der
     * neue Stand der Partie zurückgegeben.
     *
     * @param zug Spielzug
     * @return neuer Stand der Partie
     */
    @PostMapping(value = "/spielen", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object zugSpielen(@RequestBody Object zug) {
        //ToDo: Implementieren
        return null;
    }
}
