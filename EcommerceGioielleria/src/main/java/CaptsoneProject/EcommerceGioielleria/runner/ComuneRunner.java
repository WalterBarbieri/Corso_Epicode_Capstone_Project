package CaptsoneProject.EcommerceGioielleria.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import CaptsoneProject.EcommerceGioielleria.comune.CsvConverter;

@Component
public class ComuneRunner implements CommandLineRunner {
	@Autowired
	private CsvConverter converter;
	@Autowired
	private RandomInstanceGenerator generator;

	@Override
	public void run(String... args) throws Exception {

//		converter.convertCsv("Elenco-comuni-italiani.csv");

//		generator.randomUtenteGenerator(10);

	}

}
