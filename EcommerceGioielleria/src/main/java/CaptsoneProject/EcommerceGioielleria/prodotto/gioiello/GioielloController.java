package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;

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
	public ResponseEntity<Page<Gioiello>> findGioielli(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size,
			@RequestParam(defaultValue = "dataInserimento,desc") String sort) throws NotFoundException {
		Page<Gioiello> gioielli = gs.findGioielliAndPage(page, size, sort);
		return ResponseEntity.ok(gioielli);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Gioiello> findGioiello(@PathVariable UUID id) {
		Gioiello gioiello = gs.findById(id);
		if (gioiello != null) {
			return new ResponseEntity<>(gioiello, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(params = "categoria")
	public ResponseEntity<Page<Gioiello>> findByCategoria(@RequestParam(name = "categoria") Categoria categoria,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
			@RequestParam(defaultValue = "dataInserimento,desc") String sort) throws NotFoundException {
		Page<Gioiello> gioielli = gs.findByCategoria(categoria, page, size, sort);
		return ResponseEntity.ok(gioielli);

	}

	@GetMapping(params = "nomeProdotto")
	public ResponseEntity<Page<Gioiello>> findByName(@RequestParam(name = "nomeProdotto") String nomeProdotto,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
			@RequestParam(defaultValue = "dataInserimento,desc") String sort) throws NotFoundException {
		Page<Gioiello> gioielli = gs.cercaGioiello(nomeProdotto, page, size, sort);
		return ResponseEntity.ok(gioielli);
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Gioiello> saveGioiello(@RequestParam String nomeProdotto, @RequestParam String descrizione,
			@RequestParam double price, @RequestParam int quantita, @RequestParam MultipartFile[] immagini,
			@RequestParam Categoria categoria) throws IOException {
		System.out.println(nomeProdotto);

		try {
			Gioiello gioiello = gs.saveGioiello(nomeProdotto, descrizione, price, quantita, immagini, categoria);
			return ResponseEntity.ok(gioiello);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteGioiello(@PathVariable UUID id) {

		try {
			gs.deleteGioiello(id);

			return ResponseEntity.ok("Gioiello eliminato con successo");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
