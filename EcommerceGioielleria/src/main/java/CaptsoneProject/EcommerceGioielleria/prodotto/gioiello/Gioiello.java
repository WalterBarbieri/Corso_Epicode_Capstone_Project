package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.time.LocalDateTime;
import java.util.List;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gioiello extends Prodotto {
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public Gioiello(String nomeProdotto, String descrizione, double price, int quantita, LocalDateTime dataInserimento,
			List<Immagine> immagini) {
		super(nomeProdotto, descrizione, price, quantita, dataInserimento, immagini);

	}

}
