package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.util.List;

import org.springframework.stereotype.Component;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.ProdottoFactory;
import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;

@Component
public class GioielloFactory extends ProdottoFactory {

	@Override
	public Prodotto createProdotto(String nomeProdotto, String descrizione, double price, int quantita,
			List<Immagine> immagini) {
		Prodotto gioiello = new Gioiello(nomeProdotto, descrizione, price, quantita, immagini);
		return gioiello;
	}

	public void addCategory(Prodotto prodotto, Categoria categoria) {
		if (prodotto instanceof Gioiello) {
			((Gioiello) prodotto).setCategoria(categoria);
		}
	}

}
