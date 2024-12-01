package de.idigath.klammern.backend.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.web.dto.KarteDto;
import de.idigath.klammern.backend.web.dto.ZugDto;
import de.idigath.klammern.backend.web.dto.ZugbestandDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZugMapperTest {

  @Test
  void mapDtoToZug_jeweilsEineKarte_ok() {
    KarteDto dameHerzDto = new KarteDto(Farbe.HERZ.getName(), Wert.DAME.getName());
    ZugbestandDto spielerZugbestandDto =
        new ZugbestandDto(Spieler.SPIELER.getName(), List.of(dameHerzDto));
    KarteDto koenigHerzDto = new KarteDto(Farbe.HERZ.getName(), Wert.KOENIG.getName());
    ZugbestandDto gegnerZugbestandDto =
        new ZugbestandDto(Spieler.GEGNER.getName(), List.of(koenigHerzDto));
    ZugDto zugDto = new ZugDto(spielerZugbestandDto, gegnerZugbestandDto);

    Zug result = ZugMapper.mapDtoToZug(zugDto);

    assertThat(result.getDeckerKarten()).hasSameSizeAs(zugDto.getDeckerKarten());
    assertThat(result.getDeckerKarten().getFirst().wert().getName())
        .isEqualTo(zugDto.getDeckerKarten().getFirst().wert());
    assertThat(result.getDeckerKarten().getFirst().farbe().getName())
        .isEqualTo(zugDto.getDeckerKarten().getFirst().farbe());
    assertThat(result.getBeginnerKarten()).hasSameSizeAs(zugDto.getBeginnerKarten());
    assertThat(result.getBeginnerKarten().getFirst().wert().getName())
        .isEqualTo(zugDto.getBeginnerKarten().getFirst().wert());
    assertThat(result.getBeginnerKarten().getFirst().farbe().getName())
        .isEqualTo(zugDto.getBeginnerKarten().getFirst().farbe());
  }

  @Test
  void mapDtoToZug_kartenKombinationen_ok() {
    KarteDto damePikDto = new KarteDto(Farbe.PIK.getName(), Wert.DAME.getName());
    KarteDto koenigPikDto = new KarteDto(Farbe.PIK.getName(), Wert.KOENIG.getName());
    KarteDto assPikDto = new KarteDto(Farbe.PIK.getName(), Wert.ASS.getName());
    ZugbestandDto spielerZugbestandDto =
        new ZugbestandDto(Spieler.SPIELER.getName(), List.of(damePikDto, koenigPikDto, assPikDto));
    KarteDto siebenKaroDto = new KarteDto(Farbe.KARO.getName(), Wert.SIEBEN.getName());
    KarteDto achtKaroDto = new KarteDto(Farbe.KARO.getName(), Wert.KOENIG.getName());
    KarteDto neunKaroDto = new KarteDto(Farbe.KARO.getName(), Wert.NEUN.getName());
    ZugbestandDto gegnerZugbestandDto =
        new ZugbestandDto(
            Spieler.GEGNER.getName(), List.of(siebenKaroDto, achtKaroDto, neunKaroDto));
    ZugDto zugDto = new ZugDto(spielerZugbestandDto, gegnerZugbestandDto);

    Zug result = ZugMapper.mapDtoToZug(zugDto);

    assertThat(result.getDeckerKarten()).hasSameSizeAs(zugDto.getDeckerKarten());
  }
}
