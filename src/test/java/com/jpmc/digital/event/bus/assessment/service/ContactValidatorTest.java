package com.jpmc.digital.event.bus.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ContactValidatorTest {

    private final ContactValidator contactValidator = new ContactValidatorImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenFirstNameNotPresent() throws IOException {

        ContactDTO invalidContactDTO = objectMapper.readValue(new File(CONTACT_DTO_WITHOUT_FIRST_NAME_JSON), ContactDTO.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(invalidContactDTO));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenMobileNumberNotPresent() throws IOException {

        ContactDTO invalidContactDTO = objectMapper.readValue(new File(CONTACT_DTO_WITHOUT_MOBILE_JSON), ContactDTO.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(invalidContactDTO));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenAddressFirstLineNotPresent() throws IOException {

        ContactDTO invalidContactDTO = objectMapper.readValue(new File(CONTACT_DTO_WITHOUT_ADD_FIRST_LINE), ContactDTO.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(invalidContactDTO));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenPostCodeNotPresent() throws IOException {

        ContactDTO invalidContactDTO = objectMapper.readValue(new File(CONTACT_DTO_WITHOUT_POSTCODE), ContactDTO.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactValidator.validate(invalidContactDTO));
    }

}
