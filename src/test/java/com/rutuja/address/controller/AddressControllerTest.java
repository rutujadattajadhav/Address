package com.rutuja.address.controller;

import com.rutuja.address.beans.AddressResponseBean;
import com.rutuja.address.service.AddressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

   @Mock
    private AddressService addressService;
//    @Test
//    public void getAddressById() throws Exception {
//        AddressResponseBean addressResponseBean=new AddressResponseBean();
//        Mono<AddressResponseBean> addressResponseBeanMono1=Mono.just(addressResponseBean);
//        Mockito.when(addressService.getAddressbyId(Mockito.anyInt())).thenReturn(addressResponseBeanMono1);
//        Mono<AddressResponseBean> addressResponseBeanMono=addressController.getAddressById(1);
//        Assert.assertEquals(addressResponseBeanMono1.toString(),addressResponseBeanMono.toString());
//    }
//
//    @Test
//    public void getAllAddress() throws Exception {
//        AddressResponseBean addressResponseBean=new AddressResponseBean();
//        Flux<AddressResponseBean> addressResponseBeanFlux=Flux.just(addressResponseBean);
//        Mockito.when(addressService.getAllAddress()).thenReturn(addressResponseBeanFlux);
//        Flux<AddressResponseBean> allAddress=addressController.getAllAddress();
//        Assert.assertEquals(addressResponseBeanFlux.toString(),allAddress.toString());
//    }
//    @Test
//    public void getAddresses() throws Exception {
//        AddressResponseBean addressResponseBean=new AddressResponseBean();
//        Flux<AddressResponseBean> addressResponseBeanFlux=Flux.just(addressResponseBean);
//        Mockito.when(addressService.getAddress(Mockito.anyList())).thenReturn(addressResponseBeanFlux);
//        List<Integer> integerList=new ArrayList<>();
//        integerList.add(1);
//        Flux<AddressResponseBean> addressResponseBeanFluxResult=addressController.getAddress(integerList);
//        Assert.assertEquals(addressResponseBeanFlux.toString(),addressResponseBeanFluxResult.toString());
//    }
}
