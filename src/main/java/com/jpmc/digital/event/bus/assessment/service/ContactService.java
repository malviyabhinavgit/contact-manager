package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import com.jpmc.digital.event.bus.assessment.dto.ContactResponse;

import java.util.List;

public interface ContactService {
    ContactResponse save (ContactRequest contactRequest);
    ContactResponse getContact(Long contactId);
    List<ContactResponse> getContacts(List<Long> ids);

}
