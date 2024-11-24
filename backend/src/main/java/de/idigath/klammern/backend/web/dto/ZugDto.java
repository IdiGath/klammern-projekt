package de.idigath.klammern.backend.web.dto;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Das DTO repräsentiert einen gespielten Zug, welcher aus zwei Zugbeständen der beiden Spielern
 * besteht.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ZugDto {

  private ZugbestandDto beginner;
  private ZugbestandDto decker;

  public String getBeginner() {
    return beginner.getSpieler();
  }

  public String getDecker() {
    return decker.getSpieler();
  }

  public List<KarteDto> getBeginnerKarten() {
    return beginner.getInhalt();
  }

  public List<KarteDto> getDeckerKarten() {
    return decker.getInhalt();
  }

  public boolean isVollstaendig() {
    return Objects.nonNull(beginner) && Objects.nonNull(decker);
  }
}
