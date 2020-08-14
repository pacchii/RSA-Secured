package com.varshagroups.secured.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.varshagroups.secured.rest.model.AuthRequest;
import com.varshagroups.secured.rest.model.AuthResponse;
import com.varshagroups.secured.rest.model.Response;
import com.varshagroups.secured.rest.model.UserInformation;
import com.varshagroups.secured.rest.service.IRSASecuredService;

@RestController
public class RSASecuredRestController {

	@Autowired
	IRSASecuredService iRSASecuredService;

	public void setiRSASecuredService(IRSASecuredService iRSASecuredService) {
		this.iRSASecuredService = iRSASecuredService;
	}

	@PostMapping(path="userservice/authenticate",consumes={"application/xml","application/json"},produces={"application/xml","application/json"})
	public @ResponseBody AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
		System.out.println(authRequest.getLogin());
		System.out.println(authRequest.getPassword());
		System.out.println(authRequest.get_2facode());
		return iRSASecuredService.authenticate(authRequest);
	}
	
	@PostMapping(path="userservice/register",consumes={"application/xml","application/json"},produces={"application/xml","application/json"})
	public @ResponseBody Response register(@RequestBody UserInformation userInformation) {
		return iRSASecuredService.register(userInformation);
	}
}
