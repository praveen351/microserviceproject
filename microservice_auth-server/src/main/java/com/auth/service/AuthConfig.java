package com.auth.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth.service.model.AuthurizationUsers;
import com.auth.service.service.AuthurizationUsersService;

@Configuration
public class AuthConfig {
	
	@Autowired
	AuthurizationUsersService auth_service;
	
	@Bean
	public void addStaticAuthData() {
		ArrayList<AuthurizationUsers> auth_userList = new ArrayList<>(
				Arrays.asList(new AuthurizationUsers(null,"admin", "admin4321@", "ALL", "ADMIN", "ACTIVE"),
						new AuthurizationUsers(null,"pks", "praja1234", "READ,UPDATE", "DEVELOPER", "ACTIVE"),
						new AuthurizationUsers(null,"ksp", "ks1234", "READ", "TESTER", "ACTIVE")));
		for (AuthurizationUsers authurizationUsers : auth_userList) {
			auth_service.addUpdateAuthUser(authurizationUsers);
		}
	}
}
