package com.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.service.model.AuthurizationUsers;

@Repository
public interface AuthurizationUsersRepository extends JpaRepository<AuthurizationUsers, Long> {
	@Query(value = "select * from authuser where user_name=:auth_username and user_passwd=:auth_passwd and status='ACTIVE'", nativeQuery = true)
	AuthurizationUsers findByUsernamePasswd(@Param("auth_username") String authusername,
			@Param("auth_passwd") String authpasswd);

	@Transactional
	@Modifying
	@Query(value = "update authuser set status='DEAD' where AUTHUSERID=:auth_userid", nativeQuery = true)
	int deleteByAuthUserID(@Param("auth_userid") long auth_userid);
}
