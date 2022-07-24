package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;

public interface ContactValidator {
    void validate(ContactRequest contactRequest);
}
