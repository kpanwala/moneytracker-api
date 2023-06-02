package com.moneymanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moneymanager.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
	
	@Query(value = "SELECT * FROM Transactions WHERE id = ?1", nativeQuery=true)
	public List<Transactions> findUserTransations(int id);
}
