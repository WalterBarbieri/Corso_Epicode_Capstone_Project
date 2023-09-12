package CaptsoneProject.EcommerceGioielleria.runner;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import CaptsoneProject.EcommerceGioielleria.comune.Comune;
import CaptsoneProject.EcommerceGioielleria.comune.ComuneService;
import CaptsoneProject.EcommerceGioielleria.exceptions.BadRequestException;
import CaptsoneProject.EcommerceGioielleria.indirizzo.IndirizzoPayload;
import CaptsoneProject.EcommerceGioielleria.indirizzo.IndirizzoService;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.UtenteService;
import CaptsoneProject.EcommerceGioielleria.utente.payloads.UtenteRequestPayload;
import jakarta.transaction.Transactional;

@Component
public class RandomInstanceGenerator {
	private final UtenteService us;
	private final IndirizzoService is;
	private final ComuneService cs;
	private final PasswordEncoder bcrypt;
	Faker faker = new Faker();
	Random rnd = new Random();

	@Autowired
	public RandomInstanceGenerator(UtenteService us, IndirizzoService is, PasswordEncoder bcrypt, ComuneService cs) {
		super();
		this.us = us;
		this.is = is;
		this.cs = cs;
		this.bcrypt = bcrypt;
	}

	@Transactional
	public Utente randomUtenteGenerator(int istanze) {
		for (int i = 0; i < istanze; i++) {
			try {
				String nome = faker.name().firstName();
				String congnome = faker.name().lastName();
				int rndYear = rnd.nextInt(60) + 1940;
				int rndMonth = rnd.nextInt(12) + 1;
				int rndDay = rnd.nextInt(28) + 1;
				LocalDate dataNascita = LocalDate.of(rndYear, rndMonth, rndDay);
				String email = faker.internet().emailAddress();
				String password = bcrypt.encode(faker.lorem().characters(6, 15));

				UtenteRequestPayload rndUtente = new UtenteRequestPayload(nome, congnome, dataNascita, email, password);
				Utente utente = us.saveUtente(rndUtente);
				is.saveResidenza(this.randomIndirizzoGenerator(utente));
				is.saveDomicilio(this.randomIndirizzoGenerator(utente));
				int rndNumber = rnd.nextInt(5);
				for (int j = 0; j < rndNumber; j++) {
					is.saveIndirizzi(this.randomIndirizzoGenerator(utente));
				}

			} catch (BadRequestException e) {
				e.printStackTrace();
				System.out.println("Errore durante la creazione dell'utente");
			}
		}
		return null;
	}

	public IndirizzoPayload randomIndirizzoGenerator(Utente utente) {
		String via = faker.address().streetName();
		String civico = faker.address().streetAddress();
		String localita = faker.ancient().god();
		int cap = this.generateRandomNumberSeries(5);
		List<Comune> comuni = cs.findComuni();
		Comune comune = comuni.get(rnd.nextInt(comuni.size()));
		String nomeComune = comune.getDenominazione();
		UUID id = utente.getId();
		IndirizzoPayload rndIndirizzo = new IndirizzoPayload(via, civico, localita, cap, nomeComune, id);
		return rndIndirizzo;
	}

	public int generateRandomNumberSeries(int cifre) {
		int n = cifre;
		int min = (int) Math.pow(10, n - 1);
		int max = (int) Math.pow(10, n) - 1;
		int rndSeries = min + (int) (Math.random() * (max - min + 1));
		return rndSeries;
	}

}
