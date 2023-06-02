package com.moneymanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.moneymanager.entity.Usersdetails;

@Repository
public interface UserRepository extends JpaRepository<Usersdetails, Integer> {
	@Query(value = "SELECT * FROM UsersDetails", nativeQuery=true)
	public List<Usersdetails> findAll();

	@Query(value = "SELECT * FROM UsersDetails WHERE id = ?1", nativeQuery=true)
	public Optional<Usersdetails> findById(int id);
}
