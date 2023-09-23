import { Immagine } from "./immagine.interface";

export interface Gioiello {
    id: string,
    nomeProdotto: string,
    descrizione: string,
    price: number,
    quantita: number,
    immagini: Immagine[],
    categoria: string;

}
