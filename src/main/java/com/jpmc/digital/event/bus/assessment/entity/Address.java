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
    private String lastLineOfAddress;

    private String city;
    private String postcode;

    private String country;

    public String getFirstLineOfAddress() {
        return firstLineOfAddress;
    }

    public void setFirstLineOfAddress(String firstLineOfAddress) {
        this.firstLineOfAddress = firstLineOfAddress;
    }

    public String getLastLineOfAddress() {
        return lastLineOfAddress;
    }

    public void setLastLineOfAddress(String lastLineOfAddress) {
        this.lastLineOfAddress = lastLineOfAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && Objects.equals(firstLineOfAddress, address.firstLineOfAddress) && Objects.equals(lastLineOfAddress, address.lastLineOfAddress) && Objects.equals(city, address.city) && Objects.equals(postcode, address.postcode) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstLineOfAddress, lastLineOfAddress, city, postcode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", firstLineOfAddress='" + firstLineOfAddress + '\'' +
                ", lastLineOfAddress='" + lastLineOfAddress + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
