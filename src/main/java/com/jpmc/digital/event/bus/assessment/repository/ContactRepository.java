package com.jpmc.digital.event.bus.assessment.repository;

import com.jpmc.digital.event.bus.assessment.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
