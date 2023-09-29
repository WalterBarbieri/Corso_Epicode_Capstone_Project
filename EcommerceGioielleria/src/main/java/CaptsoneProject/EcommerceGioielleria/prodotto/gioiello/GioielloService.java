package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.io.IOException;
import java.time.LocalDateTime;
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
import jakarta.transaction.Transactional;

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

	@Transactional
	public Gioiello saveGioiello(String nomeProdotto, String descrizione, double price, int quantita,
			MultipartFile[] immagini, Categoria categoria) throws IOException {

		Prodotto gioiello = gf.createProdotto(nomeProdotto, descrizione, price, quantita, LocalDateTime.now(),
				new ArrayList<>());
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

	@Transactional
	public Gioiello findById(UUID id) {
		return gr.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	@Transactional
	public Page<Gioiello> findGioielliAndPage(int page, int size, String sort) {
		String[] sortParts = sort.split(",");
		String sortBy = sortParts[0];
		String sortOrder = sortParts.length > 1 ? sortParts[1] : "asc";

		Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, sortBy);

		return gr.findAll(pageable);
	}

	@Transactional
	public List<Gioiello> findGioielli() {
		return gr.findAll();
	}

	@Transactional
	public Page<Gioiello> findByCategoria(Categoria categoria, int page, int size, String sort) {
		String[] sortParts = sort.split(",");
		String sortBy = sortParts[0];
		String sortOrder = sortParts.length > 1 ? sortParts[1] : "asc";

		Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, sortBy);
		return gr.findByCategoria(categoria, pageable);
	}

	@Transactional
	public Page<Gioiello> cercaGioiello(String nomeProdotto, int page, int size, String sort) {
		String[] sortParts = sort.split(",");
		String sortBy = sortParts[0];
		String sortOrder = sortParts.length > 1 ? sortParts[1] : "asc";

		Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, sortBy);
		return gr.cercaGioielli(nomeProdotto, pageable);
	}

	public void deleteGioiello(UUID id) {
		Gioiello gioiello = this.findById(id);
		gr.delete(gioiello);
	}

}
