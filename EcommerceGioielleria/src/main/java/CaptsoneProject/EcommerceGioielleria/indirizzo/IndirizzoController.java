package CaptsoneProject.EcommerceGioielleria.indirizzo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
	private final IndirizzoService is;

	@Autowired
	public IndirizzoController(IndirizzoService is) {
		super();
		this.is = is;
	}

	@GetMapping
	public List<Indirizzo> findIndirizzi() {
		return is.findIndirizzi();
	}

	@PostMapping("/residenza")
	public Indirizzo saveResidenza(@RequestBody IndirizzoPayload body) {
		return is.saveResidenza(body);
	}

	@PostMapping("/domicilio")
	public Indirizzo saveDomicilio(@RequestBody IndirizzoPayload body) {
		return is.saveDomicilio(body);
	}

	@PostMapping("/indirizzi")
	public Indirizzo saveIndirizzi(@RequestBody IndirizzoPayload body) {
		return is.saveIndirizzi(body);
	}

	@PutMapping("/{id}")
	public Indirizzo updateIndirizzo(@PathVariable UUID id, @RequestBody IndirizzoPayload body) {
		return is.updateIndirizzo(id, body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteIndirizzo(@PathVariable UUID id) {
		is.deleteIndirizzo(id);

		return ResponseEntity.noContent().build();
	}

}
