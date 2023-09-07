package CaptsoneProject.EcommerceGioielleria.security;

import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
	private String token;
	private UUID id;
	private String nome;
	private String cognome;
	private String email;

	public TokenResponse(String token, Utente utente) {
		this.token = token;
		this.id = utente.getId();
		this.nome = utente.getNome();
		this.cognome = utente.getCognome();
		this.email = utente.getEmail();
	}

}
