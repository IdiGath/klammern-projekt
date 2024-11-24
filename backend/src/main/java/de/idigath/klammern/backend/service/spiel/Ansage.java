package de.idigath.klammern.backend.service.spiel;

import static de.idigath.klammern.backend.model.DeckTyp.REIHE;

import de.idigath.klammern.backend.model.Deck;
import de.idigath.klammern.backend.model.DeckFactory;
import de.idigath.klammern.backend.model.Farbe;
import de.idigath.klammern.backend.model.Karte;
import de.idigath.klammern.backend.model.Kombination;
import de.idigath.klammern.backend.model.PhasenInfo;
import de.idigath.klammern.backend.model.Spieler;
import de.idigath.klammern.backend.model.Wert;
import de.idigath.klammern.backend.model.Zug;
import de.idigath.klammern.backend.service.vergleich.KartenComparator;
import de.idigath.klammern.backend.service.vergleich.VergleichsTyp;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Ansage extends AbstractPhase implements Phase {
  private final boolean nativeTrumpf;
  private boolean ansageFertig;

  public Ansage(PhasenInfo phasenInfo) {
    this.stand = phasenInfo.stand();
    this.beginner = phasenInfo.beginner();
    this.nativeTrumpf = phasenInfo.nativeTrump();
    this.reihen = phasenInfo.reihen();
    this.spielDeck = phasenInfo.spielDeck();
    this.trumpfKarte = phasenInfo.trumpfKarte();
  }

  @Override
  public void spieleZug(Zug zug) {
    validateZug(zug);
    if (isTrumpfkarteWechsel(zug)) {
      trumpfWechseln(zug);
    } else if (isBelle(zug)) {
      stand.addPunkte(zug.getBeginner(), Kombination.BELLE.getPunkte());
    } else {
      var beginnerKombi = ermittleKombinationen(zug.getBeginnerKarten());
      var deckerKombi = ermittleKombinationen(zug.getDeckerKarten());
      verarbeiteAnsage(zug.getBeginner(), beginnerKombi, deckerKombi);
      ansageFertig = true;
    }
  }

  @Override
  public Phase getNext() {
    if (ansageFertig) {
      PhasenInfo phasenInfo =
          new PhasenInfo(beginner, stand, spielDeck, reihen, trumpfKarte, nativeTrumpf);
      return new Aktion(phasenInfo);
    }
    return this;
  }

  private void trumpfWechseln(Zug zug) {
    var reihe = reihen.get(zug.getBeginner());
    reihe.addSpielkarte(trumpfKarte);
    trumpfKarte = zug.getBeginnerKarten().getFirst();
  }

  private boolean isBelle(Zug zug) {
    var trumpfDame = new Karte(trumpfKarte.farbe(), Wert.DAME);
    var trumpfKoenig = new Karte(trumpfKarte.farbe(), Wert.KOENIG);
    return zug.getBeginnerKarten().size() == 2
        && zug.getDeckerKarten().isEmpty()
        && zug.getBeginnerKarten().getFirst().equals(trumpfDame)
        && zug.getBeginnerKarten().getFirst().equals(trumpfKoenig);
  }

  private Map<Karte, Kombination> ermittleKombinationen(List<Karte> karten) {
    Map<Farbe, Deck> kombinationen = new EnumMap<>(Farbe.class);

    for (Karte karte : karten) {
      if (kombinationen.containsKey(karte.farbe())) {
        kombinationen.get(karte.farbe()).addSpielkarte(karte);
      } else {
        Deck reihe = DeckFactory.createDeck(REIHE);
        reihe.addSpielkarte(karte);
        kombinationen.put(karte.farbe(), reihe);
      }
    }
    return definiereKombinationen(kombinationen);
  }

  private Map<Karte, Kombination> definiereKombinationen(Map<Farbe, Deck> kombinationen) {
    Map<Karte, Kombination> result = new HashMap<>();

    List<Deck> kartenDecks = kombinationen.keySet().stream().map(kombinationen::get).toList();

    for (Deck deck : kartenDecks) {
      validateEinzelkarte(deck);
      validateZuVieleKarten(deck);
      if (isKomplexeKombination(deck)) {
        addKomplexenEintrag(result, deck);
      } else {
        setzeEintrag(result, deck);
      }
    }
    return result;
  }

  private void addKomplexenEintrag(Map<Karte, Kombination> result, Deck deck) {
    Map<Farbe, List<Wert>> sortierteKarten = sortiereKartenAsMap(deck.getSpielkartenList());
    for (var entry : sortierteKarten.entrySet()) {
      List<List<Wert>> zerlegteKombinationen = zerlegeKombinationen(entry);
      for (List<Wert> einzelnKombination : zerlegteKombinationen) {
        Deck reihe = mapToDeck(entry.getKey(), einzelnKombination);
        setzeEintrag(result, reihe);
      }
    }
  }

  private List<List<Wert>> zerlegeKombinationen(Map.Entry<Farbe, List<Wert>> entry) {
    List<List<Wert>> zerlegteKombinationen = new LinkedList<>();
    List<Wert> kartenWertList = entry.getValue();
    Integer untergeordneteReihenfolge = null;
    for (Wert kartenwert : kartenWertList) {
      if (Objects.isNull(zerlegteKombinationen.getLast())) {
        zerlegteKombinationen.add(new LinkedList<>());
      }

      if (Objects.isNull(untergeordneteReihenfolge)) {
        untergeordneteReihenfolge = kartenWertList.getFirst().getReihenfolge() - 1;
      }
      int aktuelleReihenfolge = kartenwert.getReihenfolge();

      if (aktuelleReihenfolge - untergeordneteReihenfolge != 1) {
        zerlegteKombinationen.add(new LinkedList<>());
      }
      zerlegteKombinationen.getLast().add(kartenwert);
      untergeordneteReihenfolge = aktuelleReihenfolge;
    }
    return zerlegteKombinationen;
  }

  private Map<Farbe, List<Wert>> sortiereKartenAsMap(List<Karte> kartenList) {
    Map<Farbe, List<Wert>> farbenWertMap = new EnumMap<>(Farbe.class);
    farbenWertMap.put(Farbe.HERZ, new LinkedList<>());
    farbenWertMap.put(Farbe.KARO, new LinkedList<>());
    farbenWertMap.put(Farbe.KREUZ, new LinkedList<>());
    farbenWertMap.put(Farbe.PIK, new LinkedList<>());
    kartenList.forEach(karte -> farbenWertMap.get(karte.farbe()).add(karte.wert()));

    var comparator = KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE);
    farbenWertMap.forEach((farbe, wertList) -> wertList.sort(comparator));

    return farbenWertMap;
  }

  private Deck mapToDeck(Farbe farbe, List<Wert> einzelnKombination) {
    Deck reihe = DeckFactory.createDeck(REIHE);
    for (var wert : einzelnKombination) {
      reihe.addSpielkarte(new Karte(farbe, wert));
    }
    return reihe;
  }

  private void setzeEintrag(Map<Karte, Kombination> result, Deck deck) {
    var kartenList = deck.getSpielkartenList();
    List<Wert> kartenWertList =
        kartenList.stream().map(Karte::wert).toList().stream()
            .sorted(KartenComparator.createKartenWertComparator(VergleichsTyp.REIHENFOLGE))
            .toList();
    Farbe farbe = kartenList.getFirst().farbe();
    Karte kombinationsHoehe = new Karte(farbe, kartenWertList.getLast());

    if (isFuenfziger(kartenWertList)) {
      result.put(kombinationsHoehe, Kombination.FUENFZIGER);
    } else if (isTerz(kartenWertList)) {
      result.put(kombinationsHoehe, Kombination.TERZ);
    } else {
      throw new IllegalArgumentException(
          "Übergebene Karten stellen keine gültige Kombination dar!");
    }
  }

  private boolean isTerz(List<Wert> kartenWertList) {
    return kartenWertList.size() == 3 && isKartenReihenfolge(kartenWertList);
  }

  private boolean isKartenReihenfolge(List<Wert> kartenWertList) {
    boolean result = false;
    int untergeordneteReihenfolge = kartenWertList.getFirst().getReihenfolge() - 1;
    for (Wert kartenwert : kartenWertList) {
      result = true;
      int aktuelleReihenfolge = kartenwert.getReihenfolge();
      if (aktuelleReihenfolge - untergeordneteReihenfolge != 1) {
        result = false;
        break;
      }
    }
    return result;
  }

  private boolean isFuenfziger(List<Wert> kartenWertList) {
    return kartenWertList.size() == 4 && isKartenReihenfolge(kartenWertList);
  }

  private void validateZuVieleKarten(Deck deck) {
    if (deck.countSpielkarten()
        > Kombination.FUENFZIGER.getKartenAnzahl() + Kombination.FUENFZIGER.getKartenAnzahl()) {
      throw new IllegalArgumentException("Ein Spieler darf nicht mehr als 8 Karten haben!");
    }
  }

  private boolean isKomplexeKombination(Deck deck) {
    return deck.countSpielkarten() > Kombination.FUENFZIGER.getKartenAnzahl()
        && deck.countSpielkarten()
            < Kombination.FUENFZIGER.getKartenAnzahl()
                + Kombination.FUENFZIGER.getKartenAnzahl()
                + 1;
  }

  private void validateEinzelkarte(Deck deck) {
    if (deck.countSpielkarten() == 1) {
      throw new IllegalArgumentException("Einzelkarte ist keine Kombination!");
    }
  }

  private void verarbeiteAnsage(
      final Spieler beginner,
      final Map<Karte, Kombination> beginnerKombi,
      final Map<Karte, Kombination> deckerKombi) {
    // ToDo: Ansagenlogik verarbeiten
    /*  Die Reihenfolge ist also: 2×50er>50er>2x Terz>Terz<br>
            Wenn ein Spieler einen Terz bis Dame und ein anderer einen bis König, gewinnt der Spieler mit dem Terz bis
            zum König.
    <br>
                    Wenn zwei Spieler einen gleichwertigen Terz haben, also zum Beispiel beide einen Terz bis Dame(10,
                    Bube, Dame), gewinnt
            die Trumpffarbe bzw. die Erstmeldung.<br>*/
  }

  private boolean isTrumpfkarteWechsel(Zug zug) {
    return zug.getBeginnerKarten().size() == 1
        && zug.getDeckerKarten().isEmpty()
        && zug.getBeginnerKarten().getFirst().equals(new Karte(trumpfKarte.farbe(), Wert.SIEBEN))
        && nativeTrumpf;
  }
}
