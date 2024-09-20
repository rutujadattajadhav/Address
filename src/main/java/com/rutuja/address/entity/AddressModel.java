package com.rutuja.address.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "address")
@Data
public class AddressModel {

    @Id
    @Column(name = "addressId")
    private Integer addressid;

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "districtId")
    private Integer districtId;

    @Column(name="stateId")
    private Integer stateId;

    @Column(name = "countryId")
    private Integer countryId;

}
