package com.moneymanager.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moneymanager.entity.Usercredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<Usercredentials, Integer> {
	@Query(value = "SELECT id FROM UsersCredentials WHERE username = ?1 and password= ?2", nativeQuery=true)
	public List<Integer> findUserCredentials(String username, String password);
}
