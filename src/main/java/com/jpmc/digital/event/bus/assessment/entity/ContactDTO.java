package com.jpmc.digital.event.bus.assessment.entity;

import java.util.Objects;

public class ContactDTO {

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
        ContactDTO that = (ContactDTO) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(contactDetail, that.contactDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, contactDetail);
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactDetail=" + contactDetail +
                '}';
    }
}
