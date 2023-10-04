package CaptsoneProject.EcommerceGioielleria.utente.payloads;

import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.utente.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UtenteTokenPayload {
	private UUID id;
	private String nome;
	private String cognome;
	private String email;
	private Ruolo ruolo;

}
