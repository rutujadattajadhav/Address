package com.rutuja.address.service;

import com.rutuja.address.beans.*;
import com.rutuja.address.entity.AddressModel;
import com.rutuja.address.exception.AddressNotFoundException;
import com.rutuja.address.repo.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    private static final String STATE_USER = "stateUser";
    private static final String STATE_PASSWORD = "state";
    private static final String COUNTRY_USER = "countryUser";
    private static final String COUNTRY_PASSWORD = "countery";
    private static final String DISTRICT_USER = "districtUser";
    private static final String DISTRICT_PASSWORD = "districtp";

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

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<AddressResponseBean> getAddressById(Integer addressId) {
        return addressRepository.findById(addressId)
                .switchIfEmpty(Mono.error(new AddressNotFoundException("Address not found")))
                .flatMap(addressModel -> createAddressResponseBean(addressModel));
    }

    public Flux<AddressResponseBean> getAllAddresses() {
        return addressRepository.findAll()
                .flatMap(this::createAddressResponseBean);
    }

    public Flux<AddressResponseBean> getAllAddressesById(Iterable<Integer> addressIds) {
        return addressRepository.findAllById(addressIds)
                .flatMap(this::createAddressResponseBean);
    }

    public Mono<AddressResponseBean> saveAddress(AddressRequestBean address) {
        return getNextSequenceValue("address")
                .flatMap(id -> {
                    address.setAddressId(id.intValue());
                    String insertQuery = "INSERT INTO address (addressId, line1, line2, districtId, stateId, countryId) " +
                            "VALUES (:addressId, :line1, :line2, :districtId, :stateId, :countryId)";
                    return databaseClient.sql(insertQuery)
                            .bind("addressId", address.getAddressId())
                            .bind("line1", address.getAddressLine1())
                            .bind("line2", address.getAddressLine2())
                            .bind("districtId", address.getDistrict().getDistrictId())
                            .bind("stateId", address.getState().getStateId())
                            .bind("countryId", address.getCountry().getCountryId())
                            .fetch()
                            .rowsUpdated()
                            .flatMap(rowsUpdated -> createAddressResponseBean(address));
                });
    }

    private Mono<AddressResponseBean> createAddressResponseBean(AddressModel addressModel) {
        AddressResponseBean addressResponseBean = new AddressResponseBean();
        setAddressFields(addressModel, addressResponseBean);

        Mono<StateBean> stateBeanMono = makeWebClientCall("http://STATE" + stateContextName +"/stateById/"+ addressModel.getStateId(), STATE_USER, STATE_PASSWORD, StateBean.class);
        Mono<CountryBean> countryBeanMono = makeWebClientCall("http://COUNTRY" + countryContextName +"/getCountry/"+ addressModel.getCountryId(), COUNTRY_USER, COUNTRY_PASSWORD, CountryBean.class);
        Mono<DistrictBean> districtBeanMono = makeWebClientCall("http://DISTRICT" + districtContextPath +"/districtId/"+ addressModel.getDistrictId(), DISTRICT_USER, DISTRICT_PASSWORD, DistrictBean.class);

        return Mono.zip(stateBeanMono, districtBeanMono, countryBeanMono)
                .map(tuple -> {
                    addressResponseBean.setState(tuple.getT1());
                    addressResponseBean.setDistrict(tuple.getT2());
                    addressResponseBean.setCountry(tuple.getT3());
                    return addressResponseBean;
                });
    }

    private Mono<AddressResponseBean> createAddressResponseBean(AddressRequestBean addressRequestBean) {
        AddressResponseBean addressResponseBean = new AddressResponseBean();
        setAddressFields(addressRequestBean, addressResponseBean);

        Mono<StateBean> stateBeanMono = makeWebClientCall("http://STATE" + stateContextName +"/stateById/"+ addressRequestBean.getState().getStateId(), STATE_USER, STATE_PASSWORD, StateBean.class);
        Mono<CountryBean> countryBeanMono = makeWebClientCall("http://COUNTRY" + countryContextName +"/getCountry/"+ addressRequestBean.getCountry().getCountryId(), COUNTRY_USER, COUNTRY_PASSWORD, CountryBean.class);
        Mono<DistrictBean> districtBeanMono = makeWebClientCall("http://DISTRICT" + districtContextPath +"/districtId/"+ addressRequestBean.getDistrict().getDistrictId(), DISTRICT_USER, DISTRICT_PASSWORD, DistrictBean.class);

        return Mono.zip(stateBeanMono, districtBeanMono, countryBeanMono)
                .map(tuple -> {
                    addressResponseBean.setState(tuple.getT1());
                    addressResponseBean.setDistrict(tuple.getT2());
                    addressResponseBean.setCountry(tuple.getT3());
                    return addressResponseBean;
                });
    }

    private void setAddressFields(AddressModel addressModel, AddressResponseBean addressResponseBean) {
        addressResponseBean.setAddressId(addressModel.getAddressid());
        addressResponseBean.setAddressLine1(addressModel.getLine1());
        addressResponseBean.setAddressLine2(addressModel.getLine2());
    }

    private void setAddressFields(AddressRequestBean addressRequestBean, AddressResponseBean addressResponseBean) {
        addressResponseBean.setAddressId(addressRequestBean.getAddressId());
        addressResponseBean.setAddressLine1(addressRequestBean.getAddressLine1());
        addressResponseBean.setAddressLine2(addressRequestBean.getAddressLine2());
    }

    private <T> Mono<T> makeWebClientCall(String uri, String username, String password, Class<T> responseType) {
        WebClient webClient = webClientBuilder.build();
        return webClient.get()
                .uri(uri)
                .headers(headers -> headers.setBasicAuth(username, password))
                .retrieve()
                .bodyToMono(responseType);
    }

    private Mono<Integer> getNextSequenceValue(String sequenceName) {
        return databaseClient.sql("UPDATE sequence SET value = LAST_INSERT_ID(value + 1) WHERE name = :name")
                .bind("name", sequenceName)
                .fetch()
                .rowsUpdated()
                .then(databaseClient.sql("SELECT value as LAST_INSERT_ID FROM sequence WHERE name = :address")
                        .bind("address", sequenceName)
                        .fetch()
                        .one()
                        .map(row -> (Integer) row.get("LAST_INSERT_ID")));
    }

    public Mono<AddressResponseBean> updateAddress(AddressRequestBean addressRequestBean) {
        return addressRepository.findById(addressRequestBean.getAddressId())
                .switchIfEmpty(Mono.error(new AddressNotFoundException("Address not found")))
                .flatMap(addressModel -> {
                    addressModel.setLine1(addressRequestBean.getAddressLine1());
                    addressModel.setLine2(addressRequestBean.getAddressLine2());
                    addressModel.setDistrictId(addressRequestBean.getDistrict().getDistrictId());
                    addressModel.setStateId(addressRequestBean.getState().getStateId());
                    addressModel.setCountryId(addressRequestBean.getCountry().getCountryId());
                    return r2dbcEntityTemplate.update(addressModel)
                            .thenReturn(addressModel);
                })
                .flatMap(this::createAddressResponseBean);
    }

    public Mono<String> deleteAddress(Integer addressId) {
        return addressRepository.findById(addressId)
                .switchIfEmpty(Mono.error(new AddressNotFoundException("Address not found")))
                .flatMap(addressModel -> r2dbcEntityTemplate.delete(addressModel)
                        .thenReturn("Address deleted successfully"));
    }
}