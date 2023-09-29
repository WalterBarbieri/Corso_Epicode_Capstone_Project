package CaptsoneProject.EcommerceGioielleria.orderItem;

import java.util.List;
import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Prodotto> prodotti;
	@OneToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;

	public OrderItem(Utente utente) {
		super();
		this.utente = utente;
	}

}
