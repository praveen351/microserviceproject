package com.auth.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.model.AuthenticatedUser;
import com.auth.service.model.AuthurizationUsers;
import com.auth.service.service.AuthurizationUsersService;

@RestController
@RequestMapping("/autherize")
public class AuthControllUser {
	private AuthurizationUsersService auth_service;

	@Autowired
	public AuthControllUser(AuthurizationUsersService auth_service) {
		this.auth_service = auth_service;
	}

	@PostMapping("/getAuthData")
	public ResponseEntity<Map<String, Object>> getAuthUser(@RequestBody AuthurizationUsers auth_userParam) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		AuthenticatedUser authen_obj = auth_service.getAuthUserKeyValue(auth_userParam.getUsername(),
				auth_userParam.getPassword());
		if (authen_obj.getAuthen_userid() == 0) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Sorry , user is not autherized to used the api");
		} else if (authen_obj.getAuthen_userid() == -1) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message",
					"You are currently logged in into the system , please logged out from the system");
		} else if (authen_obj.getAuthen_userid() == -2) {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Sorry , please try after some time");
		} else {
			authmetadata.put("status", "SUCCESS");
			authmetadata.put("message", "Successfully added a Auth User");
			authmetadata.put("authobj", authen_obj);
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/addUpdateAuthData")
	public ResponseEntity<Map<String, Object>> addUpdateAuthUser(@RequestBody AuthurizationUsers auth_userParam,
			@RequestParam("authKey") String authKey) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();

		AuthenticatedUser authen_obj = auth_service.getAuthenUserByKeyValue(authKey);
		if (authen_obj.getAuthen_userid() != 0) {
			AuthenticatedUser fauthen_obj = auth_service.chkStatusAuthenUserByKeyValue(authen_obj);
			if (fauthen_obj.getAuthen_userid() != 0) {
				if (fauthen_obj.getStatus().equals("ACTIVE")) {
					AuthurizationUsers auth_user = auth_service.addUpdateAuthUser(auth_userParam);
					if (auth_user.getAuthuserid() != 0) {
						authmetadata.put("status", "SUCCESS");
						authmetadata.put("message", "Successfully added a Auth User");
						authmetadata.put("data", auth_user);
					} else {
						authmetadata.put("status", "FAILURE");
						authmetadata.put("message",
								"Sorry , user is not added please change username and try again!!!!!");
					}
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Your login time period is completed please login again");
				}
			} else {
				authmetadata.put("status", "FAILURE");
				authmetadata.put("message", "Tech issue is there ,  please try it after some time");
			}
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Auth Key is not a valid key");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/deleteAuthData/{authuserId}")
	public ResponseEntity<Map<String, Object>> deleteAuthUser(@PathVariable("authuserId") long authuserId,
			@RequestParam("authKey") String authKey) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();

		AuthenticatedUser authen_obj = auth_service.getAuthenUserByKeyValue(authKey);
		if (authen_obj.getAuthen_userid() != 0) {
			AuthenticatedUser fauthen_obj = auth_service.chkStatusAuthenUserByKeyValue(authen_obj);
			if (fauthen_obj.getAuthen_userid() != 0) {
				if (fauthen_obj.getStatus().equals("ACTIVE")) {
					int delete_status = auth_service.deleteAuthUser(authuserId);
					if (delete_status != 0) {
						authmetadata.put("status", "SUCCESS");
						authmetadata.put("message", "Successfully deleted a Auth User");
					} else {
						authmetadata.put("status", "FAILURE");
						authmetadata.put("message", "Sorry , user is not deleted please try again!!!!!");
					}
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Your login time period is completed please login again");
				}
			} else {
				authmetadata.put("status", "FAILURE");
				authmetadata.put("message", "Tech issue is there ,  please try it after some time");
			}
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Auth Key is not a valid key");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/logoutAuthData")
	public ResponseEntity<Map<String, Object>> logoutAuthUser(@RequestParam("authKey") String authKey) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();

		AuthenticatedUser authen_obj = auth_service.getAuthenUserByKeyValue(authKey);
		if (authen_obj.getAuthen_userid() != 0) {
			AuthenticatedUser fauthen_obj = auth_service.chkStatusAuthenUserByKeyValue(authen_obj);
			if (fauthen_obj.getAuthen_userid() != 0) {
				if (fauthen_obj.getStatus().equals("ACTIVE")) {
					AuthenticatedUser lfauthen_obj = auth_service
							.logoutAuthenUserByKeyValue(fauthen_obj.getAuthen_userid());
					if (lfauthen_obj.getAuthen_userid() != 0) {
						authmetadata.put("status", "SUCCESS");
						authmetadata.put("message", "Successfully logged a Auth User");
						lfauthen_obj.setStatus("DEAD");
						authmetadata.put("data", lfauthen_obj);
					} else {
						authmetadata.put("status", "FAILURE");
						authmetadata.put("message", "Sorry , user is not logged out please try again!!!!!");
					}
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Your login time period is completed please login again");
				}
			} else {
				authmetadata.put("status", "FAILURE");
				authmetadata.put("message", "Tech issue is there ,  please try it after some time");
			}
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Auth Key is not a valid key");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/getAllAuthData")
	public ResponseEntity<Map<String, Object>> getAllAuthUser(@RequestParam("authKey") String authKey) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();

		AuthenticatedUser authen_obj = auth_service.getAuthenUserByKeyValue(authKey);
		if (authen_obj.getAuthen_userid() != 0) {
			AuthenticatedUser fauthen_obj = auth_service.chkStatusAuthenUserByKeyValue(authen_obj);
			if (fauthen_obj.getAuthen_userid() != 0) {
				if (fauthen_obj.getStatus().equals("ACTIVE")) {
					List<AuthurizationUsers> auth_user = auth_service.getAllAuthUser();
					authmetadata.put("status", "SUCCESS");
					authmetadata.put("message", "Successfully deleted a Auth User");
					authmetadata.put("data", auth_user);
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Your login time period is completed please login again");
				}
			} else {
				authmetadata.put("status", "FAILURE");
				authmetadata.put("message", "Tech issue is there ,  please try it after some time");
			}
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Auth Key is not a valid key");
		}

		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/chkAuthority/{authKey}")
	public ResponseEntity<Map<String, Object>> chkAuthority(@PathVariable("authKey") String authKey) {
		Map<String, Object> authmetadata = new HashMap<String, Object>();
		AuthenticatedUser authen_obj = auth_service.getAuthenUserByKeyValue(authKey);
		if (authen_obj.getAuthen_userid() != 0) {
			AuthenticatedUser fauthen_obj = auth_service.chkStatusAuthenUserByKeyValue(authen_obj);
			if (fauthen_obj.getAuthen_userid() != 0) {
				if (fauthen_obj.getStatus().equals("ACTIVE")) {
					authmetadata.put("status", "SUCCESS");
					authmetadata.put("message", "Successfully added a Auth User");
				} else {
					authmetadata.put("status", "FAILURE");
					authmetadata.put("message", "Your login time period is completed please login again");
				}
			} else {
				authmetadata.put("status", "FAILURE");
				authmetadata.put("message", "Tech issue is there ,  please try it after some time");
			}
		} else {
			authmetadata.put("status", "FAILURE");
			authmetadata.put("message", "Auth Key is not a valid key");
		}
		return new ResponseEntity<Map<String, Object>>(authmetadata, new HttpHeaders(), HttpStatus.OK);
	}
}
