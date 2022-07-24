package com.jpmc.digital.event.bus.assessment.repository.jpa;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepositoryJpa extends JpaRepository<Contact, Long> {
}
