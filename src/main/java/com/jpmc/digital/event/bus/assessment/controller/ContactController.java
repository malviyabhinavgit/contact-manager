package com.jpmc.digital.event.bus.assessment.controller;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import com.jpmc.digital.event.bus.assessment.dto.ContactResponse;
import com.jpmc.digital.event.bus.assessment.service.ContactService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
@OpenAPIDefinition(info = @Info(title = "Contact-Manager", description = "Api to store/Retrieve Contacts over HTTP",
        version = "v1", license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class ContactController {

    private final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Saves Contact.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Contact Created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponse.class)))})
    public ContactResponse createContact(@NotNull @Valid @RequestBody ContactRequest contactRequest) {
        log.info("POST request received for creating contact={}", contactRequest);
        ContactResponse contactResponse = contactService.save(contactRequest);
        log.info("Successfully created contact={}", contactResponse);
        return contactResponse;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a Contact by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found Contact",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)})
    public ContactResponse getContact(@PathVariable("id") Long contactId) {
        log.info("GET request received for retrieving contactId={}", contactId);
        ContactResponse contactResponse = contactService.getContact(contactId);
        log.info("Successfully fetched contact={}", contactResponse);
        return contactResponse;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Contacts for given list of contactIds")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ContactResponse.class)))})})
    public List<ContactResponse> getContacts(@RequestParam List<Long> contactIds) {

        log.info("GET request received for fetching contacts having contactIds={}", contactIds);
        List<ContactResponse> contacts = contactService.getContacts(contactIds);
        if (CollectionUtils.isEmpty(contacts)) {
            log.info("No contacts found for contactIds={}", contactIds);
            return Collections.emptyList();
        } else {
            log.info("Contacts found for contactIds={} are {}", contactIds, contacts);
            return contacts;
        }

    }
}
