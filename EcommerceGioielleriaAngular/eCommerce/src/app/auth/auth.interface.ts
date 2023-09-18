export interface AuthData {
    accessToken: string;
    user: {
        id: string;
        nome: string;
        cognome: string;
        email: string;
    };
}
