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
			System.out.println(parts[6]);
			System.out.println(parts[11]);
			System.out.println(parts[12]);
			System.out.println(parts[15]);

			String denominazione = parts[6];
			String nomeRegione = parts[11];
			String nomeProvincia = parts[12];
			String siglaProvincia = parts[15];

			Comune comune = new Comune(denominazione, nomeRegione, nomeProvincia, siglaProvincia);
			cs.saveComune(comune);
		}
		System.out.println(totalLines);
		scanner.close();

	}

}
