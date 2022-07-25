package com.jpmc.digital.event.bus.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.dto.Contact;
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

        Contact contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_FIRST_NAME_JSON), Contact.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenLastNameNotPresent() throws IOException {

        Contact contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_LAST_NAME_JSON), Contact.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }


    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenContactDetailNotPresent() throws IOException {
        Contact contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_CONTACT_DETAIL), Contact.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenNoAddressOrMobileNumberPresent() throws IOException {
        Contact contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_MOBILE_ADDRESS_DETAIL), Contact.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(contactRequest));
    }
}
