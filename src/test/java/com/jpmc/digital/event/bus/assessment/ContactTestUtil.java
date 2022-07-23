package com.jpmc.digital.event.bus.assessment;

public class ContactTestUtil {
    public static final String VALID_CONTACT_DTO_JSON = "src/test/resources/contactDto.json";
    public static final String CONTACT_DTO_WITHOUT_FIRST_NAME_JSON = "src/test/resources/contactDtoWithoutFirstName.json";
    public static final String CONTACT_DTO_WITHOUT_MOBILE_JSON = "src/test/resources/contactDtoWithoutMobileNumber.json";
    public static final String CONTACT_DTO_WITHOUT_ADD_FIRST_LINE = "src/test/resources/contactDtoWithoutFirstLineOfAddress.json";
    public static final String CONTACT_DTO_WITHOUT_POSTCODE = "src/test/resources/contactDtoWithoutPostCode.json";
    public static final String VALID_CONTACT_JSON = "src/test/resources/contact.json";
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String CONTACT_API_BASE_PATH = "/api/contact/";
    public static final Long TEST_CONTACT_ID = 123L;
    public static final Long TEST_CONTACT_ID_1 = 345L;

    private ContactTestUtil(){

    }
}
