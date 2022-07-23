package com.jpmc.digital.event.bus.assessment.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactNotFoundException extends RuntimeException {
    private final Logger log = LoggerFactory.getLogger(ContactNotFoundException.class);

    public ContactNotFoundException() {
        log.error("No Contact found.");
    }

}
