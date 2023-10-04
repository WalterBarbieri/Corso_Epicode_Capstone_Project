import { Indirizzo } from "./indirizzo.interface";
import { Utente } from "./utente.interface";

export interface Ordine {
    ordineId: string;
    prodotti: { [key: string]: number };
    utente: Utente;
    indirizzoConsegna: Indirizzo;
    dataOrdine: Date;
    importo: number;
    iva: number;
    totale: number;
    statoOrdine: string;
}
