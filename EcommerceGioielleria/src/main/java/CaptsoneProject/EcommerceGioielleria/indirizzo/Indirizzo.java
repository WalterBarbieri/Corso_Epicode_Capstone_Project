package CaptsoneProject.EcommerceGioielleria.indirizzo;

import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.comune.Comune;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
public class Indirizzo {
	@Id
	@GeneratedValue
	private UUID id;

	private String via;
	private String civico;
	private String localita;
	private int cap;
	@ManyToOne
	@JoinColumn(name = "comune_id")
	private Comune comune;
	@ManyToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;

	public Indirizzo(String via, String civico, String localita, int cap, Comune comune, Utente utente) {
		this.via = via;
		this.civico = civico;
		this.localita = localita;
		this.cap = cap;
		this.comune = comune;
		this.utente = utente;
	}

}
