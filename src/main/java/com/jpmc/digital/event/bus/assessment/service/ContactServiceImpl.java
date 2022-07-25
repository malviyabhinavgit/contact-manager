package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Address;
import com.jpmc.digital.event.bus.assessment.dto.ContactDetail;
import com.jpmc.digital.event.bus.assessment.dto.Contact;
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
    public Contact save(Contact contactRequest) {
        return convertEntityToDto(contactRepository.save(convertDtoToEntity(contactRequest)));
    }

    @Override
    public Contact getContact(Long contactId) {
        return convertEntityToDto(contactRepository.getContact(contactId));
    }

    @Override
    public List<Contact> getContacts(List<Long> ids) {
        return contactRepository.getContacts(ids).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private com.jpmc.digital.event.bus.assessment.entity.Contact convertDtoToEntity(Contact contactRequest) {

        contactValidator.validate(contactRequest);

        com.jpmc.digital.event.bus.assessment.entity.Contact contact = new com.jpmc.digital.event.bus.assessment.entity.Contact();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        populateContactWithAvailableContactDetails(contactRequest, contact);
        return contact;
    }

    private void populateContactWithAvailableContactDetails(Contact contactRequest, com.jpmc.digital.event.bus.assessment.entity.Contact contact) {
        com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail = new com.jpmc.digital.event.bus.assessment.entity.ContactDetail();
        populateAddressForContactIfPresent(contactRequest, contactDetail);
        contactDetail.setMobileNumber(Collections.unmodifiableList(contactRequest.getContactDetail().getMobileNumber()));
        contact.setContactDetail(contactDetail);
    }

    private void populateAddressForContactIfPresent(Contact contactRequest, com.jpmc.digital.event.bus.assessment.entity.ContactDetail contactDetail) {

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

    private Contact convertEntityToDto(com.jpmc.digital.event.bus.assessment.entity.Contact contact) {
        Contact contactResponse = new Contact();
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setLastName(contact.getLastName());
        populateContactRespWithAvailableContactDetails(contact, contactResponse);
        return contactResponse;
    }

    private void populateContactRespWithAvailableContactDetails(com.jpmc.digital.event.bus.assessment.entity.Contact contact, Contact contactResponse) {
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
