package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequestResponse;

public interface ContactValidator {
    void validate(ContactRequestResponse contactRequest);
}
