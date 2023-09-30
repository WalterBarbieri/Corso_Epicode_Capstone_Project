package CaptsoneProject.EcommerceGioielleria.orderItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue
	private UUID orderItemId;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "order_item_products", joinColumns = @JoinColumn(name = "order_item_id"))
	@MapKeyJoinColumn(name = "prodotto_id")
	@Column(name = "quantita")
	private Map<UUID, Integer> prodotti = new HashMap<>();
	@OneToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;

	public OrderItem(Utente utente) {
		super();
		this.utente = utente;
	}

}
