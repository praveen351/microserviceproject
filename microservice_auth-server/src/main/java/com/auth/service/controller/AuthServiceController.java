package com.auth.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.model.AuthenticatedUser;
import com.auth.service.model.AuthurizationUsers;
import com.auth.service.service.AuthurizationUsersService;

@RestController
@RequestMapping("/auth")
public class AuthServiceController {
	private AuthurizationUsersService auth_service;

	@Autowired
	public AuthServiceController(AuthurizationUsersService auth_service) {
		this.auth_service = auth_service;
	}

	@PostMapping("/getAuthData")
	public ResponseEntity<Map<String, Object>> getAuthUser(@RequestBody AuthurizationUsers auth_userParam) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		AuthenticatedUser authen_obj = auth_service.getAuthUserKeyValue(auth_userParam.getUsername(),
				auth_userParam.getPassword());
		if (authen_obj != null) {
			authmetadata.put("status", "SUCCESS");
			authmetadata.put("message", "Successfully added a Auth User");
			authmetadata.put("authobj", authen_obj);
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Sorry , user is not autherized to used the api");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/addUpdateAuthData")
	public ResponseEntity<Map<String, Object>> addUpdateAuthUser(@RequestBody AuthurizationUsers auth_userParam,
			HttpSession session) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		if (session.getAttribute("username") == null) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "No user is there in session");
		} else {
			if (session.getAttribute("authtype").toString().equals("ADMIN")) {
				AuthurizationUsers auth_user = this.auth_service.addUpdateAuthUser(auth_userParam);
				if (auth_user.getAuthuserid() != 0) {
					authmetadata.put("status", "SUCCESS");
					authmetadata.put("message", "Successfully added a Auth User");
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Sorry , user is not added please change username and try again!!!!!");
				}
			} else {
				authmetadata.put("status", "NOT VALID");
				authmetadata.put("message", "Auth user is unable to Add or Update");
			}
		}

		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/deleteAuthData/{authuserId}")
	public ResponseEntity<Map<String, Object>> deleteAuthUser(@PathVariable("authuserId") long authuserId,
			HttpSession session) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		if (session.getAttribute("username") == null) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "No user is there in session");
		} else {
			if (session.getAttribute("authtype").toString().equals("ADMIN")) {
				int deletestatus = this.auth_service.deleteAuthUser(authuserId);
				if (deletestatus != 0) {
					authmetadata.put("status", "SUCCESS");
					authmetadata.put("message", "Successfully deleted a Auth User");
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Sorry , user doesn't exist");
				}
			} else {
				authmetadata.put("status", "NOT VALID");
				authmetadata.put("message", "Auth user is unable to Delete");
			}
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/getUsernameAuthData")
	public ResponseEntity<Map<String, Object>> getUserAuthUser(HttpSession session) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		if (session.getAttribute("username") == null) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Sorry , No data in session");
		} else {
			authmetadata.put("status", "SUCCESS");
			authmetadata.put("user_name", session.getAttribute("username").toString());
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/logoutAuthData")
	public ResponseEntity<Map<String, Object>> logoutAuthUser(HttpSession session) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		if (session.getAttribute("username") == null) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Sorry , No data in session");
		} else {
			session.setAttribute("username", null);
			authmetadata.put("status", "SUCCESS");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/getAllAuthData")
	public ResponseEntity<Map<String, Object>> getAllAuthUser(HttpSession session) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		if (session.getAttribute("username") == null) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "No user is there in session");
		} else {
			if (session.getAttribute("authtype").toString().equals("ADMIN")) {
				List<AuthurizationUsers> auth_list = this.auth_service.getAllAuthUser();
				if (auth_list.size() != 0) {
					authmetadata.put("status", "SUCCESS");
					authmetadata.put("data", auth_list);
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Sorry , user's doesn't exist");
				}
			} else {
				authmetadata.put("status", "NOT VALID");
				authmetadata.put("message", "Auth user is unable to Get the Data");
			}
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}
}