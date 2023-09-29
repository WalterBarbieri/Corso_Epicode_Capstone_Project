package CaptsoneProject.EcommerceGioielleria.orderItem;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.gioiello.GioielloService;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

	private final OrderItemService ois;
	private final GioielloService gs;

	@Autowired
	public OrderItemController(OrderItemService ois, GioielloService gs) {
		super();
		this.ois = ois;
		this.gs = gs;
	}

	@GetMapping("/{orderItemId}")
	public ResponseEntity<OrderItem> findOrderItem(@PathVariable UUID orderItemId) {
		OrderItem orderItem = ois.findById(orderItemId);
		if (orderItem != null) {
			return new ResponseEntity<>(orderItem, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<OrderItem> findOrderItemByUtenteId(@RequestParam UUID id) {
		OrderItem orderItem = ois.findByUtenteId(id);
		if (orderItem != null) {
			return new ResponseEntity<>(orderItem, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<OrderItem> saveOrderItem(@RequestParam UUID id) {
		OrderItem orderItem = ois.saveOrderItem(id);
		return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
	}

	@PostMapping("/{orderItemId}/add-product")
	public ResponseEntity<OrderItem> addProduct(@PathVariable UUID orderItemId, @RequestParam UUID id,
			@RequestParam int quantita) {
		Prodotto prodotto = gs.findById(id);
		if (prodotto != null) {
			ois.addProduct(orderItemId, prodotto, quantita);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
