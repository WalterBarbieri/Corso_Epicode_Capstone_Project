package CaptsoneProject.EcommerceGioielleria.comune;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comuni")
@Getter
@Setter
public class Comune {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String denominazione;
	private String nomeRegione;
	private String nomeProvincia;
	private String siglaProvincia;

	public Comune(String denominazione, String nomeRegione, String nomeProvincia, String siglaProvincia) {
		super();
		this.denominazione = denominazione;
		this.nomeRegione = nomeRegione;
		this.nomeProvincia = nomeProvincia;
		this.siglaProvincia = siglaProvincia;
	}

}
