package CaptsoneProject.EcommerceGioielleria.prodotto.immagini;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Immagine {
	@Id
	@GeneratedValue
	private UUID id;

	@Lob
	private byte[] dati;

	private String nomeImmagine;

	@ManyToOne
	@JoinColumn(name = "prodotto_id")
	@JsonIgnore
	private Prodotto prodotto;

	public Immagine(byte[] dati, String nomeImmagine, Prodotto prodotto) {
		this.dati = dati;
		this.nomeImmagine = nomeImmagine;
		this.prodotto = prodotto;
	}

}
