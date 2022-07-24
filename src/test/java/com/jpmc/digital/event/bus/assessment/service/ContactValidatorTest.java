package com.jpmc.digital.event.bus.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ContactValidatorTest {

    private final ContactValidator contactValidator = new ContactValidatorImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenFirstNameNotPresent() throws IOException {

        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_FIRST_NAME_JSON), ContactRequest.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenMobileNumberNotPresent() throws IOException {

        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_MOBILE_JSON), ContactRequest.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenAddressFirstLineNotPresent() throws IOException {

        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_ADD_FIRST_LINE), ContactRequest.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenPostCodeNotPresent() throws IOException {

        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_POSTCODE), ContactRequest.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

}
