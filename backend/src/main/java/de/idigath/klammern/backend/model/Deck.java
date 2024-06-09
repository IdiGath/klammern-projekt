package de.idigath.klammern.backend.model;

import java.util.*;

/**
 * Die abstrakte Klasse für alle Kartensammlungen wie Spieldecke oder Hand von dem Spieler. Sie
 * implementiert allgemeine Grundfunktionen, legt fest die Strukturen der Datenhaltung und dient für
 * die Typisierung bei der Verarbeitung.
 */
public abstract class Deck {

    protected final Set<Karte> karten;
    private final Random random = new Random();

    protected Deck(Set<Karte> karten) {
        this.karten = karten;
    }

    /**
     * Gibt eine zufällige Karte zurück. Falls das Deck keine Spielkarten beinhaltet, kommt es zu
     * einer IllegalStateException mit einem Hinweis.
     *
     * @return Zufällige Spielkarte
     * @throws IllegalStateException Wenn im Deck keine Karten mehr gibt
     */
    public Karte giveSpielkarte() {
        checkIsEmpty();
        int randomIndex = random.nextInt(karten.size());
        Iterator<Karte> iterator = karten.iterator();
        for (int i = 0; i < randomIndex; i++) {
            iterator.next();
        }
        Karte randomSpielkarte = iterator.next();
        iterator.remove();
        return randomSpielkarte;
    }

    /**
     * Gibt eine zufällige Karte der angegebenen Farbe zurück. Falls das Deck keine passende
     * Spielkarten der Farbe beinhaltet, kommt es zu einer IllegalStateException mit einem Hinweis.
     *
     * @param farbe Übergebene Farbe für die ermittlung der Karte
     * @return Zufällige Spielkarte der jeweiligen Farbe
     * @throws IllegalStateException Wenn im Deck keine passende Karte gefunden wird
     */
    public Karte giveSpielkarte(Farbe farbe) {
        checkIsEmpty();
        List<Karte> matchingCards =
                karten.stream().filter(karte -> karte.farbe().equals(farbe)).toList();

        if (matchingCards.isEmpty()) {
            throw new IllegalStateException("Das Deck hat keine passende Karten in der Farbe: " + farbe);
        }

        int randomIndex = random.nextInt(matchingCards.size());
        Karte karte = matchingCards.get(randomIndex);
        karten.remove(karte);
        return karte;
    }

    /**
     * Gibt eine Spielkarte mit der angegebenen Farbe und dem angegebenen Kartenwert zurück. Falls das
     * Deck keine passende Karte enthält, wird eine IllegalStateException ausgelöst.
     *
     * @param farbe      Die Farbe der gesuchten Spielkarte
     * @param kartenWert Der Kartenwert der gesuchten Spielkarte
     * @return Die gefundene Spielkarte
     * @throws IllegalStateException Wenn im Deck keine passende Karte gefunden wird
     */
    public Karte giveSpielkarte(Farbe farbe, Wert kartenWert) {
        checkIsEmpty();
        Optional<Karte> spielkarte =
                karten.stream()
                        .filter(karte -> karte.farbe().equals(farbe) && karte.wert().equals(kartenWert))
                        .findFirst();

        if (spielkarte.isEmpty()) {
            throw new IllegalStateException(
                    "Das Deck hat keine passende Karte in der Farbe: "
                            + farbe
                            + " und dem Wert: "
                            + kartenWert);
        }

        Karte karte = spielkarte.get();
        karten.remove(karte);
        return karte;
    }

    /**
     * Gibt alle Karten vom Deck im Form einer Liste zurück.
     *
     * @return Alle im Deck vorhandene Karten.
     */
    public List<Karte> getSpielkartenList() {
        return karten.stream().toList();
    }

    /**
     * Gibt die Anzahl der Spielkarten im Deck zurück.
     *
     * @return die Anzahl der Spielkarten im Deck
     */
    public int countSpielkarten() {
        return karten.size();
    }

    /**
     * Fügt eine Karte dem erstellten Deck hinzu.
     *
     * @param spielkarte neue Spielkarte
     * @throws IllegalStateException Karte kann nicht hinzugefügt werden
     */
    public abstract void addSpielkarte(Karte spielkarte);

    private void checkIsEmpty() {
        if (Objects.isNull(karten) || karten.isEmpty()) {
            throw new IllegalStateException("Das Deck hat keine passende Karten");
        }
    }
}
