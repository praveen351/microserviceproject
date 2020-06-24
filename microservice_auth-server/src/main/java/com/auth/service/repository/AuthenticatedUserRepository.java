package com.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.service.model.AuthenticatedUser;

@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long>{
	@Transactional
	@Modifying
	@Query(value = "update AUTHENTICATED_USER set authxid=:auth_userid where authen_userid=:authen_userid", nativeQuery = true)
	int updateAuthUserID(@Param("auth_userid") long auth_userid,@Param("authen_userid") long authen_userid);
	
	@Query(value = "select * from AUTHENTICATED_USER where auth_key=:auth_key and status='ACTIVE'", nativeQuery = true)
	AuthenticatedUser findByAuthKey(@Param("auth_key") String auth_key);
	
	@Transactional
	@Modifying
	@Query(value = "update AUTHENTICATED_USER set status='DEAD' where authen_userid=:authen_userid", nativeQuery = true)
	int logoutAuthen_User(@Param("authen_userid") long authen_userid);
	
	@Query(value = "select * from AUTHENTICATED_USER where authxid=:auth_userid and status='ACTIVE'", nativeQuery = true)
	AuthenticatedUser findByAuthurizedId(@Param("auth_userid") long auth_userid);
}
