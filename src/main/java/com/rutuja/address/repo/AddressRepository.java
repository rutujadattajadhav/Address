package com.rutuja.address.repo;

import com.rutuja.address.entity.AddressModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AddressRepository extends ReactiveCrudRepository<AddressModel ,Integer> {
}
