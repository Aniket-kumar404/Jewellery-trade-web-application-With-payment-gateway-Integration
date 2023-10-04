package com.jewellery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
 
	Optional<User> findUserByEmail(String email);
}
