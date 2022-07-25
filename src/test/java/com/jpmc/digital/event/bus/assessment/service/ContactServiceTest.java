package com.jpmc.digital.event.bus.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import com.jpmc.digital.event.bus.assessment.dto.ContactResponse;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Collections;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ContactServiceTest {

    private final ContactRepositoryImpl contactRepository = Mockito.mock(ContactRepositoryImpl.class);

    private final ContactValidator contactValidator = new ContactValidatorImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ContactService contactService;

    @Test
    void shouldCreateContactWhenCalledWithValidContactReq() throws IOException {
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        ContactResponse contactResponse = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactResponse.class);
        when(contactRepository.save(any(Contact.class))).thenReturn(validContact);
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_REQ_JSON), ContactRequest.class);
        assertEquals(contactResponse, contactService.save(contactRequest));
    }

    @Test
    void shouldGetContactWhenContactExists() throws IOException {
        ContactResponse contactResponse = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactResponse.class);
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepository.getContact(TEST_CONTACT_ID)).thenReturn(validContact);
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertEquals(contactResponse, contactService.getContact(TEST_CONTACT_ID));
    }

    @Test
    void shouldThrowContactNotFoundExceptionWhenContactDoesnotExist() {
        when(contactRepository.getContact(TEST_CONTACT_ID)).thenThrow(ContactNotFoundException.class);
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertThrows(ContactNotFoundException.class, () -> contactService.getContact(TEST_CONTACT_ID));
    }

    @Test
    void shouldGetContactsWhenContactExistsForGivenContactIds() throws IOException {
        ContactResponse contactResponse = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactResponse.class);
        Contact contact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepository.getContacts(Collections.singletonList(TEST_CONTACT_ID))).thenReturn(Collections.singletonList(contact));
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertEquals(Collections.singletonList(contactResponse), contactService.getContacts(Collections.singletonList(TEST_CONTACT_ID)));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenCalledWithInvalidContactReq() throws IOException {
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        ContactRequest contactRequest = objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_FIRST_NAME_JSON), ContactRequest.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactService.save(contactRequest));
    }

}
