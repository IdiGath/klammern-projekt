import type {Karte} from "@/types/Karte";

export interface Runde {

    reihenfolge: string
    trumpf: Karte
    karten: Map<string, Set<Karte>>
}