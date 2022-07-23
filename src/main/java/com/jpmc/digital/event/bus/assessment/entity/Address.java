package com.jpmc.digital.event.bus.assessment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstLineOfAddress;
    private String secondLineOfAddress;
    private String postcode;

    public String getFirstLineOfAddress() {
        return firstLineOfAddress;
    }

    public void setFirstLineOfAddress(String firstLineOfAddress) {
        this.firstLineOfAddress = firstLineOfAddress;
    }

    public String getSecondLineOfAddress() {
        return secondLineOfAddress;
    }

    public void setSecondLineOfAddress(String secondLineOfAddress) {
        this.secondLineOfAddress = secondLineOfAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(firstLineOfAddress, address.firstLineOfAddress) &&
                Objects.equals(secondLineOfAddress, address.secondLineOfAddress) &&
                Objects.equals(postcode, address.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstLineOfAddress, secondLineOfAddress, postcode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", firstLineOfAddress='" + firstLineOfAddress + '\'' +
                ", secondLineOfAddress='" + secondLineOfAddress + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
