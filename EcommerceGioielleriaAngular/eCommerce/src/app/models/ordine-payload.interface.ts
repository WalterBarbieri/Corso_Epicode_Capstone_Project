import { Indirizzo } from "./indirizzo.interface";
import { Utente } from "./utente.interface";

export interface OrdinePayload {
    prodotti: { [key: string]: number };
    utente: Utente;
    indirizzoConsegna: Indirizzo;
}
