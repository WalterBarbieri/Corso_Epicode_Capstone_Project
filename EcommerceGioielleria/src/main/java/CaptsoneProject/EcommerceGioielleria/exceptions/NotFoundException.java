package CaptsoneProject.EcommerceGioielleria.exceptions;

import java.util.UUID;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message + " non trovato");
	}

	public NotFoundException(UUID id) {
		super(id + " non trovato");
	}
}
