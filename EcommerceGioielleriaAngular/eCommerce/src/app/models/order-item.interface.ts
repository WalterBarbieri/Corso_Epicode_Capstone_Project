

import { Utente } from './utente.interface';

export interface OrderItem {
    orderItemId: string;
    prodotti: { [key: string]: number };
    utente: Utente;
  }
