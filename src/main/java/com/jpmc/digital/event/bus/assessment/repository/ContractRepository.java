package com.jpmc.digital.event.bus.assessment.repository;

import com.jpmc.digital.event.bus.assessment.entity.Contact;

import java.util.List;

public interface ContractRepository {
    Contact save(Contact contact);

    Contact getContact(Long contactId);

    List<Contact> getContacts(List<Long> ids);
}
