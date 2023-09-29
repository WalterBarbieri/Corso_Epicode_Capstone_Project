import { Gioiello } from "./gioiello.interface";
import { Utente } from "./utente.interface";

export interface OrderItem {
    orderItemId: string,
    prodotti: Gioiello[],
    utente: Utente

}
