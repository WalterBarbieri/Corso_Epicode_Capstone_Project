package CaptsoneProject.EcommerceGioielleria.indirizzo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.comune.ComuneService;

@Service
public class IndirizzoService {
	private final IndirizzoRepository ir;
	private final ComuneService cs;

	@Autowired
	public IndirizzoService(IndirizzoRepository ir, ComuneService cs) {
		super();
		this.ir = ir;
		this.cs = cs;
	}

//	public Indirizzo saveIndirizzo(IndirizzoPayload body) {
//		Comune comune = cs.findComuneByNome(body.getNomeComune());
//		Indirizzo indirizzo = new Indirizzo(body)
//	}

}
