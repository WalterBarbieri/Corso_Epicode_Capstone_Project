package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gioielli")
public class GioielloController {
	private final GioielloService gs;

	@Autowired
	public GioielloController(GioielloService gs) {
		super();
		this.gs = gs;
	}

	@GetMapping
	public Page<Gioiello> findGioielli(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
		return gs.findGioielliAndPage(page, size, sort);
	}

	@GetMapping(params = "categoria")
	public Page<Gioiello> findByCategoria(@RequestParam(name = "categoria") Categoria categoria,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort) {
		return gs.findByCategoria(categoria, page, size, sort);
	}

	@GetMapping(params = "nomeProdotto")
	public Page<Gioiello> findByName(@RequestParam(name = "nomeProdotto") String nomeProdotto,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort) {
		return gs.cercaGioiello(nomeProdotto, page, size, sort);
	}

	@PostMapping
	public Gioiello saveGioiello(@RequestParam String nomeProdotto, @RequestParam String descrizione,
			@RequestParam double price, @RequestParam int quantita, @RequestParam MultipartFile[] immagini,
			@RequestParam Categoria categoria) throws IOException {
		return gs.saveGioiello(nomeProdotto, descrizione, price, quantita, immagini, categoria);
	}
}
