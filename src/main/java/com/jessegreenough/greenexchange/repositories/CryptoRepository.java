package com.jessegreenough.greenexchange.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jessegreenough.greenexchange.models.Crypto;

@Repository
public interface CryptoRepository extends CrudRepository<Crypto, Long>{

	public List<Crypto> findAll();
}
