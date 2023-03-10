package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.User;

//DAO
//자동으로 bean등록이 됨
//@RestController  생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//select * from user where username = ?;
	Optional<User> findByUsername(String username);
	
	//JPA Naming전략
	//select * from user where username = ? and password =?;
	/* User findByUsernameAndPassword(String username, String password); */
	
	

}
