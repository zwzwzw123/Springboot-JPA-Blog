package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//RestController : html파일이 아닌 data를 return해줌
@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;

	//{Id} 주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException(id+" : 해당 유저는 없습니다.");
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환 (웹 브라우저가 이해할 수 있는 데이터로) ->json
		//스프링부트 = MessageConverter가 응답시에 자동 작동
		//자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//user오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
		
	}
