package com.springboot.inventry.restapi.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.inventry.restapi.exception.InvoiceNotFoundException;
import com.springboot.inventry.restapi.model.ErrorType;

@RestControllerAdvice
public class InvoiceErrorHandler {

	@ExceptionHandler(InvoiceNotFoundException.class)
	public ResponseEntity<ErrorType> handleNotFound(InvoiceNotFoundException nfe) {

		return new ResponseEntity<ErrorType>(
				new ErrorType(new Date(System.currentTimeMillis()).toString(), "404- NOT FOUND", nfe.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
