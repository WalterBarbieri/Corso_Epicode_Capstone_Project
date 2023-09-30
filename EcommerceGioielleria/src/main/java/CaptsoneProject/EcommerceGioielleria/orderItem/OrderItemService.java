package CaptsoneProject.EcommerceGioielleria.orderItem;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.UtenteService;
import jakarta.transaction.Transactional;

@Service
public class OrderItemService {
	private final OrderItemRepository oir;
	private final UtenteService us;

	@Autowired
	public OrderItemService(OrderItemRepository or, UtenteService us) {
		super();
		this.oir = or;
		this.us = us;
	}

	@Transactional
	public OrderItem saveOrderItem(UUID id) {
		Utente utente = us.findById(id);
		OrderItem orderItem = new OrderItem(utente);
		return oir.save(orderItem);
	}

	@Transactional
	public OrderItem findById(UUID orderItemId) {
		return oir.findById(orderItemId).orElseThrow(() -> new NotFoundException(orderItemId));
	}

	@Transactional
	public OrderItem findByUtenteId(UUID id) {
		return oir.findByUtente_Id(id);
	}

	@Transactional
	public void addProduct(UUID orderItemId, Prodotto prodotto, int quantita) {
		if (prodotto.getQuantita() >= quantita) {
			OrderItem orderItem = this.findById(orderItemId);
			Map<UUID, Integer> prodottiQuantita = orderItem.getProdotti();

			if (prodottiQuantita.containsKey(prodotto.getId())) {
				prodottiQuantita.put(prodotto.getId(), prodottiQuantita.get(prodotto.getId()) + quantita);
			} else {
				prodottiQuantita.put(prodotto.getId(), quantita);
			}

			oir.save(orderItem);
		} else {
			System.out.println("Quantità richiesta superiore a quella in magazzino");

		}

	}

	@Transactional
	public void increaseProduct(UUID orderItemId, Prodotto prodotto) {

		OrderItem orderItem = this.findById(orderItemId);
		Map<UUID, Integer> prodottiQuantita = orderItem.getProdotti();
		int differenza = prodotto.getQuantita() - prodottiQuantita.get(prodotto.getId());

		if (prodottiQuantita.containsKey(prodotto.getId()) && differenza > 0) {
			prodottiQuantita.put(prodotto.getId(), prodottiQuantita.get(prodotto.getId()) + 1);
			oir.save(orderItem);
		} else {
			System.out.println("Quantità richiesta superiore a quella in magazzino");
		}

	}

	@Transactional
	public void decreaseProduct(UUID orderItemId, Prodotto prodotto) {

		OrderItem orderItem = this.findById(orderItemId);
		Map<UUID, Integer> prodottiQuantita = orderItem.getProdotti();

		if (prodottiQuantita.containsKey(prodotto.getId()) && prodottiQuantita.get(prodotto.getId()) > 1) {
			prodottiQuantita.put(prodotto.getId(), prodottiQuantita.get(prodotto.getId()) - 1);
			oir.save(orderItem);
		} else {
			System.out.println("Non puoi scendere sotto 1");
		}

	}

	@Transactional
	public void deleteProduct(UUID orderItemId, Prodotto prodotto) {
		OrderItem orderItem = this.findById(orderItemId);
		Map<UUID, Integer> prodottiQuantita = orderItem.getProdotti();
		prodottiQuantita.remove(prodotto.getId());
		oir.save(orderItem);
	}

	@Transactional
	public void deleteOrderItem(UUID orderItemId) {
		OrderItem orderItem = this.findById(orderItemId);
		oir.delete(orderItem);
	}
}
