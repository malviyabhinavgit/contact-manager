package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Address;
import com.jpmc.digital.event.bus.assessment.dto.ContactDetail;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequestResponse;
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
    public ContactRequestResponse save(ContactRequestResponse contactRequest) {
        return contactResponse(contactRepository.save(contact(contactRequest)));
    }

    @Override
    public ContactRequestResponse getContact(Long contactId) {
        return contactResponse(contactRepository.getContact(contactId));
    }

    @Override
    public List<ContactRequestResponse> getContacts(List<Long> ids) {
        return contactRepository.getContacts(ids).stream().map(this::contactResponse).collect(Collectors.toList());
    }

    private Contact contact(ContactRequestResponse contactRequest) {

        contactValidator.validate(contactRequest);

        Contact contact = new Contact();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        populateContactWithAvailableContactDetails(contactRequest, contact);
        return contact;
    }

    private void populateContactWithAvailableContactDetails(ContactRequestResponse contactRequest, Contact contact) {
        com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail = new com.jpmc.digital.event.bus.assessment.entity.ContactDetail();
        populateAddressForContactIfPresent(contactRequest, contactDetail);
        contactDetail.setMobileNumber(Collections.unmodifiableList(contactRequest.getContactDetail().getMobileNumber()));
        contact.setContactDetail(contactDetail);
    }

    private void populateAddressForContactIfPresent(ContactRequestResponse contactRequest, com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail) {

        Address addressFromReq = contactRequest.getContactDetail().getAddress();
        if (addressFromReq != null) {
            com.jpmc.digital.event.bus.assessment.entity.Address address = new com.jpmc.digital.event.bus.assessment.entity.Address();
            address.setFirstLineOfAddress(addressFromReq.getFirstLineOfAddress());
            address.setLastLineOfAddress(addressFromReq.getLastLineOfAddress());
            address.setCity(addressFromReq.getCity());
            address.setCountry(addressFromReq.getCountry());
            address.setPostcode(addressFromReq.getPostcode());
            contactDetail.setAddress(address);
        }
    }

    private ContactRequestResponse contactResponse(Contact contact) {
        ContactRequestResponse contactResponse = new ContactRequestResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setLastName(contact.getLastName());
        populateContactRespWithAvailableContactDetails(contact, contactResponse);
        return contactResponse;
    }

    private void populateContactRespWithAvailableContactDetails(Contact contact, ContactRequestResponse contactResponse) {
        com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail = contact.getContactDetail();

        if (contactDetail != null) {
            ContactDetail contactDetailResp = new ContactDetail();
            com.jpmc.digital.event.bus.assessment.entity.Address address = contactDetail.getAddress();
            populateContactDetailRespWithAvailableAddressDetails(contactDetailResp, address);
            contactDetailResp.setMobileNumber(Collections.unmodifiableList(contactDetail.getMobileNumber()));
            contactResponse.setContactDetail(contactDetailResp);

        }
    }

    private void populateContactDetailRespWithAvailableAddressDetails(ContactDetail contactDetailResp, com.jpmc.digital.event.bus.assessment.entity.Address address) {
        if (address != null) {
            Address addressResp = new Address();
            addressResp.setFirstLineOfAddress(address.getFirstLineOfAddress());
            addressResp.setLastLineOfAddress(address.getLastLineOfAddress());
            addressResp.setCity(address.getCity());
            addressResp.setCountry(address.getCountry());
            addressResp.setPostcode(address.getPostcode());
            contactDetailResp.setAddress(addressResp);
        }
    }
}
