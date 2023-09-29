export interface GioielloPayload {
    nomeProdotto: string,
    descrizione: string,
    price: number,
    quantita: number,
    immagini: File[],
    categoria: string;
}
