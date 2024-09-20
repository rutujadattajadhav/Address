package com.rutuja.address.repo;

import com.rutuja.address.entity.AddressModel;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressModel ,Integer> {
}
