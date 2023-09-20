import { Comune } from "./comune.interface";
import { Utente } from "./utente.interface";

export interface Indirizzo {
    id: string,
    via: string,
    civico: string,
    localita: string,
    cap: number,
    comune: Comune,
    utente: Utente

}
