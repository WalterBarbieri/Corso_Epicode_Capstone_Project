package CaptsoneProject.EcommerceGioielleria.security;

import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteTokenPayload;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
	private String token;
	private UtenteTokenPayload utenteTokenResponse = new UtenteTokenPayload();

	public TokenResponse(String token, Utente utente) {
		this.token = token;
		this.utenteTokenResponse.setId(utente.getId());
		this.utenteTokenResponse.setNome(utente.getNome());
		this.utenteTokenResponse.setCognome(utente.getCognome());
		this.utenteTokenResponse.setEmail(utente.getEmail());
	}

}
