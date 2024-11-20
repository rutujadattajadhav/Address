package com.rutuja.address.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address")
@Data
public class AddressModel {

    @Id
    @Column(value = "addressId")
    private Integer addressid;

    @Column(value = "line1")
    private String line1;

    @Column(value = "line2")
    private String line2;

    @Column(value = "districtId")
    private Integer districtId;

    @Column(value="stateId")
    private Integer stateId;

    @Column(value = "countryId")
    private Integer countryId;

}
