package com.patienthubservice.dao;

import com.patienthubservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
		User findByUsername(String username);
}
