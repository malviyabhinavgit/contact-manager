package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.ContactRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ContactValidatorImpl implements ContactValidator {

    @Override
    public void validate(ContactRequest contactRequest) {
        validate("firstName", contactRequest.getFirstName());
        validate("lastName", contactRequest.getLastName());
        validate("firstLineOfAddress", contactRequest.getContactDetail().getAddress().getFirstLineOfAddress());
        validate("postCode", contactRequest.getContactDetail().getAddress().getPostcode());
        validate("city", contactRequest.getContactDetail().getAddress().getPostcode());
        validate("country", contactRequest.getContactDetail().getAddress().getPostcode());
        validate("mobileNumber", contactRequest.getContactDetail().getMobileNumber());

    }

    private void validate(String propertyName, String propertyValue) {
        if (Strings.isBlank(propertyValue)) {
            throw new MandatoryFieldNotPresentException(propertyName);
        }
    }

    private void validate(String propertyName, List<String> propertyValue) {
        if (CollectionUtils.isEmpty(propertyValue)) {
            throw new MandatoryFieldNotPresentException(propertyName);
        }
    }
}
