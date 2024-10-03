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

        stand.addSpielerPunkte(ersterSummand);
        stand.addSpielerPunkte(zweiterSummand);

        assertThat(erwartetesErgebnis).isEqualTo(stand.getSpielerPunkte());
    }

    @Test
    void addGegnerPunktePunkte() {
        Stand stand = new Stand();
        Integer erwartetesErgebnis = 99;
        Integer ersterSummand = 1;
        Integer zweiterSummand = 98;

        stand.addGegnerPunkte(ersterSummand);
        stand.addGegnerPunkte(zweiterSummand);

        assertThat(erwartetesErgebnis).isEqualTo(stand.getGegnerPunkte());
    }

    @Test
    void copyStand() {
        Stand stand = new Stand();
        stand.addSpielerPunkte(99);
        stand.addGegnerPunkte(11);

        Stand copyStand = new Stand(stand);

        assertThat(stand).isEqualTo(copyStand);
        assertThat(stand).isNotSameAs(copyStand);

    }
}
