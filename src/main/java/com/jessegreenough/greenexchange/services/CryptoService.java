package com.jessegreenough.greenexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jessegreenough.greenexchange.models.Crypto;
import com.jessegreenough.greenexchange.repositories.CryptoRepository;

@Service
public class CryptoService {

		@Autowired
		private CryptoRepository cryptoRepo;
		
		public Crypto createCrypto(Crypto c) {
			return cryptoRepo.save(c);
		}
		
		public Crypto updateCrypto(Crypto c) {
			return cryptoRepo.save(c);
		}
}
