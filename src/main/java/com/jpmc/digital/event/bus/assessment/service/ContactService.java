package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Contact;

import java.util.List;

public interface ContactService {
    Contact save (Contact contactRequest);
    Contact getContact(Long contactId);
    List<Contact> getContacts(List<Long> ids);

}
