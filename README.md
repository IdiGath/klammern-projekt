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

### Ziel

Eine komplette Partie dauert an, bis ein Spieler 51 Augen sammelt.<br>
Jede Partie besteht aus mehreren Runden.<br>
Die Punkte, die nach jeder Runde gezählt wurden, werden in Augen umgewandelt.<br>
Die Runde endet, wenn die Spieler keine Karten mehr haben.

### Augen

Ein Auge entspricht 10 Punkten(z.B. 50 Punkte = 5 Augen; 110 Punkte = 11 Augen).<br>
Bei 5 Punkten wird auf das nächsthöhere Auge aufgerundet(z.B. 55 Punkte = 6 Augen; 115 Punkte = 12 Augen; 64 Punkte = 6
Augen).<br>

### Punkte

Die Punkte werden in jeder Runde durch Züge, Kombinationen und Sonderpunkte erzielt.

### Zug

Ein Spieler spielt eine Karte aus, der andere bedient sie.<br>
In jedem Zug ist es Pflicht, die ausgespielte Farbe zu bedienen. Wenn sie nicht bedient werden kann, muss der
Spieler<br>
mit einer Trumpfkarte übertreffen, oder eine beliebige Karte abgeben.<br>
Die Bedienung der Farbe entscheidet darüber, welcher der beiden Spieler die Punkte bekommt.<br>
Die Wertigkeit der gespielten Karten entscheidet wie viele Punkte erzielt werden.<br>
Der Spieler wer den Zug gewinnt, beginnt den nächsten.

### Bedienung

Die Bedienung einer Karte muss vorrangig mit derselben Farbe erfolgen.<br>
Die Kartenreihenfolge bestimmt den Gewinner der Bedienung.<br>
Wenn ein Spieler die Kartenfarbe nicht bedienen kann, tut er dies mit einem Trumpf.<br>
Der Trumpf hat immer eine höhere Reihenfolge, als die Farbe der ausgespielten Karte für die Bedienung.<br>
Bei Bedienung der Trumpffarbe wird eigene Reihenfolge verwendet.<br>
Falls die Farbe der ausgespielten Karte nicht bedient werden kann und kein Trumpf auf der Hand ist, muss der Spieler
eine beliebige Karte ausspielen.<br>
In diesem Fall spielt die Kartenreihenfolge keine Rolle, der Spieler wer den Zug begonnen hat, gewinnt den Zug.<br>
Der Gewinner einer Bedienung sammelt die Punkte von beiden karten.<br>

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
|    10    |   9 (Mie)    |
|   Ass    | Jappa (Bube) |

### Kombinationen

Jeder Spieler kann am Beginn erstes Stichs seine Kombinationen melden.<br>
Die abgelegten Karten bleiben offen, bis festgestellt ist, wer die höchsten Kombinationen hat.<br>
Zu den Kombinationen zählen: Terz, Fünfziger und Belle. Eine Belle besteht aus der Trumpf-Dame und dem Trumpf-König.<br>
Ihre Wertigkeit beträgt 20 Punkte.<br>
Sie wird beim ersten Stich nicht angesagt, sondern erst, wenn die Trumpf-Dame oder der Trumpf-König abgeworfen wird.<br>
Ein Terz besteht aus drei Karten in aufsteigender Reihenfolge und hat eine Wertigkeit von zwanzig Punkten.<br>
Eine Fünfziger (50er) besteht aus vier Karten in aufsteigender Reihenfolge und bringt, wie der Name schon sagt, fünfzig
Punkte.<br>

Wenn ein Spieler einen Terz hat, wird dieser automatisch von jemandem mit einer 50er ausgestochen, eine 50er ist
einem Terz also immer überlegen.<br>
Haben zwei oder mehr Spieler einen Terz, gewinnt immer der höchste Terz. Wenn ein
Spieler eine größere Anzahl der gleichwertigen Kombinationen hat, gewinnt die Anzahl über der Höhe.<br>
Die Reihenfolge ist also: 2×50er>50er>2x Terz>Terz<br>
Wenn ein Spieler einen Terz bis Dame und ein anderer einen bis König, gewinnt der Spieler mit dem Terz bis zum König.
<br>
Wenn zwei Spieler einen gleichwertigen Terz haben, also zum Beispiel beide einen Terz bis Dame(10, Bube, Dame), gewinnt
die Trumpffarbe bzw. die Erstmeldung.<br>
Wie ein Terz werden auch 50er behandelt.

### Sonderpunkte

Beim Klammern gibt es Karten, die gesondert gezählt werden.<br>
Dazu gehören der Trumpfbube (Jappa), der als höchster Trumpf 20 zusätzliche Punkte zählt, und die Trumpf 9 (Mie),
die als zweithöchster Trumpf 14 zusätzliche Punkte.<br>
Der letzte Stich gibt zusätzliche 10 Punkte.

### Kartenausteilung

1. jedem 3 Karten
2. jedem 2 Karten
3. 1 Karte aufdecken als Trumpfvorschlag
4. Entscheidung, ob jemand "reingeht"
5. jedem 3 Karten
6. ggf. eine andere Trumpffarbe auswählen Hinweis: offene Trumpf-Karte durch 7 der Farbe eintauschbar!

### Trumpffarbe festlegen

Der Mitspieler des Gebers kann entweder die offene Farbe spielen oder ablehnen.<br>
Danach ist der Geber an der Reihe.<br>
Falls kein Spieler die offene Farbe spielen möchte, kann der Mitspieler des Gebers eine Trumpffarbe vorschlagen.<br>
Wenn beide Spieler die Wahl des Trumpfs ablehnen, wird gepasst und neu gegeben.

## Prozessablauf

![Klammern-Ablauf](Klammern-Ablauf.png)
[Oder als Pdf](./Klammern-Ablauf.pdf)

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

## Erledigte Lektionen:

* Lektion 1 - Grundarchitektur
* Lektion 2 - API Definition
* Lektion 3 - DTO
* Lektion 4 - Modell
* Lektion 5 - Kartenvergleich
* Lektion 6 - Unit-Tests (Backend)
