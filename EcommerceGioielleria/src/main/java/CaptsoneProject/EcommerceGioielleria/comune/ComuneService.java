package CaptsoneProject.EcommerceGioielleria.comune;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;

@Service
public class ComuneService {
	private final ComuneRepository cr;

	@Autowired
	public ComuneService(ComuneRepository cr) {
		super();
		this.cr = cr;
	}

	public Comune saveComune(Comune comune) {
		return cr.save(comune);
	}

	public Page<Comune> findComuniAndPage(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return cr.findAll(pageable);
	}

	public List<Comune> findComuni() {
		return cr.findAll();
	}

	public Comune findComuneByNome(String denominazione) {
		return cr.findByDenominazione(denominazione).orElseThrow(() -> new NotFoundException(denominazione));
	}

	public List<Comune> findComuneByDenominazione(String denominazione, String provincia, String regione) {
		if (provincia != null) {
			return cr.findByDenominazioneIgnoreCaseContainingAndNomeProvincia(denominazione, provincia);
		} else if (provincia == null && regione != null) {
			return cr.findByDenominazioneIgnoreCaseContainingAndNomeRegione(denominazione, regione);
		} else if (provincia == null && regione == null) {
			return cr.findByDenominazioneIgnoreCaseContaining(denominazione);
		}
		return null;
	}

}
