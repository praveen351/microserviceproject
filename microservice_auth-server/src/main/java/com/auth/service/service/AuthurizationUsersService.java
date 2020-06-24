package com.auth.service.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.service.keygenerator.AutoKeyGenerator;
import com.auth.service.model.AuthenticatedUser;
import com.auth.service.model.AuthurizationUsers;
import com.auth.service.repository.AuthenticatedUserRepository;
import com.auth.service.repository.AuthurizationUsersRepository;

@Service
public class AuthurizationUsersService {
	private AuthurizationUsersRepository auth_repo;
	private AuthenticatedUserRepository authen_repo;

	@Autowired
	public AuthurizationUsersService(AuthurizationUsersRepository auth_repo, AuthenticatedUserRepository authen_repo) {
		this.auth_repo = auth_repo;
		this.authen_repo = authen_repo;
	}

	public AuthurizationUsers addUpdateAuthUser(AuthurizationUsers auth_user) {
		return this.auth_repo.save(auth_user);
	}

	public AuthenticatedUser getAuthUserKeyValue(String auth_username, String auth_passwd) {
		AuthurizationUsers authzobj = this.auth_repo.findByUsernamePasswd(auth_username, auth_passwd);
		if (authzobj != null) {
			AuthenticatedUser fetauthen_user = authen_repo.findByAuthurizedId(authzobj.getAuthuserid());
			if (fetauthen_user == null) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String genKey = AutoKeyGenerator.getAlphaNumericString(32);

				AuthenticatedUser authen_user = authen_repo
						.save(new AuthenticatedUser(genKey, 60, formatter.format(calendar.getTime()), "ACTIVE"));
				int update_status = authen_repo.updateAuthUserID(authzobj.getAuthuserid(),
						authen_user.getAuthen_userid());
				if (update_status == 1)
					return authen_repo.getOne(authen_user.getAuthen_userid());
				return new AuthenticatedUser(-2);
			} else
				return new AuthenticatedUser(-1);
		}
		return new AuthenticatedUser(0);
	}

	public AuthenticatedUser getAuthenUserByKeyValue(String authKey) {
		AuthenticatedUser authen_user = authen_repo.findByAuthKey(authKey);
		if (authen_user != null)
			return authen_user;
		return new AuthenticatedUser(0);
	}

	public AuthenticatedUser logoutAuthenUserByKeyValue(long authen_userid) {
		AuthenticatedUser authen_user = authen_repo.findById(authen_userid).get();
		if (authen_user != null) {
			int logout_status = authen_repo.logoutAuthen_User(authen_user.getAuthen_userid());
			if (logout_status != 0)
				return authen_repo.findById(authen_user.getAuthen_userid()).get();
		}
		return new AuthenticatedUser(0);
	}

	public AuthenticatedUser chkStatusAuthenUserByKeyValue(AuthenticatedUser authen_user) {
		try {
			Calendar calendar = Calendar.getInstance();

			Date now = calendar.getTime();
			Date loginTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(authen_user.getLogin_date_time());

			long diffInMillies = Math.abs(now.getTime() - loginTime.getTime());
			long mdiff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (!(mdiff <= 60)) {
				int logout_status = authen_repo.logoutAuthen_User(authen_user.getAuthen_userid());
				if (logout_status != 0) {
					authen_user.setStatus("DEAD");
					return authen_user;
				}
			} else
				return authen_user;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return new AuthenticatedUser(0);
	}

	public int deleteAuthUser(long authuserid) {
		return this.auth_repo.deleteByAuthUserID(authuserid);
	}

	public List<AuthurizationUsers> getAllAuthUser() {
		return this.auth_repo.findAll();
	}
}
