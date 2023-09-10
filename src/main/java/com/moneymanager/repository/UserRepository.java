package com.moneymanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Usersdetails;

@Repository
public interface UserRepository extends JpaRepository<Usersdetails, Integer> {

	@Query(value = "SELECT * FROM UsersDetails WHERE id = ?1", nativeQuery=true)
	public Optional<Usersdetails> findAllDetailsById(@Param("id")int id);	
}
