package com.jpmc.digital.event.bus.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.controller.ContactController;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;
import com.jpmc.digital.event.bus.assessment.service.ContactService;
import com.jpmc.digital.event.bus.assessment.service.ContactValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.jpmc.digital.event.bus.assessment.ContactTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactManagerApplicationTests {

    @Autowired
    private ContactController contactController;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactValidator contactValidator;

    @Autowired
    private ContactRepositoryImpl contactRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void contextLoads() {
        assertNotNull(contactController);
        assertNotNull(contactService);
        assertNotNull(contactValidator);
        assertNotNull(contactRepository);
    }

    @Test
    void shouldCreateContactWhenPostedRightContactDto() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactDTO validContactDto = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_DTO_JSON), ContactDTO.class);
        HttpEntity<ContactDTO> request = new HttpEntity<>(validContactDto, headers);

        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, request, Contact.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        uri = new URI(baseUrl + result.getBody().getId());
        assertEquals(result.getBody(), this.restTemplate.getForObject(uri, Contact.class));
    }

    @Test
    void shouldGetBadRequestWhenPostedInvalidContactDto() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactDTO invalidContactDto = this.objectMapper.readValue(ResourceUtils.getFile(CONTACT_DTO_WITHOUT_FIRST_NAME_JSON), ContactDTO.class);
        HttpEntity<ContactDTO> request = new HttpEntity<>(invalidContactDto, headers);
        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, request, Contact.class);

        //Verify request succeed
        assertEquals(400, result.getStatusCodeValue());

    }

    @Test
    void shouldGetContactsWhenContactExistsForGivenContactIds() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactDTO validContactDto = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_DTO_JSON), ContactDTO.class);

        HttpEntity<ContactDTO> request = new HttpEntity<>(validContactDto, headers);
        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, request, Contact.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        request = new HttpEntity<>(validContactDto, headers);
        ResponseEntity<Contact> anotherResult = this.restTemplate.postForEntity(uri, request, Contact.class);

        //Verify request succeed
        assertEquals(201, anotherResult.getStatusCodeValue());
        assertNotNull(anotherResult.getBody());

        List<Contact> expectedContacts = new ArrayList<>();
        expectedContacts.add(result.getBody());
        expectedContacts.add(anotherResult.getBody());

        String url = baseUrl + "?contactIds=" + result.getBody().getId() + "&contactIds=" + anotherResult.getBody().getId();
        ResponseEntity<List<Contact>> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Contact>>() {
                        }
                );

        List<Contact> actualContacts = responseEntity.getBody();

        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void shouldGetContactWhenContactExistsForGivenContactId() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactDTO validContactDto = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_DTO_JSON), ContactDTO.class);

        HttpEntity<ContactDTO> request = new HttpEntity<>(validContactDto, headers);
        ResponseEntity<Contact> result = this.restTemplate.postForEntity(uri, request, Contact.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        String url = baseUrl + result.getBody().getId();
        ResponseEntity<Contact> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Contact>() {
                        }
                );

        Contact actualContact = responseEntity.getBody();

        assertEquals(result.getBody(), actualContact);
    }

}
