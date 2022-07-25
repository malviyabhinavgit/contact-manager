package com.jpmc.digital.event.bus.assessment.service;

import com.jpmc.digital.event.bus.assessment.dto.Address;
import com.jpmc.digital.event.bus.assessment.dto.ContactDetail;
import com.jpmc.digital.event.bus.assessment.dto.ContactRequestResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

public class ContactValidatorImpl implements ContactValidator {

    @Override
    public void validate(ContactRequestResponse contactRequest) {
        validate("firstName", contactRequest.getFirstName());
        validate("lastName", contactRequest.getLastName());

        ContactDetail contactDetail = contactRequest.getContactDetail();

        if (contactDetail == null) {
            throw new MandatoryFieldNotPresentException("contactDetail");
        }

        Address address = contactDetail.getAddress();

        if (address == null && CollectionUtils.isEmpty(contactDetail.getMobileNumber())) {
            throw new MandatoryFieldNotPresentException("contactDetail");
        }

    }

    private void validate(String propertyName, String propertyValue) {
        if (Strings.isBlank(propertyValue)) {
            throw new MandatoryFieldNotPresentException(propertyName);
        }
    }

}
