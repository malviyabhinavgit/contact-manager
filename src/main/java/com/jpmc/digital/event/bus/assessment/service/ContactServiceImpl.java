package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;

import java.util.List;

public class ContactServiceImpl implements ContactService {

    private final ContactRepositoryImpl contactRepository;

    private final ContactValidator contactValidator;

    public ContactServiceImpl(ContactRepositoryImpl contactRepository, ContactValidator contactValidator) {
        this.contactRepository = contactRepository;
        this.contactValidator = contactValidator;
    }

    @Override
    public Contact save(ContactDTO contactDTO) {
        return contactRepository.save(initializeContactFromContactDTO(contactDTO));
    }

    @Override
    public Contact getContact(Long contactId) {
        return contactRepository.getContact(contactId);
    }

    @Override
    public List<Contact> getContacts(List<Long> ids) {
        return contactRepository.getContacts(ids);
    }

    private Contact initializeContactFromContactDTO(ContactDTO contactDTO) {

        contactValidator.validate(contactDTO);

        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setContactDetail(contactDTO.getContactDetail());
        return contact;
    }
}
