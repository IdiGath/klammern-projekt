package de.idigath.klammern.backend.model;

import java.util.Map;

/**
 * Ein Record für den Datentransfer zwischen den Phasen. In diesem Record übergibt eine Phase der anderen
 * Informationen über den jeweiligen Beginner, den Stand in der Runde, das benutzte Spieldeck, sowie die Karten der
 * beiden Spielern.
 *
 * @param beginner  Beginner des Zuges
 * @param stand     Stand in der Runde
 * @param spielDeck Spieldeck der Runde
 * @param reihen    Karten von beiden Spielern
 */
public record PhasenInfo(Spieler beginner, Stand stand, Deck spielDeck, Map<Spieler, Deck> reihen) {
}
