package com.jpmc.digital.event.bus.assessment.entity;

import javax.persistence.*;
import java.util.List;


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
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", mobileNumber=" + mobileNumber +
                ", address=" + address +
                '}';
    }
}
