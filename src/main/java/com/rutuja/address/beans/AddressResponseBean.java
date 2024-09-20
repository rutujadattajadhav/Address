package com.rutuja.address.beans;

import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class AddressResponseBean {
    private Integer addressId;
    private String  addressLine1;
    private String addressLine2;
    private StateBean state;
    private DistrictBean district;
    private CountryBean country;
    public AddressResponseBean(){

    }

}
