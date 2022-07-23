package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.entity.ContactDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ContactValidatorImpl implements ContactValidator {

    @Override
    public void validate(ContactDTO contactDTO) {
        validate("firstName", contactDTO.getFirstName());
        validate("lastName", contactDTO.getLastName());
        validate("firstLineOfAddress", contactDTO.getContactDetail().getAddress().getFirstLineOfAddress());
        validate("postCode", contactDTO.getContactDetail().getAddress().getPostcode());
        validate("phoneNumbers", contactDTO.getContactDetail().getMobileNumber());

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
