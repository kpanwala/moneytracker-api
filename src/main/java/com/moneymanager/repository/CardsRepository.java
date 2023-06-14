package com.moneymanager.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moneymanager.entity.Cards;
import com.moneymanager.entity.Usersdetails;


@Repository
public interface CardsRepository extends JpaRepository<Cards, Integer> {
		
		@Query(value = "SELECT * FROM Cards WHERE uId = ?1", nativeQuery = true)
		public List<Cards> findUserCards(int id);
	
}
