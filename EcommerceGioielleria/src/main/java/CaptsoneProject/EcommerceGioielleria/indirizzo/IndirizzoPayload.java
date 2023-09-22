package CaptsoneProject.EcommerceGioielleria.indirizzo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class IndirizzoPayload {
	private String via;
	private String civico;
	private String localita;
	private int cap;
	private String nomeComune;
	private String email;

}
