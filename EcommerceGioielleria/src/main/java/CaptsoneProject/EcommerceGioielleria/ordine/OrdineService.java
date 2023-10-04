package CaptsoneProject.EcommerceGioielleria.ordine;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.orderItem.OrderItemPayload;
import jakarta.transaction.Transactional;

@Service
public class OrdineService {
	private final OrdineRepository or;

	@Autowired
	public OrdineService(OrdineRepository or) {
		super();
		this.or = or;
	}

	@Transactional
	public Ordine saveOrdine(OrderItemPayload orderItemPayload, double importo, double iva, double totale) {
		Ordine ordine = new Ordine(orderItemPayload, importo, iva, totale);
		return or.save(ordine);
	}

	@Transactional
	public Page<Ordine> findAllOrdini(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return or.findAll(pageable);
	}

	@Transactional
	public Ordine findById(UUID ordineId) {
		return or.findById(ordineId)
				.orElseThrow(() -> new NotFoundException("Ordine con id: " + ordineId + " non trovato!"));
	}

	@Transactional
	public Page<Ordine> findByUtente(UUID id, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return or.findByUtente_Id(id, pageable);
	}

	@Transactional
	public Ordine cambiaStatoOrdine(UUID ordineId, StatoOrdine statoOrdine) {
		Ordine ordine = this.findById(ordineId);
		ordine.setStatoOrdine(statoOrdine);
		return or.save(ordine);

	}

	@Transactional
	public void eliminaOrdine(UUID ordineId) {
		Ordine ordine = this.findById(ordineId);
		or.delete(ordine);
	}
}
