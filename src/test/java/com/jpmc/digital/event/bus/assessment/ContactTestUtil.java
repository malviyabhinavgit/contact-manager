package com.jpmc.digital.event.bus.assessment;

public class ContactTestUtil {
    public static final String VALID_CONTACT_DTO_JSON = "classpath:contactDto.json";
    public static final String CONTACT_DTO_WITHOUT_FIRST_NAME_JSON = "classpath:contactDtoWithoutFirstName.json";
    public static final String CONTACT_DTO_WITHOUT_MOBILE_JSON = "classpath:contactDtoWithoutMobileNumber.json";
    public static final String CONTACT_DTO_WITHOUT_ADD_FIRST_LINE = "classpath:contactDtoWithoutFirstLineOfAddress.json";
    public static final String CONTACT_DTO_WITHOUT_POSTCODE = "classpath:contactDtoWithoutPostCode.json";
    public static final String VALID_CONTACT_JSON = "classpath:contact.json";
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String CONTACT_API_BASE_PATH = "/api/contact/";
    public static final Long TEST_CONTACT_ID = 123L;
    public static final Long TEST_CONTACT_ID_1 = 345L;

    private ContactTestUtil() {

    }
}
