package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;

public interface ContactValidator {
    void validate(ContactDTO contactDTO);
}
