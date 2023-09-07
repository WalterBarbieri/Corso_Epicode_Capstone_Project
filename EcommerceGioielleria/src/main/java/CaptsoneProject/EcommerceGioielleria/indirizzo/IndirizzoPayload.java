package CaptsoneProject.EcommerceGioielleria.indirizzo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IndirizzoPayload {
	private String via;
	private String civico;
	private String localita;
	private int cap;
	private String nomeComune;
}
