package CaptsoneProject.EcommerceGioielleria.ordine;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CaptsoneProject.EcommerceGioielleria.exceptions.BadRequestException;
import CaptsoneProject.EcommerceGioielleria.orderItem.OrderItemPayload;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

	private final OrdineService os;

	@Autowired
	public OrdineController(OrdineService os) {
		super();
		this.os = os;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Page<Ordine>> findOrdiniAndPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataOrdine") String sort) {
		Page<Ordine> ordini = os.findAllOrdini(page, size, sort);
		return ResponseEntity.ok(ordini);
	}

	@GetMapping("/{ordineId}")
	public ResponseEntity<Ordine> findByOrdineId(@PathVariable UUID ordineId) {
		Ordine ordine = os.findById(ordineId);
		return ResponseEntity.ok(ordine);
	}

	@GetMapping("/utente")
	public ResponseEntity<Page<Ordine>> findByUtenteId(@RequestParam UUID id,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "dataOrdine") String sort) {
		try {
			Page<Ordine> ordini = os.findByUtente(id, page, size, sort);
			return ResponseEntity.ok(ordini);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity<Ordine> saveOrdine(@RequestBody OrderItemPayload orderItemPayload,
			@RequestParam double importo, @RequestParam double iva, @RequestParam double totale) {
		try {
			Ordine ordine = os.saveOrdine(orderItemPayload, importo, iva, totale);
			return ResponseEntity.ok(ordine);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{ordineId}")
	public ResponseEntity<Ordine> cambiaStatoOrdine(@PathVariable UUID ordineId,
			@RequestParam StatoOrdine statoOrdine) {
		try {
			Ordine ordine = os.cambiaStatoOrdine(ordineId, statoOrdine);
			return ResponseEntity.ok(ordine);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{ordineId}")
	public void eliminaOrdine(@PathVariable UUID ordineId) {
		os.eliminaOrdine(ordineId);
	}

}
