# Klammern

Hallo zusammen, in diesem Repository befindet sich ein Klammern-Kartenspiel für **_zwei_** Spieler.
Ursprünglich plante ich die Umsetzung eines Projekts für das osteuropäische Spiel "Debertz", jedoch stellte ich fest,
dass die Spielregeln dem Klammern-Kartenspiel sehr ähnlich sind.
Deshalb habe ich mich entschieden, Klammern als Ziel des Projekts zu wählen.

<br>Der verwendete Stack für die Umsetzung
umfasst: [![My Skills](https://skillicons.dev/icons?i=maven,java,spring,ts,vue)](https://skillicons.dev)

## Spielregeln:

Wie bei vielen Kartenspielen existieren auch beim Klammern verschiedene Variationen.
In unserem Fall wird beim Klammern mit 2 Spielern und einem 32er-Französischen Blatt gespielt.
Das Ziel des Spiels besteht darin, so viele Punkte wie möglich zu erzielen.
Während des Spiels ist es Pflicht, die ausgespielte Farbe zu bedienen.
Können sie nicht bedient werden, muss der Spieler mit einer Trumpfkarte übertreffen.

### Wertigkeiten

Die einzelnen Karten haben folgende Werte:

| Karte   | Punkte |
|---------|:------:|
| 7, 8, 9 |   0    |
| Bube    |   2    |
| Dame    |   3    |
| König   |   4    |
| Zehn    |   10   |
| Ass     |   11   |

### Reihenfolge

Die Reihenfolge der Karten lautet:

| Standard |    Trumpf    |
|:--------:|:------------:|
|    7     |      7       |
|    8     |      8       |
|    9     |     Dame     |
|   Bube   |    König     |
|   Dame   |      10      |
|  König   |     Ass      |
|    10    |   Mie (9)    |
|   Ass    | Jappa (Bube) |

### Karten austeilen

1. jedem 3 Karten
2. jedem 2 Karten
3. 1 Karte aufdecken als Trumpfvorschlag
4. Entscheidung ob jemand rein geht
5. jedem 3 Karten
6. ggf. eine andere Trumpffarbe Hinweis: offene Karte durch 7 der Farbe eintauschbar!

### Trumpffarbe festlegen

Der Mitspieler des Gebers kann entweder die offene Farbe spielen oder ablehnen.
<br>Danach ist der Geber an der Reihe.
<br> Falls kein Spieler die offene Farbe spielen möchte, kann der Mitspieler des Gebers eine Trumpffarbe vorschlagen.
<br> Wenn beide Spieler die Wahl des Trumpfs ablehnen, wird gepasst und neu gegeben.

### Erster Stich

Der Mitspieler beginnt und kann eine beliebige Karte ausspielen.
<br> Nun ist es geboten, dass jeder Spieler, der an der Reihe ist, seine Meldung über **Terze bzw. 50** macht.
<br> Dabei bleiben die geworfenen Karten offen liegen, bis geklärt ist, wer die höchsten Terze oder 50 hat.
<br> Wenn jemand einen Terz hat, wird dieser automatisch von jemandem mit einer 50 ausgestochen, eine 50 ist einem Terz
also immer überlegen.
<br> Haben 2 oder mehr Spieler einen Terz, gewinnt immer der höchste Terz.
<br> Hat ein Spieler größere Anzahl der gleichwertigen Kombinationen, gewinnt die Anzahl über der Höhe.
<br> Es geht also: 2×50 > 50 > 2x Terz > Terz
<br> Hat jemand einen Terz bis Dame und ein anderer einen bis König, gewinnt derjenige, der den Terz bis König hat.
<br> Haben 2 Spieler einen gleichwertigen Terz, also zum Beispiel beide einen Terz bis Dame (10, Bube, Dame) so gewinnt
die Trumpffarbe bzw. Erstmeldung.
<br> Wie ein Terz werden auch 50 behandelt.

**„Belle“** werden beim ersten Stich nicht angesagt, das geschieht erst, wenn man die Trumpf-Dame bzw. den Trumpf-König
abwirft.
<br> Man muss aber beide Karten besitzen, um „Belle“ ansagen zu können.
<br> Die einzelne Dame oder König haben jede für sich keine Wertigkeit und werden als normale Trumpfkarten behandelt und
müssen dann auch nicht angesagt werden.
<br> Dadurch erzielt man zusätzliche 20 Punkte.

Beim Klammern gibt es aber noch Karten, die gesondert gezählt werden. Dabei handelt es sich um die Trumpfkarten Bube (
Jappa) (höchster Trumpf) und Mie (9) (zweithöchster Trumpf)
<br> Der Trumpfbube zählt hierbei 20 zusätzliche Punkte und für die Trumpf 9 gibt es 14 zusätzliche Punkte.
<br> Der letzte Stich gibt zusätzliche 10 Punkte.

### Wertung

Es ist kein Kontra erlaubt!
<br> Komplette Partie hat 51 Augen
<br> Die Punkte, die nach einer Einzel-Partie gezählt wurden, werden in „Augen“ umgewandelt.
<br> Ein Auge hat 10 Punkte (Beisp. 50 Punkte = 5 Augen; 110 Punkte = 11 Augen).
<br> Bei 5 Punkten wird auf das nächsthöhere Auge aufgerundet (Beisp. 55 Punkte = 6 Augen; 115 Punkte = 12 Augen; 64
Punkte = 6 Augen).

## Bilden und Ausführen

Das Spiel ist in zwei Docker-Container unterteilt.
Um sie zu bilden und zu ausführen sind folgende Schritte notwendig:

Der finale Weg vom Erstellen bis zum Ausführen beider Container sieht folgend aus:

```
docker network create idigath
docker build . -f klammern-projekt-db.Dockerfile -t klammern-projekt-db --no-cache --progress=plain
docker run --net idigath --name klammern-projekt-db -e POSTGRES_PASSWORD=docker -p 8090:5432 -d klammern-projekt-db
docker build --network="host" . -f klammern-projekt.Dockerfile -t klammern-projekt --no-cache --progress=plain
docker run --net idigath --name klammern-projekt -e SPRING_DATASOURCE_URL=jdbc:postgresql://klammern-projekt-db:
5432/postgres -p 8080:8080 -d klammern-projekt
```

