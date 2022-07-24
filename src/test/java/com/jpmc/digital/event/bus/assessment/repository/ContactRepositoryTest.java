package com.jpmc.digital.event.bus.assessment.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.repository.jpa.ContactRepositoryJpa;
import com.jpmc.digital.event.bus.assessment.service.ContactNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.TEST_CONTACT_ID;
import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.VALID_CONTACT_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContactRepositoryTest {
    private final ContactRepositoryJpa contactRepositoryJpa = Mockito.mock(ContactRepositoryJpa.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateContactWhenSavedWithValidContactReq() throws IOException {
        Contact contact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepositoryJpa.save(contact)).thenReturn(contact);
        assertEquals(contact, new ContactRepositoryImpl(contactRepositoryJpa).save(contact));
        verify(contactRepositoryJpa, times(1)).save(contact);
    }

    @Test
    void shouldGetContactWhenContactExists() throws IOException {
        Contact contact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepositoryJpa.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(contact));
        assertEquals(contact, new ContactRepositoryImpl(contactRepositoryJpa).getContact(TEST_CONTACT_ID));
        verify(contactRepositoryJpa, times(1)).findById(TEST_CONTACT_ID);
    }

    @Test
    void shouldThrowContactNotFoundExceptionWhenContactDoesNotExist() {
        when(contactRepositoryJpa.findById(TEST_CONTACT_ID)).thenReturn(Optional.empty());
        ContactRepositoryImpl contactRepository = new ContactRepositoryImpl(contactRepositoryJpa);
        assertThrows(ContactNotFoundException.class, () -> contactRepository.getContact(TEST_CONTACT_ID));
        verify(contactRepositoryJpa, times(1)).findById(TEST_CONTACT_ID);
    }

    @Test
    void shouldGetContactsWhenContactExistsForGivenContactIds() throws IOException {
        Contact contact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactRepositoryJpa.findAllById(Collections.singletonList(TEST_CONTACT_ID))).thenReturn(Collections.singletonList(contact));
        assertEquals(Collections.singletonList(contact), new ContactRepositoryImpl(contactRepositoryJpa).getContacts(Collections.singletonList(TEST_CONTACT_ID)));
        verify(contactRepositoryJpa, times(1)).findAllById(Collections.singletonList(TEST_CONTACT_ID));
    }

    @Test
    void shouldGetEmptyListWhenContactDoesNotExistForGivenContactIds() {
        when(contactRepositoryJpa.findAllById(Collections.singletonList(TEST_CONTACT_ID))).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), new ContactRepositoryImpl(contactRepositoryJpa).getContacts(Collections.singletonList(TEST_CONTACT_ID)));
        verify(contactRepositoryJpa, times(1)).findAllById(Collections.singletonList(TEST_CONTACT_ID));
    }

}
