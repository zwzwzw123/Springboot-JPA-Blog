package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

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
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴 받을 것
	//첫번째 페이지 : http://localhost:8000/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Direction.DESC ) org.springframework.data.domain.Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		//페이징의 부가적인 정보를 보고싶지 않다면 .getContent()
		List<User> users = pagingUser.getContent();
		return users;
	}
	//save : id전달x → insert
	//save : id 전달o, 해당 id 데이터o → update
	//save : id전달o,  해당 id 데이터x → insert
	@Transactional //함수 종료시 자동  commit됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		//@RequestBody : json데이터를 요청->Java Object로 변환해서 받음(MessageConverter의 Json라이브러리가 변화해서 받아줌)
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+ requestUser.getEmail());
		
		//영속화 시작
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
				});
		//영속화 종료
		//변경 감지
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//변경 감지 끝
		
		//update 시 save를 쓰고 싶을 때는 @Transactional을 붙이면 x
		//userRepository.save(user);
		
		//더티체킹
		return user;
	}
	
	//delete
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당id는 DB에 없습니다.";
		}
		
		return id+" : 삭제 되었습니다.";
	}
		
}
