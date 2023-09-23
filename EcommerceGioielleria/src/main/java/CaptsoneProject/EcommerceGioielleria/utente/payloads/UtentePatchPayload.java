package CaptsoneProject.EcommerceGioielleria.utente.payloads;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtentePatchPayload {
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String email;
	private String ragioneSociale;
	private String pIva;
}
