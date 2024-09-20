package com.rutuja.address.controller;

import com.rutuja.address.beans.AddressResponseBean;
import com.rutuja.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{addressId}")
    public Mono<AddressResponseBean> getAddressById(@PathVariable("addressId") Integer addressid) throws Exception {
      return  addressService.getAddressbyId(addressid) ;
    }

    @GetMapping(value = "/getAllAddress")
    public Flux<AddressResponseBean> getAllAddress() throws Exception {
        return addressService.getAllAddress();
    }

    @GetMapping("/getAddresss")
    public Flux<AddressResponseBean> getAddress(@RequestBody List<Integer> addressIds) throws Exception {
        return addressService.getAddress(addressIds);
    }
}
