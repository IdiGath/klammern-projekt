package de.idigath.klammern.backend.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Zug {

    Zugbestand beginner;
    Zugbestand decker;

    public boolean isVollstaendig() {
        return Objects.nonNull(beginner) && Objects.nonNull(decker);
    }

}
