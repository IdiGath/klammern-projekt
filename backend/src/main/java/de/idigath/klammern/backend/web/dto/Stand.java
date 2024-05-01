package de.idigath.klammern.backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Stand {

    Map<String, Integer> aktuell;
    Map<String, Integer> augen;
    List<Map<String, Integer>> historie;

    public Stand(String spielerName, String gegnerName) {
        aktuell = new HashMap<>();
        aktuell.put(spielerName, 0);
        aktuell.put(gegnerName, 0);
        augen = new HashMap<>();
        augen.put(spielerName, 0);
        augen.put(gegnerName, 0);
        historie = new ArrayList<>();
    }

}
