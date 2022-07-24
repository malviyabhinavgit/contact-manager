package com.jpmc.digital.event.bus.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Collections;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    private final ContactRepositoryImpl contactRepository = Mockito.mock(ContactRepositoryImpl.class);

    private final ContactValidator contactValidator = new ContactValidatorImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ContactService contactService;

    @Test
    void shouldCreateContactWhenCalledWithValidContactDto() throws IOException {
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        ContactDTO validContactDTO = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_DTO_JSON), ContactDTO.class);
        contactService.save(validContactDTO);
        verify(contactRepository, times(1)).save(any());
    }

    @Test
    void shouldGetContactWhenContactExists() throws IOException {
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepository.getContact(TEST_CONTACT_ID)).thenReturn(validContact);
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertEquals(validContact, contactService.getContact(TEST_CONTACT_ID));
    }

    @Test
    void shouldThrowContactNotFoundExceptionWhenContactDoesnotExist() {
        when(contactRepository.getContact(TEST_CONTACT_ID)).thenThrow(ContactNotFoundException.class);
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertThrows(ContactNotFoundException.class, () -> contactService.getContact(TEST_CONTACT_ID));
    }

    @Test
    void shouldGetContactsWhenContactExistsForGivenContactIds() throws IOException {
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepository.getContacts(Collections.singletonList(TEST_CONTACT_ID))).thenReturn(Collections.singletonList(validContact));
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        assertEquals(Collections.singletonList(validContact), contactService.getContacts(Collections.singletonList(TEST_CONTACT_ID)));
    }

    @Test
    void shouldThrowMandatoryFieldNotPresentExceptionWhenCalledWithInvalidContactDto() throws IOException {
        contactService = new ContactServiceImpl(contactRepository, contactValidator);
        ContactDTO invalidContactDTO = objectMapper.readValue(ResourceUtils.getFile(CONTACT_DTO_WITHOUT_FIRST_NAME_JSON), ContactDTO.class);
        assertThrows(MandatoryFieldNotPresentException.class, () -> contactService.save(invalidContactDTO));
    }

}
