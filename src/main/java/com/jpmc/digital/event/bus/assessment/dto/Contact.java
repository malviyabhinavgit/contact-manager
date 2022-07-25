package com.jpmc.digital.event.bus.assessment.dto;


import java.util.Objects;

public class Contact {

    private long id;

    private String firstName;

    private String lastName;

    private ContactDetail contactDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        Contact contactResponse = (Contact) o;
        return id == contactResponse.id &&
                Objects.equals(firstName, contactResponse.firstName) &&
                Objects.equals(lastName, contactResponse.lastName) &&
                Objects.equals(contactDetail, contactResponse.contactDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, contactDetail);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactDetail=" + contactDetail +
                '}';
    }
}
