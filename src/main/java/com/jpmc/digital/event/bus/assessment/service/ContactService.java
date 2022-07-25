package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequestResponse;

import java.util.List;

public interface ContactService {
    ContactRequestResponse save (ContactRequestResponse contactRequest);
    ContactRequestResponse getContact(Long contactId);
    List<ContactRequestResponse> getContacts(List<Long> ids);

}
