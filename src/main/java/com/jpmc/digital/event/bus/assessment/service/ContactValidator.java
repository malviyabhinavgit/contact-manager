package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Contact;

public interface ContactValidator {
    void validate(Contact contactRequest);
}
