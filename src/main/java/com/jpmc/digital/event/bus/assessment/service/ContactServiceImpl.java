package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactValidator contactValidator;

    public ContactServiceImpl(ContactRepository contactRepository, ContactValidator contactValidator) {
        this.contactRepository = contactRepository;
        this.contactValidator = contactValidator;
    }
    @Override
    public Contact save(ContactDTO contactDTO) {
        return contactRepository.save(initializeContactFromContactDTO(contactDTO));
    }

    @Override
    public Contact getContact(Long contactId) {
        return contactRepository.findById(contactId).orElseThrow(ContactNotFoundException::new);
    }

    @Override
    public List<Contact> getContacts(List<Long> ids) {
        return contactRepository.findAll().stream().filter(contact -> ids.contains(contact.getId())).collect(Collectors.toList());
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
