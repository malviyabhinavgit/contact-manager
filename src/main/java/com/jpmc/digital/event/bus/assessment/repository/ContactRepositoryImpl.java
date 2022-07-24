package com.jpmc.digital.event.bus.assessment.repository;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.repository.jpa.ContactRepositoryJpa;
import com.jpmc.digital.event.bus.assessment.service.ContactNotFoundException;

import java.util.List;


public class ContactRepositoryImpl implements ContractRepository {

    private final ContactRepositoryJpa contactRepositoryJpa;

    public ContactRepositoryImpl(ContactRepositoryJpa contactRepositoryJpa) {
        this.contactRepositoryJpa = contactRepositoryJpa;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepositoryJpa.save(contact);
    }

    @Override
    public Contact getContact(Long contactId) {
        return contactRepositoryJpa.findById(contactId).orElseThrow(ContactNotFoundException::new);
    }

    @Override
    public List<Contact> getContacts(List<Long> ids) {
        return contactRepositoryJpa.findAllById(ids);
    }
}
