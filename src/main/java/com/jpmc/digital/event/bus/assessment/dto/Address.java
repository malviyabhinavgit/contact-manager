package com.jpmc.digital.event.bus.assessment.dto;

import java.util.Objects;


public class Address {
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
        return Objects.equals(firstLineOfAddress, address.firstLineOfAddress) && Objects.equals(lastLineOfAddress, address.lastLineOfAddress) && Objects.equals(city, address.city) && Objects.equals(postcode, address.postcode) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstLineOfAddress, lastLineOfAddress, city, postcode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "firstLineOfAddress='" + firstLineOfAddress + '\'' +
                ", lastLineOfAddress='" + lastLineOfAddress + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
