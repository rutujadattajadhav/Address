package com.rutuja.address.service;


import com.rutuja.address.beans.AddressResponseBean;
import com.rutuja.address.beans.CountryBean;
import com.rutuja.address.beans.DistrictBean;
import com.rutuja.address.beans.StateBean;
import com.rutuja.address.entity.AddressModel;
import com.rutuja.address.repo.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.servlet.context-path-state}")
    private String stateContextName;

    @Value("${server.servlet.context-path-country}")
    private String countryContextName;

    @Value("${server.servlet.context-path-district}")
    private String districtContextPath;

    @Autowired
    @LoadBalanced
    private WebClient.Builder webClientBuilder;

    public Mono<AddressResponseBean> getAddressbyId(Integer addressId) throws Exception {
        Optional<AddressModel> addressModelOptional = addressRepository.findById(addressId);
        if(addressModelOptional.isPresent()){
            log.debug("Id is present in db");
            AddressModel addressModel =addressModelOptional.get();
                AddressResponseBean addressResponseBean = new AddressResponseBean();
                addressResponseBean.setAddressId(addressModel.getAddressid());
                addressResponseBean.setAddressLine2(addressModel.getLine2());
                addressResponseBean.setAddressLine1(addressModel.getLine1());
                Mono<StateBean> stateBeanMono=   webClientBuilder.build()
                    .get()
                    .uri("http://STATE" + stateContextName + addressModel.getStateId())
                    .retrieve()
                    .bodyToMono(StateBean.class);
            Mono<CountryBean> countryBeanMono=webClientBuilder.build()
                        .get()
                        .uri("http://COUNTRY" + countryContextName + addressModel.getCountryId())
                        .retrieve()
                        .bodyToMono(CountryBean.class);
            Mono<DistrictBean> districtBeanMono =webClientBuilder.build()
                        .get()
                        .uri("http://DISTRICT" + districtContextPath + addressModel.getDistrictId())
                        .retrieve()
                        .bodyToMono(DistrictBean.class);
            return  Mono.zip(stateBeanMono,districtBeanMono,countryBeanMono).map(tuple -> {
                addressResponseBean.setState(tuple.getT1());
                addressResponseBean.setDistrict(tuple.getT2());
                addressResponseBean.setCountry(tuple.getT3());
                return addressResponseBean;
            });
        }
         throw new Exception("address not found");
    }

    public Flux<AddressResponseBean> getAllAddress() throws Exception{
        List<AddressResponseBean> list= new ArrayList<>();

        Iterable<AddressModel> addressModels=addressRepository.findAll();
       if(addressModels!=null) {
           List<Mono<AddressResponseBean>> listMono = new ArrayList<>();
          addressModels.forEach((addressModel) -> {
               AddressResponseBean addressResponseBean = new AddressResponseBean();
                addressResponseBean.setAddressId(addressModel.getAddressid());
               addressResponseBean.setAddressLine2(addressModel.getLine2());
               addressResponseBean.setAddressLine1(addressModel.getLine1());

          Mono<StateBean> stateBeanMono = webClientBuilder.build()
                        .get()
                       .uri("http://STATE"+stateContextName+addressModel.getStateId())
                        .retrieve()
                        .bodyToMono(StateBean.class);
          Mono<CountryBean> countryBeanMono=    webClientBuilder.build()
                        .get()
                        .uri("http://COUNTRY"+countryContextName+addressModel.getCountryId())
                        .retrieve()
                        .bodyToMono(CountryBean.class);

          Mono<DistrictBean> districtBeanMono=    webClientBuilder.build()
                        .get()
                        .uri("http://DISTRICT"+districtContextPath+addressModel.getDistrictId())
                       .retrieve()
                       .bodyToMono(DistrictBean.class);
          Mono<AddressResponseBean> addressResponseBeanMono = Mono.zip(stateBeanMono,districtBeanMono,countryBeanMono).map(tuple -> {
                  addressResponseBean.setState(tuple.getT1());
                  addressResponseBean.setDistrict(tuple.getT2());
                  addressResponseBean.setCountry(tuple.getT3());
                  return addressResponseBean;
              });
              listMono.add(addressResponseBeanMono);
            });
               return Flux.merge(listMono);
          }
            throw new Exception("address not found");
        }

    public Flux<AddressResponseBean> getAddress(List<Integer> addressIds) throws Exception{
        List<Mono<AddressResponseBean>> listOfAddressResponse=new ArrayList<>();
        Iterable<AddressModel> addressModels=addressRepository.findAllById(addressIds);
        if(addressModels!=null) {
            addressModels.forEach((addressModel) -> {
                AddressResponseBean addressResponseBean = new AddressResponseBean();
                addressResponseBean.setAddressId(addressModel.getAddressid());
                addressResponseBean.setAddressLine2(addressModel.getLine2());
                addressResponseBean.setAddressLine1(addressModel.getLine1());
               Mono<StateBean> stateBeanMono=webClientBuilder.build()
                        .get()
                        .uri("http://STATE"+stateContextName+addressModel.getStateId())
                        .retrieve()
                        .bodyToMono(StateBean.class);


               Mono<CountryBean> countryBeanMono =webClientBuilder.build()
                        .get()
                        .uri("http://COUNTRY"+countryContextName+addressModel.getCountryId())
                        .retrieve()
                        .bodyToMono(CountryBean.class);

               Mono<DistrictBean> districtBeanMono=webClientBuilder.build()
                        .get()
                        .uri("http://DISTRICT"+districtContextPath+addressModel.getDistrictId())
                        .retrieve()
                        .bodyToMono(DistrictBean.class);
                Mono<AddressResponseBean> addressResponseBeanMono  =Mono.zip(stateBeanMono,countryBeanMono,districtBeanMono)
                               .map(addressTuple->{
                                   addressResponseBean.setState(addressTuple.getT1());
                                   addressResponseBean.setCountry(addressTuple.getT2());
                                   addressResponseBean.setDistrict(addressTuple.getT3());
                                   return addressResponseBean;
                               });
                listOfAddressResponse.add(addressResponseBeanMono);
            });
            return  Flux.merge(listOfAddressResponse);
        }
        throw new Exception("address not found");
    }
    }