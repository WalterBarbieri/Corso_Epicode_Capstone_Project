package CaptsoneProject.EcommerceGioielleria.orderItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import CaptsoneProject.EcommerceGioielleria.indirizzo.Indirizzo;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemPayload {
	private Map<UUID, Integer> prodotti = new HashMap<>();
	private Utente utente;
	private Indirizzo indirizzoConsegna;
}
