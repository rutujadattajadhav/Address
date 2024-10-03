package com.rutuja.address.service;

import com.rutuja.address.beans.AddressResponseBean;
import com.rutuja.address.beans.CountryBean;
import com.rutuja.address.beans.DistrictBean;
import com.rutuja.address.beans.StateBean;
import com.rutuja.address.entity.AddressModel;
import com.rutuja.address.repo.AddressRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private Optional<AddressModel> optional;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient mockedWebClient;


//    @Mock
//    private WebClient.RequestBodyUriSpec uriSpec;
//    @Mock
//    private WebClient.RequestBodyUriSpec headerSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private AddressResponseBean addressResponseBean;

    @Mock
    private Mono<AddressResponseBean> mono;

//@Test
//    public void getAddressByIdSuccess() throws Exception {
//        AddressModel addressModel=new AddressModel();
//        addressModel.setAddressid(1);
//        addressModel.setLine1("flat 205,Shiv colony");
//        addressModel.setLine2("Dange Chouk");
//        Optional<AddressModel> addressModelOptional=Optional.of(addressModel);
//        Mockito.when(optional.get()).thenReturn(addressModel);
//        Mockito.when(addressRepository.findById(Mockito.anyInt())).thenReturn(addressModelOptional);
//        Mockito.when(webClientBuilder.build()).thenReturn(mockedWebClient);
////        WebClient.RequestBodyUriSpec bodySpec = mock(WebClient.RequestBodyUriSpec.class);
////        WebClient.ResponseSpec response = mock(WebClient.ResponseSpec.class);
//        Mockito.when(mockedWebClient.get()).thenReturn(requestHeadersUriSpec);
//        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
//        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
//        Mono<CountryBean> countryBeanMono=Mono.just(new CountryBean());
//        Mono<StateBean> stateBeanMono=Mono.just(new StateBean());
//         Mono<DistrictBean> districtBeanMono=Mono.just(new DistrictBean());
//         Mockito.when(responseSpec.bodyToMono(DistrictBean.class)).thenReturn(districtBeanMono);
//         Mockito.when(responseSpec.bodyToMono(StateBean.class)).thenReturn(stateBeanMono);
//         Mockito.when(responseSpec.bodyToMono(CountryBean.class)).thenReturn(countryBeanMono);
//       Mockito.when(Mono.zip(Mockito.any()))
//        Mono<AddressResponseBean> addressResponseBeanMonoResult= addressService.getAddressbyId(1);
//    Assert.assertEquals(new AddressResponseBean().toString(),addressResponseBeanMonoResult.toString());
//    }
}
