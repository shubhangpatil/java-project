package com.auction.onlineauctionsystem.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auction.onlineauctionsystem.entities.item;

@Repository
public interface ItemRepository extends CrudRepository<item, Long> {

	
	
}
