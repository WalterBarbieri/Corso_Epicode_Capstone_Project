package CaptsoneProject.EcommerceGioielleria.comune;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comuni")
public class ComuneController {
	private final ComuneService cs;

	@Autowired
	public ComuneController(ComuneService cs) {
		super();
		this.cs = cs;
	}

//	@GetMapping
//	public Page<Comune> findComuni(@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
//		return cs.findComuniAndPage(page, size, sortBy);
//	}

	@GetMapping
	public List<Comune> findComuni() {
		return cs.findComuni();
	}

	@GetMapping(params = "denominazione")
	public List<Comune> findComuneByDenominazionAndFilter(@RequestParam(name = "denominazione") String denominazione,
			@RequestParam(name = "nomeProvincia", required = false) String nomeProvincia,
			@RequestParam(name = "nomeRegione", required = false) String nomeRegione) {
		return cs.findComuneByDenominazione(denominazione, nomeProvincia, nomeRegione);
	}

}
