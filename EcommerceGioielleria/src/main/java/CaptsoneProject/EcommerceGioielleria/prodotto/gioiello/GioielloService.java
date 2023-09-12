package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;
import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.ImmagineService;

@Service
public class GioielloService {
	private final GioielloRepository gr;
	private final GioielloFactory gf;
	private final ImmagineService is;

	@Autowired
	public GioielloService(GioielloRepository gr, GioielloFactory gf, ImmagineService is) {
		super();
		this.gr = gr;
		this.gf = gf;
		this.is = is;
	}

	public Gioiello saveGioiello(String nomeProdotto, String descrizione, double price, int quantita,
			MultipartFile[] immagini, Categoria categoria) throws IOException {

		Prodotto gioiello = gf.createProdotto(nomeProdotto, descrizione, price, quantita, new ArrayList<>());
		gf.addCategory(gioiello, categoria);
		Gioiello nuovoGioiello = gr.save((Gioiello) gioiello);

		UUID id = nuovoGioiello.getId();
		List<Immagine> immaginiCaricate = new ArrayList<>();

		for (MultipartFile immagine : immagini) {
			Immagine immagineSalvata = is.saveImmagine(id, immagine);
			immaginiCaricate.add(immagineSalvata);

		}

		nuovoGioiello.setImmagini(immaginiCaricate);
		return gr.save(nuovoGioiello);

	}

	public Gioiello findById(UUID id) {
		return gr.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Page<Gioiello> findGioielliAndPage(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		return gr.findAll(pageable);
	}

	public List<Gioiello> findGioielli() {
		return gr.findAll();
	}

	public Page<Gioiello> findByCategoria(Categoria categoria, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return gr.findByCategoria(categoria, pageable);
	}

	public Page<Gioiello> cercaGioiello(String nomeProdotto, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return gr.cercaGioielli(nomeProdotto, pageable);
	}

}
