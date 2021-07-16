package com.springboot.inventry.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.inventry.restapi.exception.InvoiceNotFoundException;
import com.springboot.inventry.restapi.model.Invoice;
import com.springboot.inventry.restapi.repository.InvoiceRepositroy;
import com.springboot.inventry.restapi.util.InvoiceUtil;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private InvoiceRepositroy repo;

	@Autowired
	private InvoiceUtil util;

	@Override
	public Long saveInvoice(Invoice invoice) {
		util.CalculateFinalAmountIncludingGST(invoice);
		Long id = repo.save(invoice).getId();
		return id;
	}

	@Override
	public void updateInvoice(Invoice invoice) {
		util.CalculateFinalAmountIncludingGST(invoice);
		repo.save(invoice);

	}

	@Override
	public void deleteInvoice(Long id) {
		Invoice inv = getOneInvoice(id);
		repo.delete(inv);

	}

	public Optional<Invoice> getSingleInvoice(Long id) {
		return repo.findById(id);
	}

	@Override
	public Invoice getOneInvoice(Long id) {
		Invoice inv=repo.findById(id)
				.orElseThrow(()-> new InvoiceNotFoundException(
					new StringBuffer().append("Product  '")
					.append(id)
					.append("'not exits")
					.toString())
				);
		return inv;
	}

	@Override
	public List<Invoice> getAllInvoices() {
		List<Invoice> list = repo.findAll();
		list.sort((ob1, ob2) -> ob1.getId().intValue() - ob2.getId().intValue());
		return list;
	}

	@Override
	public boolean isInvoiceExits(Long id) {

		return repo.existsById(id);
	}

	@Override
	public Integer updateInvoiceNumberById(String number, Long id) {
		if (!repo.existsById(id)) {
			throw new InvoiceNotFoundException(
					new StringBuffer().append("Invoice '").append(id).append("' not exits").toString());
		}
		return repo.updateInvoiceNumberById(number, id);
	}

}
