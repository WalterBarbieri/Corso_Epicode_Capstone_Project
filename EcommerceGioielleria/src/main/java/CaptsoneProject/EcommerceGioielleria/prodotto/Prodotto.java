package CaptsoneProject.EcommerceGioielleria.prodotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.prodotto.immagini.Immagine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prodotti")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@Setter
@NoArgsConstructor
public abstract class Prodotto {
	@Id
	@GeneratedValue
	private UUID id;
	private String nomeProdotto;
	@Column(length = 2000)
	private String descrizione;
	private double price;
	private int quantita;
	private LocalDateTime dataInserimento;
	@OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Immagine> immagini = new ArrayList<>();

	public Prodotto(String nomeProdotto, String descrizione, double price, int quantita, LocalDateTime dataInserimento,
			List<Immagine> immagini) {
		super();
		this.nomeProdotto = nomeProdotto;
		this.descrizione = descrizione;
		this.price = price;
		this.quantita = quantita;

		this.dataInserimento = dataInserimento;
		this.immagini = immagini;
	}

}
