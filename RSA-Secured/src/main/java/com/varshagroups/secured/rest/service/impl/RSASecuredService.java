package com.varshagroups.secured.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varshagroups.secured.rest.model.AuthRequest;
import com.varshagroups.secured.rest.model.AuthResponse;
import com.varshagroups.secured.rest.model.Response;
import com.varshagroups.secured.rest.model.UserInformation;
import com.varshagroups.secured.rest.repository.IRSASecuredRepository;
import com.varshagroups.secured.rest.service.IRSASecuredService;

@Service
public class RSASecuredService implements IRSASecuredService {
	
	@Autowired
	IRSASecuredRepository rsaSecuredRepository;

	public void setRsaSecuredRepository(IRSASecuredRepository rsaSecuredRepository) {
		this.rsaSecuredRepository = rsaSecuredRepository;
	}

	@Override
	public AuthResponse authenticate(AuthRequest authRequest) {
		return rsaSecuredRepository.authenticate(authRequest);
	}

	@Override
	public Response register(UserInformation userInformation) {
		return rsaSecuredRepository.register(userInformation);
	}

}
