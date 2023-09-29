package CaptsoneProject.EcommerceGioielleria.utente;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtentePatchPayload;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	private final UtenteService us;

	private final PasswordEncoder bcrypt;

	@Autowired
	public UtenteController(UtenteService us, PasswordEncoder bcrypt) {
		this.us = us;
		this.bcrypt = bcrypt;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Utente> findUtenti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.findUtentiAndPage(page, size, sortBy);
	}

	@GetMapping(params = "id")
	public Utente findById(@RequestParam(name = "id") UUID id) {
		return us.findById(id);
	}

	@GetMapping(params = "nome")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Utente> findByNome(@RequestParam(name = "nome") String nome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.findByNome(nome, page, size, sortBy);
	}

	@GetMapping(params = "cognome")
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Utente> cercaUtenti(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cognome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return us.cercaUtenti(nome, cognome, page, size, sortBy);
	}

	@PatchMapping("/{email}")
	public Utente updateUtente(@PathVariable String email, @RequestBody UtentePatchPayload body) {
		return us.findByEmailAndUpdate(email, body);
	}

	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deleteUtente(@PathVariable String email) {
		us.deleteUtente(email);

		return ResponseEntity.noContent().build();
	}
}
