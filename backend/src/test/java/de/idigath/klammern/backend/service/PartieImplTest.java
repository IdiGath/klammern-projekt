package de.idigath.klammern.backend.service;

import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.service.spiel.PartieImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PartieImplTest {

    @Test
    void neuBeginnen_beginnNachDemAufgeben_ok() {
        Partie partyUnderTest = new PartieImpl();

        partyUnderTest.aufgeben();
        partyUnderTest.neuBeginnen();

        assertThat(partyUnderTest.getGewinner()).isEqualTo(Spieler.NIEMAND);
    }

    @Test
    void neuBeginnen_beginnOhneAufgeben_throwsIllegalStateException() {
        Partie partyUnderTest = new PartieImpl();

        assertThatThrownBy(partyUnderTest::neuBeginnen)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Die Partie ist noch nicht beendet " +
                        "um sie neu beginnen zu können");
    }
}
