package com.varshagroups.secured.rest.service;

import com.varshagroups.secured.rest.model.AuthRequest;
import com.varshagroups.secured.rest.model.AuthResponse;
import com.varshagroups.secured.rest.model.Response;
import com.varshagroups.secured.rest.model.UserInformation;

public interface IRSASecuredService {
	
	AuthResponse authenticate(AuthRequest authRequest);
	Response register(UserInformation userInformation);

}
