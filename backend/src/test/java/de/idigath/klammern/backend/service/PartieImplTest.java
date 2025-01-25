package de.idigath.klammern.backend.service;

import de.idigath.klammern.backend.config.PostgresContainer;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.spiel.PartieImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PartieImplTest extends PostgresContainer {

    @Test
    void neuBeginnen_beginnNachDemAufgeben_ok() {
        Partie partieUnderTest = new PartieImpl();

        partieUnderTest.aufgeben();
        partieUnderTest.neuBeginnen();

        assertThat(partieUnderTest.getGewinner()).isEqualTo(Spieler.NIEMAND);
    }

    @Test
    void neuBeginnen_beginnOhneAufgeben_throwsIllegalStateException() {
        Partie partieUnderTest = new PartieImpl();

        assertThatThrownBy(partieUnderTest::neuBeginnen)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith(
                        "Die Partie ist noch nicht beendet " + "um sie neu beginnen zu können");
    }

    @Test
    void neuBeginnen_rundeDurchspielen_ok() {
        Partie partieUnderTest = new PartieImpl();

        while (!partieUnderTest.isFertig()) {
            spieleRunde(partieUnderTest);
        }

        assertThat(partieUnderTest.getGewinner()).isNotEqualTo(Spieler.NIEMAND);
    }

    private void spieleRunde(Partie partieUnderTest) {
        // Wahlphase starten
        var leererZug = new Zug();
        partieUnderTest.spieleZug(leererZug);

        // Trumpf Auswahl
        var trumpfZug = new Zug();
        trumpfZug.setBeginner(partieUnderTest.getBeginner());
        trumpfZug.setDecker(partieUnderTest.getDecker());
        trumpfZug.addKarte(partieUnderTest.getBeginner(), partieUnderTest.getTrumpfKarte());
        partieUnderTest.spieleZug(trumpfZug);

        // Ansage der Kombinationen
        var kombinationenZug = new Zug();
        kombinationenZug.setBeginner(partieUnderTest.getBeginner());
        kombinationenZug.setDecker(partieUnderTest.getDecker());
        partieUnderTest.spieleZug(kombinationenZug);

        // Karten spielen
        for (int i = 0; i < 8; i++) {
            var spielerKarten = partieUnderTest.getSpielerKarten();
            var gegnerKarten = partieUnderTest.getGegnerKarten();
            var zug = new Zug();
            zug.setBeginner(partieUnderTest.getBeginner());
            zug.setDecker(partieUnderTest.getDecker());
            zug.addKarte(Spieler.SPIELER, spielerKarten.getFirst());
            zug.addKarte(Spieler.GEGNER, gegnerKarten.getFirst());
            partieUnderTest.spieleZug(zug);
        }
    }
}
