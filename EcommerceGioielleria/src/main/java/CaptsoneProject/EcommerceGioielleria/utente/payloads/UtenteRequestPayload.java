package CaptsoneProject.EcommerceGioielleria.utente.payloads;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UtenteRequestPayload {
	@NotNull(message = "Il campo nome è obbligatorio")
	private String nome;
	@NotNull(message = "Il campo cognome è obbligatorio")
	private String cognome;
	@NotNull(message = "Il campo data di nascita è obbligatorio")
	private LocalDate dataNascita;
	@NotNull(message = "Inserisci una email valida, quest campo non può rimanere vuoto")
	@Email(message = "L'email inserita non è un indirizzo valido")
	private String email;
	@NotNull(message = "La password è obbligatoria")
	private String password;
}
