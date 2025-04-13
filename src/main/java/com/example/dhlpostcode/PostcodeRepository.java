package com.example.dhlpostcode;

import org.springframework.data.repository.CrudRepository;

public interface PostcodeRepository extends CrudRepository<Postcode, String> {

	Postcode findByCode(String code);
}
