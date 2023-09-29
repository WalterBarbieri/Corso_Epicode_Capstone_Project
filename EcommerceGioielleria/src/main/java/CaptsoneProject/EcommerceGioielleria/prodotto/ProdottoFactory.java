package CaptsoneProject.EcommerceGioielleria.prodotto;

import java.time.LocalDateTime;
import java.util.List;

import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;

public abstract class ProdottoFactory {
	public abstract Prodotto createProdotto(String nomeProdotto, String descrizione, double price, int quantita,
			LocalDateTime dataInserimento, List<Immagine> immagini);
}
