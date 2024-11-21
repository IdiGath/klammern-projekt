package de.idigath.klammern.backend.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class StandTest {

    @Test
    void addSpielerPunktePunkte() {
        Stand stand = new Stand();
        Integer erwartetesErgebnis = 13;
        Integer ersterSummand = 7;
        Integer zweiterSummand = 6;

        stand.addPunkte(Spieler.SPIELER, ersterSummand);
        stand.addPunkte(Spieler.SPIELER, zweiterSummand);

        assertThat(erwartetesErgebnis).isEqualTo(stand.getPunkte(Spieler.SPIELER));
    }

    @Test
    void addGegnerPunktePunkte() {
        Stand stand = new Stand();
        Integer erwartetesErgebnis = 99;
        Integer ersterSummand = 1;
        Integer zweiterSummand = 98;

        stand.addPunkte(Spieler.GEGNER, ersterSummand);
        stand.addPunkte(Spieler.GEGNER, zweiterSummand);

        assertThat(erwartetesErgebnis).isEqualTo(stand.getPunkte(Spieler.GEGNER));
    }

    @Test
    void copyStand() {
        Stand stand = new Stand();
        stand.addPunkte(Spieler.SPIELER, 99);
        stand.addPunkte(Spieler.GEGNER, 11);

        Stand copyStand = new Stand(stand);

        assertThat(stand).isEqualTo(copyStand);
        assertThat(stand).isNotSameAs(copyStand);

    }
}
