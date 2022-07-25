package com.jpmc.digital.event.bus.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequestResponse;
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
    void shouldCreateContactWhenCalledWithValidContactReq() throws Exception {
        ContactRequestResponse contactRequest = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_REQ_JSON), ContactRequestResponse.class);
        when(contactService.save(contactRequest))
                .thenReturn(this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactRequestResponse.class));


        this.mockMvc.perform(post(CONTACT_API_BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(contactRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGiveBadRequestWhenCalledWithInValidContactReq() throws Exception {
        ContactRequestResponse invalidContactRequest = this.objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_FIRST_NAME_JSON), ContactRequestResponse.class);
        when(contactService.save(invalidContactRequest))
                .thenThrow(new MandatoryFieldNotPresentException("firstName"));


        this.mockMvc.perform(post(CONTACT_API_BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(invalidContactRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetContactWhenContactExists() throws Exception {
        ContactRequestResponse contactResponse = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactRequestResponse.class);
        when(contactService.getContact(TEST_CONTACT_ID)).thenReturn(contactResponse);

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
        ContactRequestResponse contactResponse = objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_JSON), ContactRequestResponse.class);
        List<Long> contactIds = new ArrayList<>();
        contactIds.add(TEST_CONTACT_ID);
        contactIds.add(TEST_CONTACT_ID_1);

        when(contactService.getContacts(contactIds)).thenReturn(Collections.singletonList(contactResponse));

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
