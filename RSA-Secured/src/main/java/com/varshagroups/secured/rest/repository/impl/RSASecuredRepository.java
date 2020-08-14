package com.varshagroups.secured.rest.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.varshagroups.secured.rest.model.AuthRequest;
import com.varshagroups.secured.rest.model.AuthResponse;
import com.varshagroups.secured.rest.model.Response;
import com.varshagroups.secured.rest.model.UserInformation;
import com.varshagroups.secured.rest.repository.IRSASecuredRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import de.taimos.totp.TOTP;

@Repository
public class RSASecuredRepository implements IRSASecuredRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public AuthResponse authenticate(AuthRequest authRequest) {
		AuthResponse authResponse = new AuthResponse();
		try {
			String _2faSecretKey = find2FAKeyFromLogin(authRequest);

			if(!authRequest.get_2facode().equals(get2FACode(_2faSecretKey))) {
				authResponse.setResponseCode("FA");
				authResponse.setResponseMessage("Two Factor Authentication code expired/invlid");
				return authResponse;
			}

			String token = generateRuntimeToken();
			updateToken(authRequest.getLogin(),token);
			authResponse.setLogin(authRequest.getLogin());
			authResponse.setResponseCode("00");
			authResponse.setResponseMessage("success");
			authResponse.setToken(token);
		}catch(Exception e) {
			e.printStackTrace();
			authResponse.setResponseCode("99");
			authResponse.setResponseMessage("Unknown Error");
		}
		return null;
	}

	private void updateToken(String login, String token) {
		String sql = "UPDATE userinformation SET token=?, tokencreatedon=? WHERE login=?";
		jdbcTemplate.update(sql, token, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), login);
	}

	private String find2FAKeyFromLogin(AuthRequest authRequest) {

		String _2fakey= null;
		try {
			String sql = "SELECT _2fakey FROM userinformation WHERE login=? AND password=? AND isdisabled='N' AND isdeleted='N'";
			_2fakey = (String) jdbcTemplate.queryForObject(
					sql, new Object[] { authRequest.getLogin(),authRequest.getPassword() }, String.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return _2fakey;
	}

	private int findLogin(String login) {

		int count= -1;
		try {
			String sql = "SELECT count(*) FROM userinformation WHERE login=?";
			count = jdbcTemplate.queryForObject(
					sql, new Object[] { login }, Integer.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	private static String get2FACode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	private static String generate2FAKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}

	private static String generateRuntimeToken() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return  Base64.getEncoder().encodeToString(bytes);
	}

	@Override
	public Response register(UserInformation userInformation) {
		Response response = new Response();
		try {
			String sql = "INSERT INTO userinformation(login, password, email, _2fakey, createdon, name) VALUES (?, ?, ?, ?, ?, ?)";
			if(findLogin(userInformation.getLogin()) > 0) {
				response.setResponseCode("RE");
				response.setResponseMessage("Login Id already exist");
				return response;
			}
			String _2fakey = generate2FAKey();
			int res = jdbcTemplate.update(sql, userInformation.getLogin(), userInformation.getPassword(),
					userInformation.getEmail(), _2fakey, (new Date()), 
					userInformation.getName());
			if(1 == res) {
				response.setResponseCode("00");
				response.setResponseMessage("success");
				response.set_2fakey(_2fakey);
			} else {
				response.setResponseCode("RE");
				response.setResponseMessage("Unable to register. Try after some time");
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setResponseCode("99");
			response.setResponseMessage("Unknown Error");
		}
		return response;
	}
}
