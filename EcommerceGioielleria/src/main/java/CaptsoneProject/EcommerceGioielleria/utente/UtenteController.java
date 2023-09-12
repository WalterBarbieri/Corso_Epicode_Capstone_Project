package CaptsoneProject.EcommerceGioielleria.utente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteRequestPayload;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	private final UtenteService us;

	@Autowired
	public UtenteController(UtenteService us) {
		this.us = us;
	}

	@GetMapping
	public Page<Utente> findUtenti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.findUtentiAndPage(page, size, sortBy);
	}

	@GetMapping(params = "nome")

	public Page<Utente> findByNome(@RequestParam(name = "nome") String nome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.findByNome(nome, page, size, sortBy);
	}

	@GetMapping(params = "cognome")
	public Page<Utente> findByCognome(@RequestParam(name = "cognome") String cognome,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return us.findByCognome(cognome, page, size, sortBy);
	}

	@GetMapping(params = "email")
	public Utente findByEmail(@RequestParam(name = "email") String email) {
		return us.findByEmail(email);
	}

	@GetMapping("/cerca")
	public Page<Utente> cercaUtenti(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cognome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.cercaUtenti(nome, cognome, page, size, sortBy);
	}

	@PutMapping("/{email}")
	public Utente updateUtente(@PathVariable String email, @RequestBody UtenteRequestPayload body) {
		return us.findByEmailAndUpdate(email, body);
	}

	@DeleteMapping("/{email}")
	public ResponseEntity<String> deleteUtente(@PathVariable String email) {
		us.deleteUtente(email);

		return ResponseEntity.ok("Utente eliminato con successo");
	}
}
