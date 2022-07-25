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

        if(contact ==null){
            throw new ContactNotFoundException();
        }

        com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail = contact.getContactDetail();
        /*checks are in place to stop populating contact without contactDetail. This is an additional check to avoid NPE
        in case someone inserts bad data in database. */
        if(contactDetail ==null){
            contactDetail = new com.jpmc.digital.event.bus.assessment.entity.ContactDetail();
        }

        com.jpmc.digital.event.bus.assessment.entity.Address address = contactDetail.getAddress();
        /*checks are in place to stop populating contact without Address. This is an additional check to avoid NPE
        in case someone inserts bad data in database. */

        if(address ==null){
            address = new com.jpmc.digital.event.bus.assessment.entity.Address();
        }

        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setLastName(contact.getLastName());

        ContactDetail contactDetailResp = new ContactDetail();
        Address addressResp = new Address();

        addressResp.setFirstLineOfAddress(address.getFirstLineOfAddress());
        addressResp.setLastLineOfAddress(address.getLastLineOfAddress());
        addressResp.setCity(address.getCity());
        addressResp.setCountry(address.getCountry());
        addressResp.setPostcode(address.getPostcode());

        contactDetailResp.setAddress(addressResp);
        contactDetailResp.setMobileNumber(Collections.unmodifiableList(contactDetail.getMobileNumber()));

        contactResponse.setContactDetail(contactDetailResp);
        return contactResponse;
    }
}
