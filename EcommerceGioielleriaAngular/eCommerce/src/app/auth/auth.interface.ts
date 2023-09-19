export interface AuthData {
    token: string;
    utenteTokenResponse: {
        id: string;
        nome: string;
        cognome: string;
        email: string;
    };
}
