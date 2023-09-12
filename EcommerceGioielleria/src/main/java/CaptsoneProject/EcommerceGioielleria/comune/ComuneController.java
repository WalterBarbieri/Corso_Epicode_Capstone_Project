package CaptsoneProject.EcommerceGioielleria.comune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	@GetMapping
	public Page<Comune> findComuni(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return cs.findComuniAndPage(page, size, sortBy);
	}

	@GetMapping(params = "denominazione")
	public Page<Comune> findComuneByDenominazione(@RequestParam(name = "denominazione") String denominazione,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return cs.findComuneByDenominazione(denominazione, page, size, sortBy);
	}

}
