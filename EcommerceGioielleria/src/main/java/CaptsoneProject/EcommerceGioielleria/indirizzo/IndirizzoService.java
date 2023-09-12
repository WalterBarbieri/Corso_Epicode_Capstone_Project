package CaptsoneProject.EcommerceGioielleria.indirizzo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.comune.Comune;
import CaptsoneProject.EcommerceGioielleria.comune.ComuneService;
import CaptsoneProject.EcommerceGioielleria.exceptions.BadRequestException;
import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.UtenteService;

@Service
public class IndirizzoService {
	private final IndirizzoRepository ir;
	private final ComuneService cs;
	private final UtenteService us;

	@Autowired
	public IndirizzoService(IndirizzoRepository ir, ComuneService cs, UtenteService us) {
		super();
		this.ir = ir;
		this.cs = cs;
		this.us = us;
	}

	public Indirizzo addIndirizzo(IndirizzoPayload body) {
		Comune comune = cs.findComuneByNome(body.getNomeComune());
		Indirizzo indirizzo = new Indirizzo(body.getVia(), body.getCivico(), body.getLocalita(), body.getCap(), comune,
				null);
		return indirizzo;
	}

	public Indirizzo saveResidenza(IndirizzoPayload body) {
		try {
			Utente utente = us.findById(body.getId());
			Indirizzo checkResidenza = utente.getResidenza();
			if (checkResidenza != null) {
				utente.setResidenza(null);
				ir.delete(checkResidenza);
			}
			Indirizzo nuovaResidenza = this.addIndirizzo(body);
			nuovaResidenza.setUtente(utente);
			utente.setResidenza(nuovaResidenza);
			us.updateUtente(utente);
			return nuovaResidenza;
		} catch (BadRequestException e) {
			e.printStackTrace();
			System.out.println("Errore nella creazione dell'indirizzo");
			return null;
		}

	}

	public Indirizzo saveDomicilio(IndirizzoPayload body) {
		try {
			Utente utente = us.findById(body.getId());
			Indirizzo checkDomicilio = utente.getDomicilio();
			if (checkDomicilio != null) {
				utente.setDomicilio(null);
				ir.delete(checkDomicilio);
			}
			Indirizzo nuovoDomicilio = this.addIndirizzo(body);
			nuovoDomicilio.setUtente(utente);
			utente.setDomicilio(nuovoDomicilio);
			us.updateUtente(utente);
			return nuovoDomicilio;
		} catch (BadRequestException e) {
			e.printStackTrace();
			System.out.println("Errore nella creazione dell'indirizzo");
			return null;
		}

	}

	public Indirizzo saveIndirizzi(IndirizzoPayload body) {
		try {
			Utente utente = us.findById(body.getId());
			Indirizzo indirizzoConsegna = this.addIndirizzo(body);
			indirizzoConsegna.setUtente(utente);
			utente.getIndirizzi().add(indirizzoConsegna);
			us.updateUtente(utente);
			return indirizzoConsegna;
		} catch (BadRequestException e) {
			e.printStackTrace();
			System.out.println("Errore nella creazione dell'indirizzo");
			return null;
		}

	}

	public Indirizzo findIndirizzoById(UUID id) {
		return ir.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public List<Indirizzo> findIndirizzi() {
		return ir.findAll();
	}

	public void deleteIndirizzo(UUID id) {
		try {
			Indirizzo indirizzo = ir.findById(id).orElseThrow(() -> new NotFoundException(id));
			Utente utente = us.findById(indirizzo.getUtente().getId());
			if (utente.getResidenza() != null && indirizzo.getId().equals(utente.getResidenza().getId())) {
				utente.setResidenza(null);
			} else if (utente.getDomicilio() != null && indirizzo.getId().equals(utente.getDomicilio().getId())) {
				utente.setDomicilio(null);
			}
			System.out.println("ID dell'indirizzo da eliminare: " + indirizzo.getId());

			ir.delete(indirizzo);
		} catch (NotFoundException e) {
			e.printStackTrace();
			System.out.println("Errore durante l'eliminazione dell'indirizzo");
		}

	}

	public Indirizzo updateIndirizzo(UUID id, IndirizzoPayload body) {
		Comune comune = cs.findComuneByNome(body.getNomeComune());
		Indirizzo indirizzoAggiornato = this.findIndirizzoById(id);
		indirizzoAggiornato.setVia(body.getVia());
		indirizzoAggiornato.setCivico(body.getCivico());
		indirizzoAggiornato.setLocalita(body.getLocalita());
		indirizzoAggiornato.setCap(body.getCap());
		indirizzoAggiornato.setComune(comune);

		return ir.save(indirizzoAggiornato);
	}

}
