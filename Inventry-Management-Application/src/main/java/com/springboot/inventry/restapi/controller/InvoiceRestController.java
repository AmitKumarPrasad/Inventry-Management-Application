package com.springboot.inventry.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.inventry.restapi.exception.InvoiceNotFoundException;
import com.springboot.inventry.restapi.model.Invoice;
import com.springboot.inventry.restapi.service.IInvoiceService;
import com.springboot.inventry.restapi.util.InvoiceUtil;

@RestController
@RequestMapping("/invoice/rest")
public class InvoiceRestController {

	@Autowired
	private IInvoiceService service;

	@Autowired
	private InvoiceUtil util;

	@PostMapping("/saveInvoice")
	public ResponseEntity<String> saveInvoice(@RequestBody Invoice inv) {
		ResponseEntity<String> resp = null;
		try {
			Long id = service.saveInvoice(inv);
			resp = new ResponseEntity<String>("Invoice '" + id + "'created", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to save Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@GetMapping("/getAllInvoices")
	public ResponseEntity<?> getAllInvoices() {
		ResponseEntity<?> resp = null;
		try {
			List<Invoice> list = service.getAllInvoices();
			resp = new ResponseEntity<List<Invoice>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to get Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> getOneInvoice(@PathVariable Long id) {
		ResponseEntity<?> resp = null;
		try {
			Invoice inv = service.getOneInvoice(id);
			resp = new ResponseEntity<Invoice>(inv, HttpStatus.OK);
		} catch (InvoiceNotFoundException nfe) {
			throw nfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to find Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
		ResponseEntity<String> resp = null;
		try {
			service.deleteInvoice(id);
			resp = new ResponseEntity<String>("Invoice '" + id + "' deleted", HttpStatus.OK);
		} catch (InvoiceNotFoundException nfe) {
			throw nfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to delete Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<String> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
		ResponseEntity<String> resp = null;
		try {
			Invoice inv = service.getOneInvoice(id);
			util.copyNonNullValues(invoice, inv);
			service.updateInvoice(inv);
			resp = new ResponseEntity<String>(HttpStatus.RESET_CONTENT);
		} catch (InvoiceNotFoundException inf) {
			throw inf;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to update Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	@PatchMapping("/modify/{id}/number")
	public ResponseEntity<String> updateInvoiceNumberById(@PathVariable Long id, @PathVariable String number) {
		ResponseEntity<String> resp = null;
		try {
			service.updateInvoiceNumberById(number, id);
			resp = new ResponseEntity<String>("Invoice'" + number + "' Updated", HttpStatus.PARTIAL_CONTENT);
		} catch (InvoiceNotFoundException inf) {
			throw inf;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to update Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;

	}

}
