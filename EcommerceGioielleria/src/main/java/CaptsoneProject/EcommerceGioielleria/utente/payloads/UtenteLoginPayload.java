package CaptsoneProject.EcommerceGioielleria.utente.payloads;

import lombok.Data;

@Data
public class UtenteLoginPayload {
	private String email;
	private String password;
}
