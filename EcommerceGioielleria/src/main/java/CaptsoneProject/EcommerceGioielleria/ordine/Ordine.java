package CaptsoneProject.EcommerceGioielleria.ordine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.indirizzo.Indirizzo;
import CaptsoneProject.EcommerceGioielleria.orderItem.OrderItemPayload;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ordine {
	@Id
	@GeneratedValue
	private UUID ordineId;

	@ElementCollection
	@CollectionTable(name = "ordine_prodotti", joinColumns = @JoinColumn(name = "ordine_id"))
	@MapKeyColumn(name = "prodotto_id")
	@Column(name = "quantita")
	private Map<UUID, Integer> prodotti = new HashMap<>();

	@ManyToOne
	private Utente utente;

	@ManyToOne
	private Indirizzo indirizzoConsegna;

	private LocalDate dataOrdine;

	private double importo;

	private double iva;

	private double totale;
	@Enumerated(EnumType.STRING)
	private StatoOrdine statoOrdine;

	public Ordine(OrderItemPayload orderItemPayload, double importo, double iva, double totale) {
		this.prodotti = orderItemPayload.getProdotti();
		this.utente = orderItemPayload.getUtente();
		this.indirizzoConsegna = orderItemPayload.getIndirizzoConsegna();
		this.dataOrdine = LocalDate.now();
		this.importo = importo;
		this.iva = iva;
		this.totale = totale;
		this.statoOrdine = StatoOrdine.PENDENTE;
	}

}
