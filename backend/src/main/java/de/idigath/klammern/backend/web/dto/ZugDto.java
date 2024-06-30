package de.idigath.klammern.backend.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Das DTO repräsentiert einen gespielten Zug, welcher aus zwei Zugbeständen der beiden Spielern besteht.
 */

@Getter
@Setter
public class ZugDto {

    ZugbestandDto beginner;
    ZugbestandDto decker;

    public boolean isVollstaendig() {
        return Objects.nonNull(beginner) && Objects.nonNull(decker);
    }

}
