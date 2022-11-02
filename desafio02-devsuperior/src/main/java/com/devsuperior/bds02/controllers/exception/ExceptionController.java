package com.devsuperior.bds02.controllers.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds02.service.exception.DataBaseException;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<DefaultExceptionDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException e,
			HttpServletRequest request) {
		return new ResponseEntity<DefaultExceptionDTO>(factoryException(e, HttpStatus.NOT_FOUND, request),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = DataBaseException.class)
	public ResponseEntity<DefaultExceptionDTO> dataBaseExceptionHandler(DataBaseException e,
			HttpServletRequest request) {
		return new ResponseEntity<DefaultExceptionDTO>(factoryException(e, HttpStatus.BAD_REQUEST, request),
				HttpStatus.BAD_REQUEST);
	}

	private DefaultExceptionDTO factoryException(Exception e, HttpStatus status, HttpServletRequest request) {
		DefaultExceptionDTO dto = new DefaultExceptionDTO();

		dto.setError(e.getClass().getSimpleName().toString());
		dto.setPath(request.getRequestURL().toString());
		dto.setStatus(status.value());
		dto.setMessage(e.getMessage());
		dto.setTimestamp(Instant.now());
		return dto;
	}

}
