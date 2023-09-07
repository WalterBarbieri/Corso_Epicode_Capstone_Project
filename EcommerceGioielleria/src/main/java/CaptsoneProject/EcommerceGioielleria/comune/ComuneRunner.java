package CaptsoneProject.EcommerceGioielleria.comune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ComuneRunner implements CommandLineRunner {
	@Autowired
	private CsvConverter converter;

	@Override
	public void run(String... args) throws Exception {

//		converter.convertCsv("Elenco-comuni-italiani.csv");

	}

}
