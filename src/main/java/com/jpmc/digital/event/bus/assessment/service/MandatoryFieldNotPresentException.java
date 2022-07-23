package com.jpmc.digital.event.bus.assessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MandatoryFieldNotPresentException extends RuntimeException {
    private final Logger log = LoggerFactory.getLogger(MandatoryFieldNotPresentException.class);

    public MandatoryFieldNotPresentException(String fieldName) {
        log.error("value for {} is mandatory", fieldName);
    }

}
