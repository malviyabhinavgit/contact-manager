package com.jpmc.digital.event.bus.assessment.config;

import com.jpmc.digital.event.bus.assessment.repository.ContactRepositoryImpl;
import com.jpmc.digital.event.bus.assessment.repository.jpa.ContactRepositoryJpa;
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
    public ContactRepositoryJpa contactRepositoryJpa;

    @Bean
    public ContactRepositoryImpl contactRepository(ContactRepositoryJpa contactRepositoryJpa) {
        return new ContactRepositoryImpl(contactRepositoryJpa);
    }


    @Bean
    public ContactValidator contactValidator() {
        return new ContactValidatorImpl();
    }

    @Bean
    public ContactService contactService(ContactRepositoryImpl contactRepository, ContactValidator contactValidator) {
        return new ContactServiceImpl(contactRepository, contactValidator);
    }

}
