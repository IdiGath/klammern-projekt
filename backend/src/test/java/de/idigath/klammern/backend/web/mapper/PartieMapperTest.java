package de.idigath.klammern.backend.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Stand;
import de.idigath.klammern.backend.service.Partie;
import de.idigath.klammern.backend.service.spiel.PartieImpl;
import de.idigath.klammern.backend.web.dto.PartieDto;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PartieMapperTest {

  @Test
  void mapPartieTodDto_neuePartie_ok() {
    Partie mockPartie = Mockito.spy(PartieImpl.class);
    List<Stand> dummyHistorie = new LinkedList<>();
    dummyHistorie.add(new Stand());
    dummyHistorie.add(new Stand());
    when(mockPartie.getHistorie()).thenReturn(dummyHistorie);

    PartieDto result = PartieMapper.mapPartieToDto(mockPartie);

    assertThat(result.beendet()).isFalse();
    assertThat(result.gewinner()).isEqualTo(Spieler.NIEMAND.getName());
    assertThat(result.stand()).isNotNull();
  }
}
