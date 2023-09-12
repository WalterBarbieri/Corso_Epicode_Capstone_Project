package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.util.List;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gioiello extends Prodotto {
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public Gioiello(String nomeProdotto, String descrizione, double price, int quantita, List<Immagine> immagini) {
		super(nomeProdotto, descrizione, price, quantita, immagini);

	}

}
