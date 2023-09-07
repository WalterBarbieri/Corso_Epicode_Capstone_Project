package CaptsoneProject.EcommerceGioielleria.utente;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.exceptions.BadRequestException;
import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteRequestPayload;

@Service
public class UtenteService {

	private final UtenteRepository ur;

	@Autowired
	public UtenteService(UtenteRepository ur) {
		super();
		this.ur = ur;
	}

	public Utente saveUtente(UtenteRequestPayload body) {
		ur.findByEmail(body.getEmail()).ifPresent(e -> {
			throw new BadRequestException("Email già presente nel database!");
		});

		Utente utente = new Utente(body.getNome(), body.getCognome(), body.getDataNascita(), body.getEmail(),
				body.getPassword(), Ruolo.ADMIN);
		return ur.save(utente);
	}

	public Utente findById(UUID utenteId) {
		return ur.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
	}

	public Page<Utente> findUtentiAndPage(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		return ur.findAll(pageable);
	}

	public Page<Utente> findByNome(String nome, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return ur.findByNome(nome, pageable);
	}

	public Page<Utente> findByCognome(String cognome, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return ur.findByNome(cognome, pageable);
	}

	public Utente findByEmail(String email) {
		return ur.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
	}

	public Utente findByEmailAndUpdate(String email, UtenteRequestPayload body) {
		Utente utente = this.findByEmail(email);
		utente.setNome(body.getNome());
		utente.setCognome(body.getCognome());
		utente.setDataNascita(body.getDataNascita());
		utente.setEmail(body.getEmail());
		utente.setPassword(body.getPassword());
		return ur.save(utente);
	}

	public void deleteUtente(String email) {
		Utente utente = this.findByEmail(email);
		ur.delete(utente);
	}

}
