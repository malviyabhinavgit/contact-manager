package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;

import java.util.List;

public interface ContactService {
    Contact save (ContactDTO contact);
    Contact getContact(Long contactId);
    List<Contact> getContacts(List<Long> ids);

}
