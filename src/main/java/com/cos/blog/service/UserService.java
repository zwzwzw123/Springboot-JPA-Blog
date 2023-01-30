package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//서비스가 성공하면 commit, 실피하면 rollback
	@Transactional
	public int 회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입()" + e.getMessage());
		}
		return -1;
	}

	/*
	 * @Transactional(readOnly = true) //Select할때 트랜젝션 시작, 서비스 정료시 트랜젝션 종료(정합성 유지
	 * 가능) public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */

}
