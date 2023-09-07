package CaptsoneProject.EcommerceGioielleria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import CaptsoneProject.EcommerceGioielleria.exceptions.UnauthorizedException;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.UtenteService;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteLoginPayload;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteRequestPayload;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UtenteService us;

	@Autowired
	private JWTTools jt;

	@Autowired
	private PasswordEncoder bcrypt;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUtente(@RequestBody UtenteRequestPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		Utente newUtente = us.saveUtente(body);
		return newUtente;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody UtenteLoginPayload body) {

		Utente utente = us.findByEmail(body.getEmail());

		if (utente != null && bcrypt.matches(body.getPassword(), utente.getPassword())) {
			String token = jt.createToken(utente);
			return new ResponseEntity<>(new TokenResponse(token, utente), HttpStatus.OK);

		} else {
			throw new UnauthorizedException(
					"Credenziali non valide, verifica che la password o Email ed Username siano corrette");
		}
	}

}
