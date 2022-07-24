package com.jpmc.digital.event.bus.assessment.dto;

import java.util.Objects;



public class ContactRequest {
    private String firstName;

    private String lastName;

    private ContactDetail contactDetail;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactRequest contact = (ContactRequest) o;
        return  Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(contactDetail, contact.contactDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, contactDetail);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactDetail=" + contactDetail +
                '}';
    }
}
