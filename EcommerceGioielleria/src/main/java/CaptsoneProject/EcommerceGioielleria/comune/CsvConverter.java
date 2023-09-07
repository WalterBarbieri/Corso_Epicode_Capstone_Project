package CaptsoneProject.EcommerceGioielleria.comune;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CsvConverter {
	private final ComuneService cs;

	@Autowired
	public CsvConverter(ComuneService cs) {
		super();
		this.cs = cs;
	}

	public void convertCsv(String source) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileReader(source));

		int totalLines = 0;

		while (scanner.hasNextLine()) {
			totalLines++;
			String line = scanner.nextLine();
			line = line.trim();
			String[] parts = line.split(";");

			String denominazione = parts[5];
			String nomeRegione = parts[10];
			if (nomeRegione.startsWith("Valle d'Aosta")) {
				nomeRegione = "Valle d'Aosta/Vallée d'Aoste";
			} else if (nomeRegione.startsWith("Trentino-Alto")) {
				nomeRegione = "Trentino-Alto Adige/Südtirol";
			}
			String nomeProvincia = parts[11];
			if (nomeProvincia.startsWith("Valle d'Aosta")) {
				nomeProvincia = "Valle d'Aosta/Vallée d'Aoste";
			} else if (nomeProvincia.startsWith("Forl")) {
				nomeProvincia = "Forlì-Cesena";
			}
			String siglaProvincia = parts[14];

			Comune comune = new Comune(denominazione, nomeRegione, nomeProvincia, siglaProvincia);
			cs.saveComune(comune);
		}
		System.out.println(totalLines);
		scanner.close();

	}

}
