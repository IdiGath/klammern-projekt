<template>
  <div class="about">
    <h1>Gefetchte-Daten:</h1>
    <h2>Daten: {{ partie }}</h2>
  </div>
</template>

<script lang="ts" setup>
import {reactive} from "vue";
import type Partie from "@/types/Partie";
import fetchPartie from "@/services/fetchPartie";
import {Farbe} from "@/types/Farbe";
import {Wert} from "@/types/Wert";
import type {Stand} from "@/types/Stand";
import type {Runde} from "@/types/Runde";
import type {Karte} from "@/types/Karte";

const emptyTrumpf: Karte = {farbe: Farbe.EMPTY, wert: Wert.EMPTY};
const emptyStand: Stand = {punkte: new Map(), augen: new Map(), historie: []};
const emptyRunde: Runde = {reihenfolge: '', trumpf: emptyTrumpf, karten: new Map()};
const partie = reactive<Partie>({
  runde: emptyRunde, stand: emptyStand,
});

(async () => {
  Object.assign(partie, await fetchPartie());
})();

</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
