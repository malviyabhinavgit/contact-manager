package com.jpmc.digital.event.bus.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.digital.event.bus.assessment.controller.ContactController;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import com.jpmc.digital.event.bus.assessment.dto.ContactResponse;
import com.jpmc.digital.event.bus.assessment.entity.Contact;
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
    void shouldCreateContactWhenPostedRightContactReq() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactRequest contactRequest = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_REQ_JSON), ContactRequest.class);
        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, headers);

        ResponseEntity<ContactResponse> result = this.restTemplate.postForEntity(uri, request, ContactResponse.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        uri = new URI(baseUrl + result.getBody().getId());
        assertEquals(result.getBody(), this.restTemplate.getForObject(uri, ContactResponse.class));
    }

    @Test
    void shouldGetBadRequestWhenPostedInvalidContactReq() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactRequest contactRequest = this.objectMapper.readValue(ResourceUtils.getFile(CONTACT_REQ_WITHOUT_FIRST_NAME_JSON), ContactRequest.class);
        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, headers);
        ResponseEntity<ContactResponse> result = this.restTemplate.postForEntity(uri, request, ContactResponse.class);

        //Verify request succeed
        assertEquals(400, result.getStatusCodeValue());

    }

    @Test
    void shouldGetContactsWhenContactExistsForGivenContactIds() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactRequest contactRequest = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_REQ_JSON), ContactRequest.class);

        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, headers);
        ResponseEntity<ContactResponse> result = this.restTemplate.postForEntity(uri, request, ContactResponse.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        request = new HttpEntity<>(contactRequest, headers);
        ResponseEntity<ContactResponse> anotherResult = this.restTemplate.postForEntity(uri, request, ContactResponse.class);

        //Verify request succeed
        assertEquals(201, anotherResult.getStatusCodeValue());
        assertNotNull(anotherResult.getBody());

        List<ContactResponse> expectedContacts = new ArrayList<>();
        expectedContacts.add(result.getBody());
        expectedContacts.add(anotherResult.getBody());

        String url = baseUrl + "?contactIds=" + result.getBody().getId() + "&contactIds=" + anotherResult.getBody().getId();
        ResponseEntity<List<ContactResponse>> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ContactResponse>>() {
                        }
                );

        List<ContactResponse> actualContacts = responseEntity.getBody();

        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void shouldGetContactWhenContactExistsForGivenContactId() throws URISyntaxException, IOException {
        final String baseUrl = LOCAL_HOST + randomServerPort + CONTACT_API_BASE_PATH;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        ContactRequest contactRequest = this.objectMapper.readValue(ResourceUtils.getFile(VALID_CONTACT_REQ_JSON), ContactRequest.class);

        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, headers);
        ResponseEntity<ContactResponse> result = this.restTemplate.postForEntity(uri, request, ContactResponse.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());

        String url = baseUrl + result.getBody().getId();
        ResponseEntity<ContactResponse> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ContactResponse>() {
                        }
                );

        ContactResponse actualContact = responseEntity.getBody();

        assertEquals(result.getBody(), actualContact);
    }

}
