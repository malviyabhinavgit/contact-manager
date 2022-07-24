package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Address;
import com.jpmc.digital.event.bus.assessment.dto.ContactDetail;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import com.jpmc.digital.event.bus.assessment.dto.ContactResponse;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContactServiceImpl implements ContactService {

    private final ContactRepositoryImpl contactRepository;

    private final ContactValidator contactValidator;

    public ContactServiceImpl(ContactRepositoryImpl contactRepository, ContactValidator contactValidator) {
        this.contactRepository = contactRepository;
        this.contactValidator = contactValidator;
    }

    @Override
    public ContactResponse save(ContactRequest contactRequest) {
        return contactResponse(contactRepository.save(initializeContactFromContactRequest(contactRequest)));
    }

    @Override
    public ContactResponse getContact(Long contactId) {
        return contactResponse(contactRepository.getContact(contactId));
    }

    @Override
    public List<ContactResponse> getContacts(List<Long> ids) {
        return contactRepository.getContacts(ids).stream().map(this::contactResponse).collect(Collectors.toList());
    }

    private Contact initializeContactFromContactRequest(ContactRequest contactRequest) {

        contactValidator.validate(contactRequest);

        Contact contact = new Contact();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail = new com.jpmc.digital.event.bus.assessment.entity.ContactDetail();
        com.jpmc.digital.event.bus.assessment.entity.Address address = new com.jpmc.digital.event.bus.assessment.entity.Address();
        address.setFirstLineOfAddress(contactRequest.getContactDetail().getAddress().getFirstLineOfAddress());
        address.setLastLineOfAddress(contactRequest.getContactDetail().getAddress().getLastLineOfAddress());
        address.setCity(contactRequest.getContactDetail().getAddress().getCity());
        address.setCountry(contactRequest.getContactDetail().getAddress().getCountry());
        address.setPostcode(contactRequest.getContactDetail().getAddress().getPostcode());
        contactDetail.setAddress(address);
        contactDetail.setMobileNumber(Collections.unmodifiableList(contactRequest.getContactDetail().getMobileNumber()));
        contact.setContactDetail(contactDetail);
        return contact;
    }

    private ContactResponse contactResponse(Contact contact) {

        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setLastName(contact.getLastName());
        ContactDetail contactDetail = new ContactDetail();
        Address address = new Address();
        address.setFirstLineOfAddress(contact.getContactDetail().getAddress().getFirstLineOfAddress());
        address.setLastLineOfAddress(contact.getContactDetail().getAddress().getLastLineOfAddress());
        address.setCity(contact.getContactDetail().getAddress().getCity());
        address.setCountry(contact.getContactDetail().getAddress().getCountry());
        address.setPostcode(contact.getContactDetail().getAddress().getPostcode());
        contactDetail.setAddress(address);
        contactDetail.setMobileNumber(Collections.unmodifiableList(contact.getContactDetail().getMobileNumber()));
        contactResponse.setContactDetail(contactDetail);
        return contactResponse;
    }
}
