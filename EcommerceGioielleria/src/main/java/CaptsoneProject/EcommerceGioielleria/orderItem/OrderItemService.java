package CaptsoneProject.EcommerceGioielleria.orderItem;

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
	public void addProduct(UUID orderItemId, Prodotto prodotto, int quantita) {
		OrderItem orderItem = this.findById(orderItemId);
		for (int i = 0; i < quantita; i++) {
			orderItem.getProdotti().add(prodotto);
		}

		oir.save(orderItem);
	}

	@Transactional
	public OrderItem findByUtenteId(UUID id) {
		return oir.findByUtente_Id(id);
	}

}
