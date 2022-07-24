package com.jpmc.digital.event.bus.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import com.jpmc.digital.event.bus.assessment.repository.jpa.ContactRepositoryJpa;
import com.jpmc.digital.event.bus.assessment.service.ContactNotFoundException;
import com.jpmc.digital.event.bus.assessment.service.ContactService;
import com.jpmc.digital.event.bus.assessment.service.MandatoryFieldNotPresentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @MockBean
    private ContactRepositoryJpa contactRepositoryJpa;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void shouldCreateContactWhenCalledWithValidContactDto() throws Exception {
        ContactDTO validContactDto = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_DTO_JSON), ContactDTO.class);
        when(contactService.save(validContactDto))
                .thenReturn(this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class));


        this.mockMvc.perform(post(CONTACT_API_BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(validContactDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGiveBadRequestWhenCalledWithInValidContactDto() throws Exception {
        ContactDTO invalidContactDto = this.objectMapper.readValue(ResourceUtils.getFile(CONTACT_DTO_WITHOUT_FIRST_NAME_JSON), ContactDTO.class);
        when(contactService.save(invalidContactDto))
                .thenThrow(new MandatoryFieldNotPresentException("firstName"));


        this.mockMvc.perform(post(CONTACT_API_BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(invalidContactDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetContactWhenContactExists() throws Exception {
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        when(contactService.getContact(TEST_CONTACT_ID)).thenReturn(validContact);

        this.mockMvc.perform(get(CONTACT_API_BASE_PATH + "{id}", TEST_CONTACT_ID))
                .andExpect(status().isOk());
    }


    @Test
    void shouldGetNotFoundWhenContactDoesnotExist() throws Exception {
        when(contactService.getContact(TEST_CONTACT_ID)).thenThrow(new ContactNotFoundException());

        this.mockMvc.perform(get(CONTACT_API_BASE_PATH + "{id}", TEST_CONTACT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetContactsWhenContactsExistsForGivenContactIds() throws Exception {
        Contact validContact = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), Contact.class);
        List<Long> contactIds = new ArrayList<>();
        contactIds.add(TEST_CONTACT_ID);
        contactIds.add(TEST_CONTACT_ID_1);

        when(contactService.getContacts(contactIds)).thenReturn(Collections.singletonList(validContact));

        this.mockMvc.perform(get(CONTACT_API_BASE_PATH).param("contactIds", "123, 234"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetEmptyListWhenContactsDoesnotExistForGivenContactIds() throws Exception {

        List<Long> contactIds = new ArrayList<>();
        contactIds.add(TEST_CONTACT_ID);
        contactIds.add(TEST_CONTACT_ID_1);

        when(contactService.getContacts(contactIds)).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(CONTACT_API_BASE_PATH).param("contactIds", "123, 345"))
                .andExpect(status().isOk());
    }

}
