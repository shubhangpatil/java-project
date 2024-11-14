package com.auction.onlineauctionsystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auction.onlineauctionsystem.entities.user;

@Repository
public interface UserRepository extends CrudRepository<user, Long> {

	user findByUsername(String username);
	user findByUsernameAndPassword(String username, String password);
	
}
