import { Indirizzo } from "./indirizzo.interface";

export interface Utente {
    id?: string,
    nome: string,
    cognome: string,
    dataNascita: Date,
    email: string,
    ragioneSociale?: string,
    ruolo: string,
    enabled: boolean,
    piva: string,
    username: string,
    residenza: Indirizzo,
    domicilio: Indirizzo,
    indirizzi: Indirizzo[]

}
