package com.rutuja.address.controller;

import com.rutuja.address.beans.AddressRequestBean;
import com.rutuja.address.beans.AddressResponseBean;
import com.rutuja.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{addressId}")
    public Mono<AddressResponseBean> getAddressById(@PathVariable("addressId") Integer addressid) throws Exception {
      return  addressService.getAddressById(addressid) ;
    }

    @GetMapping(value = "/getAllAddress")
    public Flux<AddressResponseBean> getAllAddress() throws Exception {
        return addressService.getAllAddresses();
    }

    @GetMapping("/getAddresss")
    public Flux<AddressResponseBean> getAddress(@RequestBody List<Integer> addressIds) throws Exception {
        return addressService.getAllAddressesById(addressIds);
    }


    @PostMapping(value = "/saveAddress")
    public Mono<AddressResponseBean> saveAddress(@RequestBody AddressRequestBean addressRequestBean) throws Exception {
        return addressService.saveAddress(addressRequestBean);
    }

    @PutMapping(value = "/updateAddress")
    public Mono<AddressResponseBean> updateAddress(@RequestBody AddressRequestBean addressRequestBean) throws Exception {
        return addressService.updateAddress(addressRequestBean);
    }

    @DeleteMapping(value = "/deleteAddress/{deleteAddressById}")
    public Mono<String> deleteAddress(@PathVariable("deleteAddressById") Integer addressId) {
        return addressService.deleteAddress(addressId);
    }
}
