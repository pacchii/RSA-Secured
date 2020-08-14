package com.varshagroups.secured.rest.repository;

import com.varshagroups.secured.rest.model.AuthRequest;
import com.varshagroups.secured.rest.model.AuthResponse;
import com.varshagroups.secured.rest.model.Response;
import com.varshagroups.secured.rest.model.UserInformation;

public interface IRSASecuredRepository {

	AuthResponse authenticate(AuthRequest authRequest);
	Response register(UserInformation userInformation);

}
