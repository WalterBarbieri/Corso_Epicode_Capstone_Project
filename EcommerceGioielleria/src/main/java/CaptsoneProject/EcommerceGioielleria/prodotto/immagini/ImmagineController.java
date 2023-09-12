package CaptsoneProject.EcommerceGioielleria.prodotto.immagini;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.gioiello.GioielloService;

@RestController
@RequestMapping("/immagini")
public class ImmagineController {
	private final ImmagineService is;
	private final GioielloService gs;

	@Autowired
	public ImmagineController(ImmagineService is, GioielloService gs) {
		super();
		this.is = is;
		this.gs = gs;
	}

	@GetMapping("/{prodottoId}")
	public ResponseEntity<List<byte[]>> getImmaginiByProdotto(@PathVariable UUID prodottoId) {
		try {
			List<byte[]> immagini = is.getImmagini(prodottoId);
			return new ResponseEntity<>(immagini, HttpStatus.FOUND);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Immagine> getImmagine(@PathVariable UUID id) {
		Immagine immagine = is.getImmagine(id);

		if (immagine != null) {
			return new ResponseEntity<>(immagine, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{prodottoId}")
	public ResponseEntity<Prodotto> saveImmagine(@PathVariable UUID prodottoId,
			@RequestParam("immagine") MultipartFile immagine) {
		try {
			Immagine nuovaImmagine = is.saveImmagine(prodottoId, immagine);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Prodotto prodotto = gs.findById(prodottoId);

		return ResponseEntity.ok(prodotto);
	}

	@DeleteMapping("/{id}")
	public void deleteImmagine(@PathVariable UUID id) {
		is.deleteImmagine(id);
	}

}
