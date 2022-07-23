package com.jpmc.digital.event.bus.assessment.config;

import com.jpmc.digital.event.bus.assessment.repository.ContactRepository;
import com.jpmc.digital.event.bus.assessment.service.ContactService;
import com.jpmc.digital.event.bus.assessment.service.ContactServiceImpl;
import com.jpmc.digital.event.bus.assessment.service.ContactValidator;
import com.jpmc.digital.event.bus.assessment.service.ContactValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Autowired
    public ContactRepository contactRepository;


    @Bean
    public ContactValidator contactValidator() {
        return new ContactValidatorImpl();
    }

    @Bean
    public ContactService contactService(ContactRepository contactRepository, ContactValidator contactValidator) {
        return new ContactServiceImpl(contactRepository, contactValidator);
    }

}
