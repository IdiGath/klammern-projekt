package de.idigath.klammern.backend.web.mapper;

import static de.idigath.klammern.backend.web.mapper.KarteMapper.mapDtoToKarte;
import static de.idigath.klammern.backend.web.mapper.KarteMapper.mapKarteToDto;
import static org.assertj.core.api.Assertions.assertThat;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.web.dto.KarteDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KarteMapperTest {

  @Test
  void mapKarteToDtoTest_assPik_ok() {
    Karte assPik = new Karte(Farbe.PIK, Wert.ASS);

    KarteDto assPikDto = mapKarteToDto(assPik);

    assertThat(assPikDto.farbe()).isEqualTo(assPik.farbe().getName());
    assertThat(assPikDto.wert()).isEqualTo(assPik.wert().getName());
  }

  @Test
  void mapDtoToKarte_dameHerz_ok() {
    KarteDto dameHerzDto = new KarteDto(Farbe.HERZ.getName(), Wert.DAME.getName());

    Karte dameHerz = mapDtoToKarte(dameHerzDto);

    assertThat(dameHerz.farbe().getName()).isEqualTo(dameHerzDto.farbe());
    assertThat(dameHerz.wert().getName()).isEqualTo(dameHerzDto.wert());
  }
}
