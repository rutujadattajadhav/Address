package com.rutuja.address.beans;

import lombok.Data;

@Data
public class AddressRequestBean {
    private Integer addressId;
    private String  addressLine1;
    private String addressLine2;
    private StateBean state;
    private DistrictBean district;
    private CountryBean country;
}
