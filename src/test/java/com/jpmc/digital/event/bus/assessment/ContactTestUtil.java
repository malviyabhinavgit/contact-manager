package com.jpmc.digital.event.bus.assessment;

public class ContactTestUtil {
    public static final String VALID_CONTACT_REQ_JSON = "classpath:contactReq.json";
    public static final String CONTACT_REQ_WITHOUT_FIRST_NAME_JSON = "classpath:contactReqWithoutFirstName.json";
    public static final String CONTACT_REQ_WITHOUT_LAST_NAME_JSON = "classpath:contactReqWithoutLastName.json";
    public static final String CONTACT_REQ_WITHOUT_MOBILE_JSON = "classpath:contactReqWithoutMobileNumber.json";
    public static final String CONTACT_REQ_WITHOUT_ADDRESS = "classpath:contactReqWithoutAddress.json";
    public static final String CONTACT_REQ_WITHOUT_CONTACT_DETAIL = "classpath:contactReqWithoutContactDetail.json";
    public static final String CONTACT_REQ_WITHOUT_MOBILE_ADDRESS_DETAIL = "classpath:contactReqWithoutMobileNumberAndAddress.json";
    public static final String VALID_CONTACT_JSON = "classpath:contact.json";
    public static final String VALID_CONTACT_WITHOUT_ADDRESS_JSON = "classpath:contactWithoutAddress.json";
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String CONTACT_API_BASE_PATH = "/api/v1/contact/";
    public static final Long TEST_CONTACT_ID = 123L;
    public static final Long TEST_CONTACT_ID_1 = 345L;

    private ContactTestUtil() {

    }
}
