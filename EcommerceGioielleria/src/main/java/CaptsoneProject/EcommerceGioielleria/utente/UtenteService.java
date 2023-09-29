package CaptsoneProject.EcommerceGioielleria.utente;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import CaptsoneProject.EcommerceGioielleria.exceptions.BadRequestException;
import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtentePatchPayload;
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
				body.getPassword(), Ruolo.USER);
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
		return ur.findByCognome(cognome, pageable);
	}

	public Page<Utente> cercaUtenti(String nome, String cognome, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return ur.cercaUtenti(nome, cognome, pageable);
	}

	public Utente findByEmail(String email) {
		return ur.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
	}

	public Utente findByEmailAndUpdate(String email, UtentePatchPayload body) {
		Utente utente = this.findByEmail(email);
		if (body.getNome() != null) {
			utente.setNome(body.getNome());
		}
		if (body.getCognome() != null) {
			utente.setCognome(body.getCognome());
		}
		if (body.getDataNascita() != null) {
			utente.setDataNascita(body.getDataNascita());
		}
		if (body.getEmail() != null) {
			utente.setEmail(body.getEmail());
		}

		if (body.getRagioneSociale() != null) {
			utente.setRagioneSociale(body.getRagioneSociale());
		}

		if (body.getPIva() != null) {
			utente.setPIva(body.getPIva());
		}
		return ur.save(utente);
	}

	public void deleteUtente(String email) {
		Utente utente = this.findByEmail(email);
		ur.delete(utente);
	}

	public Utente updateUtente(Utente utente) {
		return ur.save(utente);
	}

	public void invioEmail() throws IOException {

		Email from = new Email("walter90@hotmail.it");
		String subject = "Benvenuto su PepeFactory";
		Email to = new Email("walter90@hotmail.it");
		Content content = new Content("text/plain", "Benvenuto");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("sendgrind_api_key"));
		System.out.println("SENDGRID_API_KEY: " + System.getenv("sendgrind_api_key"));

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

}
