package com.jpmc.digital.event.bus.assessment.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class ContactDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ElementCollection
    private List<String> mobileNumber;

    @OneToOne(cascade = {CascadeType.ALL})
    private Address address;

    public List<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(List<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetail that = (ContactDetail) o;
        return Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileNumber, address);
    }

    @Override
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", mobileNumber=" + mobileNumber +
                ", address=" + address +
                '}';
    }
}
