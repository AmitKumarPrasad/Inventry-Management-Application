package com.springboot.inventry.restapi.service;

import java.util.List;

import com.springboot.inventry.restapi.model.Invoice;

public interface IInvoiceService {

	Long saveInvoice(Invoice invoice);

	void updateInvoice(Invoice invoice);

	void deleteInvoice(Long id);

	Invoice getOneInvoice(Long id);

	List<Invoice> getAllInvoices();

	boolean isInvoiceExits(Long id);

	Integer updateInvoiceNumberById(String number, Long id);

}
