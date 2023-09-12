package CaptsoneProject.EcommerceGioielleria.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorsPayload handleBadRequest(BadRequestException e) {
		return new ErrorsPayload(e.getMessage(), LocalDate.now());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ErrorsPayload handleIllegalArgument(IllegalArgumentException e) {
		return new ErrorsPayload(e.getMessage(), LocalDate.now());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsPayload handleNotFound(NotFoundException e) {
		return new ErrorsPayload(e.getMessage(), LocalDate.now());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorsPayload handleUnauthorized(UnauthorizedException e) {
		return new ErrorsPayload(e.getMessage(), LocalDate.now());
	}

}
